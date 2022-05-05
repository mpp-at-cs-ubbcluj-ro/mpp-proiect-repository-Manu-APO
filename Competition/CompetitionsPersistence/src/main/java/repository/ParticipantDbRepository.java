package repository;

import competition.Participant;
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

public class ParticipantDbRepository implements ParticipantRepository {

    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;

    public ParticipantDbRepository(Properties properties) {
        logger.info("Initializing ParticipantDbRepository with properties {}", properties);
        dbUtils = new JdbcUtils(properties);
    }


    @Override
    public Participant add(Participant entity) {
        logger.traceEntry("Saving participant {}", entity);

        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("insert into \"Participant\"(\"username\",\"password\",\"firstName\",\"lastName\") values (?,?,?,?) returning *")) {
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getFirstName());
            preparedStatement.setString(4, entity.getLastName());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");

                    Participant savedParticipant = new Participant(username, password, firstName, lastName);
                    savedParticipant.setId(id);
                    logger.trace("Participant saved {}", savedParticipant);
                    return savedParticipant;
                }
                logger.trace("Participant not saved");
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB: " + throwable);
        }
        logger.traceExit("Exiting saving participant {}", entity);
        return null;
    }

    @Override
    public Iterable<Participant> findAll() {
        logger.traceEntry("Finding all participants");

        Connection conn = dbUtils.getConnection();
        List<Participant> participants = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from \"Participant\"")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");

                    Participant participant = new Participant(username, password, firstName, lastName);
                    participant.setId(id);
                    participants.add(participant);
                }
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB: " + throwable);
        }

        logger.traceExit("Exiting finding all participants {}", participants);
        return participants;

    }

    @Override
    public Participant update(Long id, Participant newEntity) {
        logger.traceEntry("Update participant with id {} with {}", id, newEntity);

        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("update \"Participant\" set \"username\"=?, \"password\"=?, \"firstName\"=? ,\"lastName\" =? where \"id\"=? returning *")) {
            preparedStatement.setString(1, newEntity.getUsername());
            preparedStatement.setString(2, newEntity.getPassword());
            preparedStatement.setString(3, newEntity.getFirstName());
            preparedStatement.setString(4, newEntity.getLastName());
            preparedStatement.setLong(5, newEntity.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    if (resultSet.getLong("id") == id) {
                        String username = resultSet.getString("username");
                        String password = resultSet.getString("password");
                        String firstName = resultSet.getString("firstName");
                        String lastName = resultSet.getString("lastName");

                        Participant updatedParticipant = new Participant(username, password, firstName, lastName);
                        updatedParticipant.setId(id);
                        logger.trace("Participant updated {}", updatedParticipant);
                        return updatedParticipant;
                    }
                }
                logger.trace("Participant not updated");
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB: " + throwable);
        }
        logger.traceExit("Exiting updating participant with id {}", id);
        return null;
    }

    @Override
    public Participant delete(Long id) {
        logger.traceEntry("Delete participant with id {}", id);

        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("delete from \"Participant\" where \"id\"=? returning *")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    if (resultSet.getLong("id") == id) {
                        String username = resultSet.getString("username");
                        String password = resultSet.getString("password");
                        String firstName = resultSet.getString("firstName");
                        String lastName = resultSet.getString("lastName");

                        Participant deletedParticipant = new Participant(username, password, firstName, lastName);
                        deletedParticipant.setId(id);
                        logger.trace("Participant deleted {}", deletedParticipant);
                        return deletedParticipant;
                    }
                }
                logger.trace("Participant not deleted");
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB: " + throwable);
        }
        logger.traceExit("Exiting deleting participant with id {}", id);
        return null;
    }

    @Override
    public Participant findOne(Long id) {
        logger.traceEntry("Find participant with id {}", id);

        Connection conn = dbUtils.getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from \"Participant\" where \"id\"=?")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");

                    Participant foundParticipant = new Participant(username, password, firstName, lastName);
                    foundParticipant.setId(id);
                    logger.trace("Participant found {}", foundParticipant);
                    return foundParticipant;
                }
                logger.trace("Participant with id {} not found", id);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB: " + throwable);
        }
        logger.traceExit("Exiting finding participant with id {}", id);
        return null;
    }


    @Override
    public Participant getParticipantByCredentials(String username, String password) {
        logger.traceEntry("Find participant by credentials {} {}", username, password);

        Connection conn = dbUtils.getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from \"Participant\" where \"username\"=? and \"password\"=?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");

                    Participant foundParticipant = new Participant(username, password, firstName, lastName);
                    foundParticipant.setId(id);
                    logger.trace("Participant found by credentials {}", foundParticipant);
                    return foundParticipant;
                }
                logger.trace("Participant searched by credentials not found");
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB: " + throwable);
        }
        logger.traceExit("Exiting finding participant by credentials");
        return null;
    }


}
