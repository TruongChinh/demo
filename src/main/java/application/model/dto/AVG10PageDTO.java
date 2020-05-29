package application.model.dto;

import application.extension.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class AVG10PageDTO {
    private int pageId;
    private String url;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date scanDate;
    private Double fuzzyPoint;
    private String classesPoint;

    public AVG10PageDTO(String url, Date scanDate, Double fuzzyPoint, String classesPoint) {
        this.url = url;
        this.scanDate = scanDate;
        this.fuzzyPoint = fuzzyPoint;
        this.classesPoint = classesPoint;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getScanDate() {
        return scanDate;
    }

    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }

    public Double getFuzzyPoint() {
        return fuzzyPoint;
    }

    public void setFuzzyPoint(Double fuzzyPoint) {
        this.fuzzyPoint = fuzzyPoint;
    }

    public String getClassesPoint() {
        return classesPoint;
    }

    public void setClassesPoint(String classesPoint) {
        this.classesPoint = classesPoint;
    }
}
