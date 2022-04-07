package repository;

import competition.Competition;
import competition.network.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
public class CompetitionDbRepository implements CompetitionRepository {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    @Autowired
    public CompetitionDbRepository(Properties properties) {
        logger.info("Initializing CompetitionDbRepository with proprites {}", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Competition findOne(Long id) {
        return null;
    }

    @Override
    public Competition add(Competition e) {
        return null;
    }

    @Override
    public Iterable<Competition> findAll() {
        logger.traceEntry();

        Connection conn = dbUtils.getConnection();
        List<Competition> competitions = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from \"Competition\"")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Date startDate  = new Date(resultSet.getTimestamp("startDate").getTime());
                    Date endDate  = new Date(resultSet.getTimestamp("endDate").getTime());

                    Competition competition = new Competition(startDate, endDate);
                    competition.setId(id);
                    competitions.add(competition);

                }
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("Error DB"+throwables);
        }
        logger.traceExit(competitions);
        return competitions;
    }

    @Override
    public Competition update(Long e, Competition e2) {
        return null;
    }



    @Override
    public Competition delete(Long e) {
        return null;
    }
}
