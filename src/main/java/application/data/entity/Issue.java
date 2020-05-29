package application.data.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Issue {
    private int id;
    private int severity;
    private int typeCode;
    private int summaryId;
    private Long count;
    private String code;

    @Transient
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Transient
    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Issue(Long count, int severity, int summaryId) {
        this.count = count;
        this.severity = severity;
        this.summaryId = summaryId;
    }

    public Issue(int severity, int typeCode) {
        this.severity = severity;
        this.typeCode = typeCode;
    }

    public Issue(String code) {
        this.code = code;
    }

    public Issue() {
    }

    private List<Sample> samples = new ArrayList<>();

    @Transient
    public List<Sample> getSamples() {
        return samples;
    }


    public void setSamples(List<Sample> samples) {
        this.samples = samples;
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
    @Column(name = "severity")
    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    @Basic
    @Column(name = "type_code")
    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }

    @Basic
    @Column(name = "summary_id")
    public int getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(int summaryId) {
        this.summaryId = summaryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return id == issue.id &&
                severity == issue.severity &&
                typeCode == issue.typeCode &&
                summaryId == issue.summaryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, severity, typeCode, summaryId);
    }
}
