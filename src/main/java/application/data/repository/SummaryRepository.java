package application.data.repository;

import application.data.entity.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, Integer> {

    List<Summary> findAllByPageId(@Param("pageId") int pageId);

    List<Summary> findAllByOrderByIdDesc();
}
