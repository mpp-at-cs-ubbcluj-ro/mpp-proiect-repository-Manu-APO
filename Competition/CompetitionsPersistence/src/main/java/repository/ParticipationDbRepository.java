package repository;

import competition.Participation;
import competition.network.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;


public class ParticipationDbRepository implements ParticipationRepository{

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();


    public ParticipationDbRepository(Properties properties){
        logger.info("Initializing ParticipationDbRepository with properties {}", properties);
        dbUtils = new JdbcUtils(properties);
    }
    @Override
    public Participation add(Participation e) {
        logger.traceEntry("saving Participation {}",e);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into \"Participation\"(\"participantId\",\"trialId\",\"dateOfSubmission\",\"registryId\") values (?,?,?,?)")){
            preStmt.setString(1, e.getParticipant().getId());
            preStmt.setLong(2, e.getTrial().getId());
            preStmt.setTimestamp(3, new Timestamp(e.getDateOfSubmission().getTime()));
            preStmt.setString(4, e.getRegistry().getId());

            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances",result);

        } catch (SQLException throwable) {
            logger.error(throwable);
            System.err.println("Error DB"+throwable);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Iterable<Participation> findAll() {
        return null;
    }

    @Override
    public Participation update(Long e, Participation e2) {
        return null;
    }

    @Override
    public Participation delete(Long e) {
        return null;
    }

    @Override
    public Participation findOne(Long aLong) {
        return null;
    }
}
