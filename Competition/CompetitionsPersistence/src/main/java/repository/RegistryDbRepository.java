package repository;

import competition.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import competition.network.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RegistryDbRepository implements RegistryRepository{

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public RegistryDbRepository(Properties properties){
        logger.info("Initializing RegistryDbRepository with proprites {}", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Registry findOne(String id) {
//        logger.traceEntry();
//
//        Connection conn = dbUtils.getConnection();
//
//        Registry organizer = null;
//        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from \"Registry\" where id="+id)) {
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    Long idO = resultSet.getLong("id");
//                    String firstName = resultSet.getString("firstName");
//                    String lastName = resultSet.getString("lastName");
//                    int age = resultSet.getInt("age");
//                    String us = resultSet.getString("username");
//                    String password = resultSet.getString("password");
//                    organizer = new Organizer(firstName,lastName,age,us,password);
//                    organizer.setId(idO);
//
//                }
//            }
//        } catch (SQLException throwables) {
//            logger.error(throwables);
//            System.err.println("Error DB"+throwables);
//        }
//        logger.traceExit(organizer);
//        return organizer;
        return null;
    }

    @Override
    public Registry add(Registry e) {
        return null;
    }

    @Override
    public Iterable<Registry> findAll() {
        return null;
    }

    @Override
    public Registry update(String e, Registry e2) {
        return null;
    }

    @Override
    public Registry delete(String e) {
        return null;
    }

}
