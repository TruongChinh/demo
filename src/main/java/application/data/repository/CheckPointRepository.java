package application.data.repository;

import application.data.entity.CheckpointResult;
import application.model.dto.AVG10PageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Repository
public interface CheckPointRepository extends JpaRepository<CheckpointResult, Integer> {


    List<CheckpointResult> findByPageIdOrderByIdDesc(@Param("pageId") int pageId);

    List<CheckpointResult> findAllByOrderByIdDesc();

    @Query("SELECT new application.model.dto.AVG10PageDTO(p.url, max(s.scanDate), max(c.fuzzyPoint), max(c.classesPoint)) FROM CheckpointResult c, Page p, Summary s " +
            "   where c.pageId = p.id  and s.pageId = p.id and c.fuzzyPoint >= :point group by p.url order by p.url desc ")
    List<AVG10PageDTO> getAVGPage(@Param("point") Double point);
}
