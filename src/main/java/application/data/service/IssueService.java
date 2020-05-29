package application.data.service;

import application.constant.Constant;
import application.data.entity.CheckpointResult;
import application.data.entity.Issue;
import application.data.entity.Page;
import application.data.entity.Sample;
import application.data.repository.IssueRepository;
import application.data.repository.PageRepository;
import application.data.repository.SampleRepository;
import application.model.DataApiResult;
import application.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class IssueService {
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    SampleRepository sampleRepository;
    @Autowired
    CheckPointDataService checkPointDataService;

    private RateDetailDTO dto = null;

    public int saveOrUpdate(Issue s) {
        return issueRepository.save(s).getId();
    }

    public List<GeneralTypeCodeDTO> generalIssue(int pageId, int summaryId) {
        List<GeneralTypeCodeDTO> rs = new ArrayList<>();
        List<Issue> issues = issueRepository.generalIssue(pageId, summaryId);
        String code = "";
        Long count = 0L;
        for (int j = 0; j < issues.size(); j++) {
            Issue i = issues.get(j);
            if (!code.equals(i.getCode())) {
                if (count != 0L) {
                    rs.add(new GeneralTypeCodeDTO(count, code));
                }
                code = i.getCode();
                count = 1L;
            } else {
                count++;
            }
            if (j == issues.size() - 1) {
                rs.add(new GeneralTypeCodeDTO(count, code));
            }
        }
        return rs;
    }

    public List<Issue> issueBySeverity(int pageId, int summaryId, String code) {
        return issueRepository.issueBySeverity(pageId, summaryId, code);
    }

    public List<RateDetailDTO> getRateDetail(int pageId, int summaryId) {
        List<RateDetailDTO> lst = new ArrayList<>();
        for (int i = 1; i <= Constant.numOfRate; i++) {
            dto = new RateDetailDTO();
            dto.setRank(i);
            List<Issue> issues = issueBySeverity(pageId, summaryId, i + "");
            if (!issues.isEmpty()) {
                List<Sample> samples = new ArrayList<>();
                for (Issue issue : issues) {
                    samples = sampleRepository.findAllByIssueId(issue.getId());
                    dto.getSamples().addAll(samples);
                }
            }
            lst.add(dto);
        }
        return lst;
    }

    public SeverityDTO getSeverity(int pageId, int summaryId) {
        SeverityDTO severityDTO = new SeverityDTO();
        try {
            severityDTO.setRateIssue(generalIssue(pageId, summaryId));
            severityDTO.setRateDetail(getRateDetail(pageId, summaryId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return severityDTO;
    }

    public void scanLogic(ScanResultDTO scanResultDTO, int pageId, int summaryId) {
        List obj = new ArrayList();
        List<String> key = new ArrayList<>();
        List<String> value = new ArrayList<>();
        CheckpointResult checkpointResult = checkPointDataService.findByPageId(pageId);
        GeneralResultDTO general = new GeneralResultDTO();
        //first value
        key.add("Base Line");
        value.add("100");

        key.add("Last Scan");
        value.add(checkpointResult.getFuzzyPoint() + "");
        key.add("Last 10 times");
        value.add(checkPointDataService.getAVGFuzzyPoint() + "");
        general.setKey(key);
        general.setValue(value);
        obj.add(general);

        GeneralResultDTO pointClasses = checkPointDataService.getColumnChart(pageId);
        obj.add(pointClasses);
        obj.add(checkPointDataService.getAVG10Page());
        scanResultDTO.setScanData(obj);
        scanResultDTO.setSeverityDTO(getSeverity(pageId, summaryId));
    }
}
