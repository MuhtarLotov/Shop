package team.alabs.wso3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.alabs.wso3.entity.DatabaseConfiguration;

@Repository
public interface DatabaseConfigurationRepository extends JpaRepository<DatabaseConfiguration, Integer> {
    boolean existsByConnectionStringAndUsernameAndPassword(String connectionString, String username, String password);
}
