package repository;

import competition.*;
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
        logger.traceEntry("Find trial with id {}", id);

        Connection conn = dbUtils.getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from \"Trial\" where \"id\"=?")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Long competitionId = resultSet.getLong("competitionId");
                    TRIAL_TYPE trialType = TRIAL_TYPE.valueOf(resultSet.getString("trialType"));
                    AGE_CATEGORY ageCategory = AGE_CATEGORY.valueOf(resultSet.getString("ageCategory"));
                    int maxNumberOfParticipants = resultSet.getInt("maxNumberOfParticipants");
                    Date startDate = resultSet.getDate("startDate");
                    Date endDate = resultSet.getDate("endDate");

                    TrialDTO foundTrial = new TrialDTO(competitionId,maxNumberOfParticipants,trialType,ageCategory,startDate,endDate);
                    foundTrial.setId(id);
                    logger.trace("Trial found {}", foundTrial);
                    return foundTrial;
                }
                logger.trace("Trial with id {} not found", id);
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB: " + throwable);
        }
        logger.traceExit("Exiting finding trial with id {}", id);
        return null;
    }

    @Override
    public TrialDTO add(TrialDTO entity) {
        logger.traceEntry("Saving trial {}", entity);

        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("insert into \"Trial\"(\"competitionId\",\"maxNumberOfParticipants\",\"trialType\",\"ageCategory\",\"startDate\",\"endDate\") values (?,?,?,?,?,?) returning *")) {
            preparedStatement.setLong(1, 1);
            preparedStatement.setInt(2, entity.getMaxNumberOfParticipants());
            preparedStatement.setString(3, entity.getTrialType().toString());
            preparedStatement.setString(4, entity.getAgeCategory().toString());
            //preparedStatement.setTimestamp(5, new Timestamp(entity.getStartDate().getTime()));
            //preparedStatement.setTimestamp(6, new Timestamp(entity.getEndDate().getTime()));
            preparedStatement.setTimestamp(5, new Timestamp(new Date(System.currentTimeMillis()).getTime()));
            preparedStatement.setTimestamp(6, new Timestamp(new Date(System.currentTimeMillis()).getTime()));


            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Long competitionId = resultSet.getLong("competitionId");
                    TRIAL_TYPE trialType = TRIAL_TYPE.valueOf(resultSet.getString("trialType"));
                    AGE_CATEGORY ageCategory = AGE_CATEGORY.valueOf(resultSet.getString("ageCategory"));
                    int maxNumberOfParticipants = resultSet.getInt("maxNumberOfParticipants");
                    Date startDate = resultSet.getDate("startDate");
                    Date endDate = resultSet.getDate("endDate");

                    TrialDTO foundTrial = new TrialDTO(competitionId,maxNumberOfParticipants,trialType,ageCategory,startDate,endDate);
                    foundTrial.setId(id);
                    logger.trace("Trial returned {}", foundTrial);
                    return foundTrial;
                }
                logger.trace("Trial not saved");
            }

        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB" + throwable);
        }
        logger.traceExit();
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
        logger.traceEntry("Update trial with id {} with {}", id, newEntity);

        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("update \"Trial\" set \"competitionId\"=?, \"maxNumberOfParticipants\"=?, \"trialType\"=? ,\"ageCategory\" =? , \"startDate\" =? , \"endDate\" =? where \"id\"=? returning *")) {
            preparedStatement.setLong(1, 1);
            preparedStatement.setInt(2, newEntity.getMaxNumberOfParticipants());
            preparedStatement.setString(3, newEntity.getTrialType().toString());
            preparedStatement.setString(4, newEntity.getAgeCategory().toString());
//            preparedStatement.setTimestamp(5, new Timestamp(newEntity.getStartDate().getTime()));
//            preparedStatement.setTimestamp(6, new Timestamp(newEntity.getEndDate().getTime()));
            preparedStatement.setTimestamp(5, new Timestamp(new Date(System.currentTimeMillis()).getTime()));
            preparedStatement.setTimestamp(6, new Timestamp(new Date(System.currentTimeMillis()).getTime()));
            preparedStatement.setLong(7,newEntity.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    if (resultSet.getLong("id") == id) {
                        Long competitionId = resultSet.getLong("competitionId");
                        TRIAL_TYPE trialType = TRIAL_TYPE.valueOf(resultSet.getString("trialType"));
                        AGE_CATEGORY ageCategory = AGE_CATEGORY.valueOf(resultSet.getString("ageCategory"));
                        int maxNumberOfParticipants = resultSet.getInt("maxNumberOfParticipants");
                        Date startDate = resultSet.getDate("startDate");
                        Date endDate = resultSet.getDate("endDate");

                        TrialDTO updatedTrial = new TrialDTO(competitionId,maxNumberOfParticipants,trialType,ageCategory,startDate,endDate);
                        updatedTrial.setId(id);
                        logger.trace("Trial returned {}", updatedTrial);
                        return updatedTrial;
                    }
                }
                logger.trace("Trial not updated");
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB: " + throwable);
        }
        logger.traceExit("Exiting updating trial with id {}", id);
        return null;
    }

    @Override
    public TrialDTO delete(Long id) {
        logger.traceEntry("Delete trial with id {}", id);

        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("delete from \"Trial\" where \"id\"=? returning *")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    if (resultSet.getLong("id") == id) {
                        Long competitionId = resultSet.getLong("competitionId");
                        TRIAL_TYPE trialType = TRIAL_TYPE.valueOf(resultSet.getString("trialType"));
                        AGE_CATEGORY ageCategory = AGE_CATEGORY.valueOf(resultSet.getString("ageCategory"));
                        int maxNumberOfParticipants = resultSet.getInt("maxNumberOfParticipants");
                        Date startDate = resultSet.getDate("startDate");
                        Date endDate = resultSet.getDate("endDate");

                        TrialDTO deletedTrial = new TrialDTO(competitionId,maxNumberOfParticipants,trialType,ageCategory,startDate,endDate);
                        deletedTrial.setId(id);
                        logger.trace("Trial returned {}", deletedTrial);
                        return deletedTrial;
                    }
                }
                logger.trace("Trial not deleted");
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB: " + throwable);
        }
        logger.traceExit("Exiting deleting trial with id {}", id);
        return null;
    }
}
