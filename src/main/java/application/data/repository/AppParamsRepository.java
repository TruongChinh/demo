package application.data.repository;

import application.data.entity.AppParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppParamsRepository extends JpaRepository<AppParams, Long> {

    AppParams findByName(@Param("name") String name);

}
