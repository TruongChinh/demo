package application.model.pojo;

import java.util.List;

public class IssuePOJO {
    private List<SamplePOJO> samples;
    private Long severity;
    private Long type;

    public List<SamplePOJO> getSamples() {
        return samples;
    }

    public void setSamples(List<SamplePOJO> samples) {
        this.samples = samples;
    }

    public Long getSeverity() {
        return severity;
    }

    public void setSeverity(Long severity) {
        this.severity = severity;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
