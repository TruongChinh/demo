package application.model.dto;

import java.util.List;

public class ScanResultDTO {
    private List scanData;
    private SeverityDTO severityDTO;

    public List getScanData() {
        return scanData;
    }

    public void setScanData(List scanData) {
        this.scanData = scanData;
    }

    public SeverityDTO getSeverityDTO() {
        return severityDTO;
    }

    public void setSeverityDTO(SeverityDTO severityDTO) {
        this.severityDTO = severityDTO;
    }
}
