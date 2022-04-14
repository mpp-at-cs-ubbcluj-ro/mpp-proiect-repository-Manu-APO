package repository;

import competition.Competition;
import competition.network.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class CompetitionDbRepository implements CompetitionRepository {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    @Autowired
    public CompetitionDbRepository(Properties properties) {
        logger.info("Initializing CompetitionDbRepository with properties {}", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Competition findOne(Long id) {
        return null;
    }

    @Override
    public Competition add(Competition entity) {
        return null;
    }

    @Override
    public Iterable<Competition> findAll() {
        return null;
    }

    @Override
    public Competition update(Long id, Competition newEntity) {
        return null;
    }


    @Override
    public Competition delete(Long id) {
        return null;
    }
}
