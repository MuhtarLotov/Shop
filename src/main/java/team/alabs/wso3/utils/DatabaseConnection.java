package team.alabs.wso3.utils;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Component;
import team.alabs.wso3.entity.DatabaseConfiguration;
import team.alabs.wso3.exception.ValidationException;
import java.sql.SQLException;

@Component
public class DatabaseConnection {

    public BasicDataSource getDatasource(DatabaseConfiguration databaseConfiguration) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(databaseConfiguration.getConnectionString());
        dataSource.setUsername(databaseConfiguration.getUsername());
        dataSource.setPassword(databaseConfiguration.getPassword());
        dataSource.setMinIdle(1);
        dataSource.setMaxIdle(3);
        dataSource.setMaxWaitMillis(120000);
        dataSource.setTimeBetweenEvictionRunsMillis(300000);
        return dataSource;
    }

    public void closeDatasource(BasicDataSource dataSource) {
        try {
            dataSource.close();
        } catch (SQLException throwable) {
            throw new ValidationException("could not close datasource connection");
        }
    }
}
