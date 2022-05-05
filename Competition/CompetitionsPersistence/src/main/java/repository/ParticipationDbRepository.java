package repository;

import competition.*;
import competition.network.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ParticipationDbRepository implements ParticipationRepository {

    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;


    public ParticipationDbRepository(Properties properties) {
        logger.info("Initializing ParticipationDbRepository with properties {}", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public ParticipationDTO add(ParticipationDTO entity) {
        logger.traceEntry("Saving participation {}", entity);

        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("insert into \"Participation\"(\"participantId\",\"trialId\",\"dateOfSubmission\",\"registryId\") values (?,?,?,?) returning *")) {
            preparedStatement.setLong(1, entity.getParticipantId());
            preparedStatement.setLong(2, entity.getTrialId());
            preparedStatement.setTimestamp(3, new Timestamp(entity.getDateOfSubmission().getTime()));
            preparedStatement.setLong(4, entity.getRegistryId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Long participantId = resultSet.getLong("participantId");
                    Long trialId = resultSet.getLong("trialId");
                    Date dateOfSubmission = resultSet.getDate("dateOfSubmission");
                    Long registryId = resultSet.getLong("registryId");

                    ParticipationDTO savedParticipationDTO = new ParticipationDTO(participantId, trialId, dateOfSubmission, registryId);
                    savedParticipationDTO.setId(id);
                    logger.trace("Participation saved {}", savedParticipationDTO);
                    return savedParticipationDTO;
                }
                logger.trace("Participation not saved");
            }

        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB" + throwable);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Iterable<ParticipationDTO> findAll() {
        logger.traceEntry("Finding all participation");

        Connection conn = dbUtils.getConnection();
        List<ParticipationDTO> participation = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from \"Participation\"")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Long participantId = resultSet.getLong("participantId");
                    Long trialId = resultSet.getLong("trialId");
                    Date dateOfSubmission = resultSet.getDate("dateOfSubmission");
                    Long registryId = resultSet.getLong("registryId");

                    ParticipationDTO part = new ParticipationDTO(participantId,trialId,dateOfSubmission,registryId);
                    part.setId(id);
                    participation.add(part);
                }
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB: " + throwable);
        }

        logger.traceExit("Exiting finding all participation {}", participation);
        return participation;

    }

    public Iterable<Participant> getAllParticipantsOfTrial(Long trialId){
        logger.traceEntry("Finding all participants of trial with id:" + trialId);

        Connection conn = dbUtils.getConnection();
        List<Participant> participants = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement("select A.id,\"username\",\"firstName\",\"lastName\" from \"Participant\" A INNER JOIN \"Participation\" B on A.id=B.\"participantId\" and B.\"trialId\"=? ")) {
            preparedStatement.setLong(1, trialId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String username = resultSet.getString("username");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");

                    Participant participant = new Participant(username, "", firstName, lastName);
                    participant.setId(id);
                    participants.add(participant);
                }
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB: " + throwable);
        }

        logger.traceExit("Exiting Finding all participants of trial {}", participants);
        return participants;

    }

    @Override
    public ParticipationDTO update(Long id, ParticipationDTO newEntity) {
        return null;
    }

    @Override
    public ParticipationDTO delete(Long id) {
        return null;
    }

    @Override
    public ParticipationDTO findOne(Long id) {
        return null;
    }
}
