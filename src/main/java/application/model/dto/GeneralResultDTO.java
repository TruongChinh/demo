package application.model.dto;

import java.util.List;

public class GeneralResultDTO {
    private List<String> key;
    private List<String> value;

    public List<String> getKey() {
        return key;
    }

    public void setKey(List<String> key) {
        this.key = key;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
