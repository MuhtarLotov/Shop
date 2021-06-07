package team.alabs.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.alabs.shop.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
