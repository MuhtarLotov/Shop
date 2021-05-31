package team.alabs.wso3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.alabs.wso3.entity.Data;
import team.alabs.wso3.entity.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface QueryRepository extends JpaRepository<Query, Integer> {

    Optional<Query> findByNameAndData(String name, Data data);

    void deleteByNameAndData(String name, Data data);

    List<Query> findByData(Data data);
}
