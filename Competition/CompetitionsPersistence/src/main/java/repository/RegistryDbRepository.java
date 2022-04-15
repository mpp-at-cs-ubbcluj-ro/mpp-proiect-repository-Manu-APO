package repository;

import competition.Registry;
import competition.network.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RegistryDbRepository implements RegistryRepository {

    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;

    public RegistryDbRepository(Properties properties) {
        logger.info("Initializing RegistryDbRepository with properties {}", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Registry add(Registry entity) {
        logger.traceEntry("Saving registry {}", entity);

        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("insert into \"Registry\"(\"username\",\"password\",\"firstName\",\"lastName\",\"isAdmin\") values (?,?,?,?,?) returning *")) {
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getFirstName());
            preparedStatement.setString(4, entity.getLastName());
            preparedStatement.setBoolean(5, entity.getIsAdmin());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    boolean isAdmin = resultSet.getBoolean("isAdmin");

                    Registry savedRegistry = new Registry(username, password, firstName, lastName, isAdmin);
                    savedRegistry.setId(id);
                    logger.trace("Registry saved {}", savedRegistry);
                    return savedRegistry;
                }
                logger.trace("Registry not saved");
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB: " + throwable);
        }
        logger.traceExit("Exiting saving registry {}", entity);
        return null;
    }

    @Override
    public Iterable<Registry> findAll() {
        logger.traceEntry("Finding all registries");

        Connection conn = dbUtils.getConnection();
        List<Registry> registries = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from \"Registry\"")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    boolean isAdmin = resultSet.getBoolean("isAdmin");

                    Registry registry = new Registry(username, password, firstName, lastName, isAdmin);
                    registry.setId(id);
                    registries.add(registry);
                }
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB: " + throwable);
        }

        logger.traceExit("Exiting finding all registries {}", registries);
        return registries;

    }

    @Override
    public Registry update(Long id, Registry newEntity) {
        return null;
    }

    @Override
    public Registry delete(Long id) {
        return null;
    }

    @Override
    public Registry findOne(Long id) {
        logger.traceEntry("Find registry with id {}", id);

        Connection conn = dbUtils.getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from \"Registry\" where \"id\"=?")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    boolean isAdmin = resultSet.getBoolean("isAdmin");

                    Registry foundRegistry = new Registry(username, password, firstName, lastName, isAdmin);
                    foundRegistry.setId(id);
                    logger.trace("Registry found {}", foundRegistry);
                    return foundRegistry;
                }
                logger.trace("Registry with id {} not found", id);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB: " + throwable);
        }
        logger.traceExit("Exiting finding registry with id {}", id);
        return null;
    }


    @Override
    public Registry getRegistryByCredentials(String username, String password) {
        logger.traceEntry("Find registry by credentials {} {}", username, password);

        Connection conn = dbUtils.getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from \"Registry\" where \"username\"=? and \"password\"=?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    boolean isAdmin = resultSet.getBoolean("isAdmin");

                    Registry foundRegistry = new Registry(username, password, firstName, lastName, isAdmin);
                    foundRegistry.setId(id);
                    logger.trace("Registry found by credentials {}", foundRegistry);
                    return foundRegistry;
                }
                logger.trace("Registry searched by credentials not found");
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB: " + throwable);
        }
        logger.traceExit("Exiting finding registry by credentials");
        return null;
    }

}
