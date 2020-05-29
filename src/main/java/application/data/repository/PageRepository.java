package application.data.repository;

import application.data.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {

    List<Page> findPageByUrl(@Param("url") String url);

    Page findPageById(@Param("id") int id);
}
