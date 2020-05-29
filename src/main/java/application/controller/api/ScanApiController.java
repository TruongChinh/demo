package application.controller.api;

import application.common.Common;
import application.data.entity.CheckpointResult;
import application.data.entity.Page;
import application.data.entity.Sample;
import application.data.entity.Summary;
import application.data.service.*;
import application.model.DataApiResult;
import application.model.HisDTO;
import application.model.dto.GeneralResultDTO;
import application.model.dto.ScanDTO;
import application.model.dto.ScanResultDTO;
import application.service.CheckPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/scan")
public class ScanApiController {
    Logger log = LogManager.getLogger(ScanApiController.class);

    @Autowired
    CheckPointService checkPointService;
    @Autowired
    CheckPointDataService checkPointDataService;
    @Autowired
    IssueService issueService;
    @Autowired
    SummaryService summaryService;
    @Autowired
    PageService pageService;
    @Autowired
    SampleService sampleService;

    private DataApiResult rs = null;

    @PostMapping
    public DataApiResult scan(@RequestBody ScanDTO dto) {
        rs = new DataApiResult();
        ScanResultDTO scanResultDTO = new ScanResultDTO();
        try {
            Summary rsScan = checkPointService.toScan(dto.getUrl());
            int pageId = rsScan.getPageId();
            int summaryId = rsScan.getId();
            issueService.scanLogic(scanResultDTO, pageId, summaryId);
            scanResultDTO.getScanData().add(dto.getUrl());
            return Common.setResult(rs, 200, scanResultDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Common.setResult(rs, 500, e.getMessage(), null);
        }
    }

    @PostMapping("/his")
    public DataApiResult his(@RequestBody HisDTO dto) {
        rs = new DataApiResult();
        ScanResultDTO scanResultDTO = new ScanResultDTO();
        try {
            issueService.scanLogic(scanResultDTO, dto.getPageId(), dto.getSummaryId());
            Page page = pageService.findPageById(dto.getPageId());
            scanResultDTO.getScanData().add(page.getUrl());
            return Common.setResult(rs, 200, scanResultDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Common.setResult(rs, 500, null);
        }
    }


    @GetMapping("/listSummary")
    public DataApiResult listSummary() {
        rs = new DataApiResult();
        try {
            List<Summary> summaries = summaryService.findAll();
            for (Summary s : summaries) {
                Page page = pageService.findPageById(s.getPageId());
                s.setUrl(page.getUrl());
            }
            return Common.setResult(rs, 200, summaries);
        } catch (Exception e) {
            e.printStackTrace();
            return Common.setResult(rs, 500, null);
        }
    }

    @GetMapping("/sample/{sampleId}")
    public DataApiResult getSample(@PathVariable("sampleId") Long sampleId) {
        rs = new DataApiResult();
        try {
            Sample sample = sampleService.findById(sampleId.intValue());
            return Common.setResult(rs, 200, sample);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Common.setResult(rs, 500, null);
        }
    }

    @GetMapping("/report/{pageQty}")
    public DataApiResult getReport(@PathVariable("pageQty") Long pageQty) {
        rs = new DataApiResult();
        try {
            return Common.setResult(rs, 200, checkPointDataService.getPageReport(pageQty.intValue()));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Common.setResult(rs, 500, null);
        }
    }


}
