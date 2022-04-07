package repository;

import competition.Participant;
import competition.Trial;
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

public class ParticipantDbRepository implements ParticipantRepository {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public ParticipantDbRepository(Properties properties){
        logger.info("Initializing ParticipantDbRepository with proprites {}", properties);
        dbUtils = new JdbcUtils(properties);
    }


    @Override
    public Participant add(Participant e) {
        logger.traceEntry("saving Participant {}",e);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into \"Participant\"(\"id\",\"userName\",\"password\",\"firstName\",\"lastName\") values (?,?,?,?,?)")){
            preStmt.setString(1,e.getId());
            preStmt.setString(2,e.getUserName());
            preStmt.setString(3,e.getPassword());
            preStmt.setString(4,e.getFirstName());
            preStmt.setString(5,e.getLastName());

            ResultSet result = preStmt.executeQuery();

            if(result.next()){

                String id = result.getString("id");
                e.setId(id);
                logger.trace("Saved {} instances",result);
                return e;
            }

            logger.trace("Not saved {} instances",result);

        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB"+throwable);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Iterable<Participant> findAll() {
        logger.traceEntry();

        Connection conn = dbUtils.getConnection();
        List<Participant> participants = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from \"Participant\"")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String userName = resultSet.getString("userName");
                    String password = resultSet.getString("password");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");

                    Participant participant = new Participant(userName, password, firstName, lastName);
                    participant.setId(id);
                    participants.add(participant);
                }
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB"+throwable);
        }
        logger.traceExit(participants);
        return participants;

    }

    @Override
    public Participant update(String id, Participant e) {
        return null;
    }

    @Override
    public Participant delete(String id) {
        return null;
    }

    @Override
    public Participant findOne(String id) {
        logger.traceEntry();

        Connection conn = dbUtils.getConnection();

        Participant participant = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from \"Participants\" where id="+id)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String userName = resultSet.getString("userName");
                    String password = resultSet.getString("password");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");

                    participant = new Participant(userName, password, firstName, lastName);
                    participant.setId(id);

                }
            }
        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB"+throwable);
        }
        logger.traceExit(participant);
        return participant;
    }

    @Override
    public int getNumberOfParticipantsForTrial(Trial trial) {
//        logger.traceEntry();
//        Connection conn = dbUtils.getConnection();
//
//        int nr = 0;
//        try (PreparedStatement preparedStatement = conn.prepareStatement("select count(*) from \"Participants\" as p inner join \"Entry\" E on p.id = E.\"idParticipant\" where \"idAthleticTest\" = "+trial.getId())) {
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    nr = resultSet.getInt(1);
//                }
//            }
//        } catch (SQLException throwable) {
//            logger.error(throwable);
//            System.err.println("Error DB"+throwable);
//        }
//        logger.traceExit(nr);
//        return nr;
        return 0;
    }
}
