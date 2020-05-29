package application.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "app_params", schema = "iso15408", catalog = "")
public class AppParams {
    private int id;
    private String name;
    private String code;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppParams appParams = (AppParams) o;
        return id == appParams.id &&
                Objects.equals(name, appParams.name) &&
                Objects.equals(code, appParams.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code);
    }
}
