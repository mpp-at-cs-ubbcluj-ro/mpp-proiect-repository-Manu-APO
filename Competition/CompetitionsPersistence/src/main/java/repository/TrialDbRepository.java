package repository;

import competition.TrialDTO;
import competition.network.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        return null;
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
