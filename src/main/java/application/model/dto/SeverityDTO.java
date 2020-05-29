package application.model.dto;

import application.data.entity.Issue;

import java.util.List;

public class SeverityDTO {
    private List<GeneralTypeCodeDTO> rateIssue;
    private List<RateDetailDTO> rateDetail;

    public List<GeneralTypeCodeDTO> getRateIssue() {
        return rateIssue;
    }

    public void setRateIssue(List<GeneralTypeCodeDTO> rateIssue) {
        this.rateIssue = rateIssue;
    }

    public List<RateDetailDTO> getRateDetail() {
        return rateDetail;
    }

    public void setRateDetail(List<RateDetailDTO> rateDetail) {
        this.rateDetail = rateDetail;
    }
}
