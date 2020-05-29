package application.data.entity;

import application.extension.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Summary {
    private int id;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date scanDate;
    private int scanTime;
    private String version;
    private int pageId;
    private String url;


    public Summary(int id, int pageId) {
        this.id = id;
        this.pageId = pageId;
    }

    public Summary(Date scanDate, int scanTime, String version) {
        this.scanDate = scanDate;
        this.scanTime = scanTime;
        this.version = version;
    }

    public Summary() {
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

    @Transient
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "scan_date")
    public Date getScanDate() {
        return scanDate;
    }

    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }

    @Basic
    @Column(name = "scan_time")
    public int getScanTime() {
        return scanTime;
    }

    public void setScanTime(int scanTime) {
        this.scanTime = scanTime;
    }

    @Basic
    @Column(name = "version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
        Summary summary = (Summary) o;
        return id == summary.id &&
                scanTime == summary.scanTime &&
                Objects.equals(scanDate, summary.scanDate) &&
                Objects.equals(version, summary.version) &&
                Objects.equals(pageId, summary.pageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scanDate, scanTime, version, pageId);
    }
}
