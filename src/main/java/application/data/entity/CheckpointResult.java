package application.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "checkpoint_result")
public class CheckpointResult {
    private int id;
    private double totalPoint;
    private double issuePoint;
    private double fuzzyPoint;
    private String classesPoint;
    private int pageId;

    public CheckpointResult() {
    }

    public CheckpointResult(double totalPoint, double issuePoint, double fuzzyPoint, String classesPoint, int pageId) {
        this.totalPoint = totalPoint;
        this.issuePoint = issuePoint;
        this.fuzzyPoint = fuzzyPoint;
        this.classesPoint = classesPoint;
        this.pageId = pageId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "total_point")
    public double getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(double totalPoint) {
        this.totalPoint = totalPoint;
    }

    @Basic
    @Column(name = "issue_point")
    public double getIssuePoint() {
        return issuePoint;
    }

    public void setIssuePoint(double issuePoint) {
        this.issuePoint = issuePoint;
    }

    @Basic
    @Column(name = "fuzzy_point")
    public double getFuzzyPoint() {
        return fuzzyPoint;
    }

    public void setFuzzyPoint(double fuzzyPoint) {
        this.fuzzyPoint = fuzzyPoint;
    }

    @Basic
    @Column(name = "classes_point")
    public String getClassesPoint() {
        return classesPoint;
    }

    public void setClassesPoint(String classesPoint) {
        this.classesPoint = classesPoint;
    }

    @Basic
    @Column(name = "page_id")
    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckpointResult that = (CheckpointResult) o;
        return id == that.id &&
                Double.compare(that.totalPoint, totalPoint) == 0 &&
                Double.compare(that.issuePoint, issuePoint) == 0 &&
                Double.compare(that.fuzzyPoint, fuzzyPoint) == 0 &&
                Objects.equals(classesPoint, that.classesPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalPoint, issuePoint, fuzzyPoint, classesPoint);
    }
}
