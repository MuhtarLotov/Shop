package team.alabs.wso3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.alabs.wso3.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
