package application.data.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Page {
    private int id;
    private String url;
    private List<Summary> summaries;


    public Page(String url) {
        this.url = url;
    }

    public Page() {
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
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Transient
    public List<Summary> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<Summary> summaries) {
        this.summaries = summaries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return id == page.id &&
                Objects.equals(url, page.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url);
    }
}
