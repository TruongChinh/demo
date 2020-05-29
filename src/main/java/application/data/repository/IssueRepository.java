package application.data.repository;

import application.data.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {

    @Query(value = "SELECT new Issue (SUBSTRING(i.typeCode, 1, 1)) FROM Issue i , Summary s, " +
            "  Page p where i.summaryId = s.id and p.id = s.pageId " +
            "  and p.id = :pageId and s.id = :summaryId  order by SUBSTRING(i.typeCode, 1, 1)")
    List<Issue> generalIssue(@Param("pageId") int pageId, @Param("summaryId") int summaryId);

    @Query(value = "SELECT i FROM Issue i , Summary s,  " +
            " Page p where i.summaryId = s.id and p.id = s.pageId and p.id = :pageId and s.id = :summaryId and SUBSTRING(i.typeCode, 1, 1) = :code order by SUBSTRING(i.typeCode, 1, 1) ")
    List<Issue> issueBySeverity(@Param("pageId") int pageId, @Param("summaryId") int summaryId, @Param("code") String code);
}
