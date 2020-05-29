package application.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Sample {
    private int id;
    private String urlIssue;
    private String request;
    private String response;
    private int issueId;

    public Sample() {
    }

    public Sample(String urlIssue, String request, String response) {
        this.request = request;
        this.response = response;
        this.urlIssue = urlIssue;

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
    @Column(name = "url_issue")
    public String getUrlIssue() {
        return urlIssue;
    }

    public void setUrlIssue(String urlIssue) {
        this.urlIssue = urlIssue;
    }

    @Basic
    @Column(name = "request")
    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    @Basic
    @Column(name = "response")
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Basic
    @Column(name = "issue_id")
    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sample sample = (Sample) o;
        return id == sample.id &&
                issueId == sample.issueId &&
                Objects.equals(urlIssue, sample.urlIssue) &&
                Objects.equals(request, sample.request) &&
                Objects.equals(response, sample.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, urlIssue, request, response, issueId);
    }
}
