package application.model.dto;

import application.data.entity.Sample;

import java.util.ArrayList;
import java.util.List;

public class RateDetailDTO {
    private int rank;
    private List<Sample> samples = new ArrayList<>();

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }
}
