package application.data.repository;

import application.data.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Integer> {

    List<Sample> findAllByIssueId(@Param("issueId") int issueId);

    Sample findById(@Param("id") int id);
}
