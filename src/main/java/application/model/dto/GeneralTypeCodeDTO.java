package application.model.dto;

public class GeneralTypeCodeDTO {
    private Long countTypeCode;
    private String code;

    public Long getCountTypeCode() {
        return countTypeCode;
    }

    public void setCountTypeCode(Long countTypeCode) {
        this.countTypeCode = countTypeCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public GeneralTypeCodeDTO() {
    }

    public GeneralTypeCodeDTO(Long countTypeCode, String code) {
        this.countTypeCode = countTypeCode;
        this.code = code;
    }
}
