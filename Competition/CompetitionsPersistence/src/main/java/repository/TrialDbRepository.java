package repository;

import competition.AGE_CATEGORY;
import competition.Participant;
import competition.TRIAL_TYPE;
import competition.TrialDTO;
import competition.network.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class TrialDbRepository implements TrialRepository {

    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;

    @Autowired
    public TrialDbRepository(Properties properties) {
        logger.info("Initializing TrialDbRepository with properties {}", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public TrialDTO findOne(Long id) {
        return null;
    }

    @Override
    public TrialDTO add(TrialDTO entity) {
        return null;
    }

    @Override
    public Iterable<TrialDTO> findAll() {
        logger.traceEntry("Finding all trials");

        Connection conn = dbUtils.getConnection();
        List<TrialDTO> trials = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from \"Trial\"")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Long competitionId = resultSet.getLong("competitionId");
                    TRIAL_TYPE trialType = TRIAL_TYPE.valueOf(resultSet.getString("trialType"));
                    AGE_CATEGORY ageCategory = AGE_CATEGORY.valueOf(resultSet.getString("ageCategory"));
                    int maxNumberOfParticipants = resultSet.getInt("maxNumberOfParticipants");
                    Date startDate = resultSet.getDate("startDate");
                    Date endDate = resultSet.getDate("endDate");

                    TrialDTO trial = new TrialDTO(competitionId,maxNumberOfParticipants,trialType,ageCategory,startDate,endDate);
                    trial.setId(id);
                    trials.add(trial);
                }
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB: " + throwable);
        }

        logger.traceExit("Exiting finding all trials {}", trials);
        return trials;

    }

    @Override
    public TrialDTO update(Long id, TrialDTO newEntity) {
        return null;
    }

    @Override
    public TrialDTO delete(Long id) {
        return null;
    }
}
