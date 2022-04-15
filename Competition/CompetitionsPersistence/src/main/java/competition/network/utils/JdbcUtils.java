package competition.network.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class JdbcUtils {

    private static final Logger logger = LogManager.getLogger();
    private Properties jdbcProps;
    private Connection instance = null;

    public JdbcUtils(Properties props) {
        jdbcProps = props;
    }

    private Connection getNewConnection() {
        logger.traceEntry("Receiving database new connection");

        String url = jdbcProps.getProperty("jdbc.url");
        String user = jdbcProps.getProperty("jdbc.user");
        String pass = jdbcProps.getProperty("jdbc.pass");

        Connection connection = null;
        try {

            if (user != null && pass != null)
                connection = DriverManager.getConnection(url, user, pass);
            else
                connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            logger.error("Error getting connection: " + throwables);
            System.out.println(throwables);
        }
        return connection;
    }

    public Connection getConnection() {
        logger.traceEntry("Receiving database connection");
        try {
            if (instance == null || instance.isClosed())
                instance = getNewConnection();

        } catch (SQLException throwables) {
            logger.error("Error DB: " + throwables);
            System.out.println(throwables);
        }
        logger.traceExit("Connection received: " + instance);
        return instance;
    }
}
