package repository;

import competition.Trial;
import competition.network.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class TrialDbRepository implements TrialRepository {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    @Autowired
    public TrialDbRepository(Properties properties) {
        logger.info("Initializing TrialDbRepository with properties {}", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Trial findOne(Long id) {
//        logger.traceEntry();
//
//        Connection conn = dbUtils.getConnection();
//
//        AthleticTest athleticTest = null;
//        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from \"AthleticTests\" where id="+id)) {
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    Long idA = resultSet.getLong("id");
//                    int minAge  = resultSet.getInt("minAge");
//                    int maxAge  = resultSet.getInt("maxAge");
//                    int testLength  = resultSet.getInt("testLength");
//                    athleticTest = new AthleticTest(minAge,maxAge,testLength);
//                    athleticTest.setId(idA);
//
//
//                }
//            }
//        } catch (SQLException throwables) {
//            logger.error(throwables);
//            System.err.println("Error DB"+throwables);
//        }
//        logger.traceExit(athleticTest);
//        return athleticTest;
        return null;
    }

    @Override
    public Trial add(Trial e) {
//        logger.traceEntry("saving Athletic test{}",e);
//        Connection con = dbUtils.getConnection();
//        try(PreparedStatement preStmt = con.prepareStatement("insert into \"AthleticTests\"(\"minAge\",\"maxAge\",\"testLength\") values (?,?,?) RETURNING *")){
//            preStmt.setInt(1,e.getMinAge());
//            preStmt.setInt(2,e.getMaxAge());
//            preStmt.setInt(3, e.getTestLength());
//            ResultSet result = preStmt.executeQuery();
//
//            if( result.next()){
//                long id = result.getLong("id");
//                e.setId(id);
//                return e;
//            }
//            logger.trace("Saved {} instances",result);
//        } catch (SQLException throwables) {
//            logger.error(throwables);
//            System.err.println("Error DB"+throwables);
//        }
//        logger.traceExit();
        return null;
    }

    @Override
    public Iterable<Trial> findAll() {
//        logger.traceEntry();
//
//        Connection conn = dbUtils.getConnection();
//        List<AthleticTest> athleticTests = new ArrayList<>();
//        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from \"AthleticTests\"")) {
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                while (resultSet.next()) {
//                    Long id = resultSet.getLong("id");
//                    int minAge  = resultSet.getInt("minAge");
//                    int maxAge  = resultSet.getInt("maxAge");
//                    int testLength  = resultSet.getInt("testLength");
//                    AthleticTest athleticTest = new AthleticTest(minAge,maxAge,testLength);
//                    athleticTest.setId(id);
//                    athleticTests.add(athleticTest);
//
//                }
//            }
//        } catch (SQLException throwables) {
//            logger.error(throwables);
//            System.err.println("Error DB"+throwables);
//        }
//        logger.traceExit(athleticTests);
//        return athleticTests;
        return null;
    }

    @Override
    public Trial update(Long e, Trial e2) {
//        logger.traceEntry();
//
//        Connection conn = dbUtils.getConnection();
//        try (PreparedStatement preparedStatement = conn.prepareStatement("UPDATE \"AthleticTests\" set \"minAge\" = ? , \"maxAge\"= ?, \"testLength\" = ? where id = ? RETURNING *")) {
//            preparedStatement.setInt(1,e2.getMinAge());
//            preparedStatement.setInt(2,e2.getMaxAge());
//            preparedStatement.setInt(3, e2.getTestLength());
//            preparedStatement.setLong(4, e);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    Long id = resultSet.getLong("id");
//                    int minAge  = resultSet.getInt("minAge");
//                    int maxAge  = resultSet.getInt("maxAge");
//                    int testLength  = resultSet.getInt("testLength");
//                    AthleticTest athleticTest = new AthleticTest(minAge,maxAge,testLength);
//                    athleticTest.setId(id);
//                    return athleticTest;
//                }
//            }
//        } catch (SQLException throwables) {
//            logger.error(throwables);
//            System.err.println("Error DB"+throwables);
//        }

        return null;
    }



    @Override
    public Trial delete(Long e) {
//        Connection conn = dbUtils.getConnection();
//        try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM \"AthleticTests\"  where id = ? RETURNING *")) {
//            preparedStatement.setLong(1, e);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    Long id = resultSet.getLong("id");
//                    int minAge  = resultSet.getInt("minAge");
//                    int maxAge  = resultSet.getInt("maxAge");
//                    int testLength  = resultSet.getInt("testLength");
//                    AthleticTest athleticTest = new AthleticTest(minAge,maxAge,testLength);
//                    athleticTest.setId(id);
//                    return athleticTest;
//                }
//            }
//        } catch (SQLException throwables) {
//            logger.error(throwables);
//            System.err.println("Error DB"+throwables);
//        }

        return null;
    }
}
