package application.service;

import application.constant.Constant;
import application.data.entity.*;
import application.data.service.*;
import application.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class CheckPointServiceImpl implements CheckPointService {

    @Autowired
    EpScanImpl epScan;
    @Autowired
    FuzzyServiceImpl fuzzy;
    @Autowired
    CheckPointServiceImpl checkPoint;
    @Autowired
    SampleService sampleService;
    @Autowired
    IssueService issueService;
    @Autowired
    SummaryService summaryService;
    @Autowired
    PageService pageService;
    @Autowired
    CheckPointDataService checkPointDataService;
    @Autowired
    ReadExcelTemplateServiceImpl readExcelTemplateService;
    @Autowired
    ReadDataFromScanResultImpl readDataFromScanResult;
    @Autowired
    AppParamsService appParamsService;

    @Override
    public Float getTotalFuzzyPoint() {
        float firstInput = this.getTotalPoint();
        float secondInput = this.getTotalIssuePoint();
        float fuzzyPointResult = fuzzy.getFuzzyResult(firstInput, secondInput);
        fuzzyPointResult = Math.round(fuzzyPointResult * 100) / 100;

        System.out.println("The first input value: " + firstInput);
        System.out.println("The second input value: " + secondInput);
        System.out.println("The final point result: " + fuzzyPointResult);

        return fuzzyPointResult;
    }

    @Override
    public Float getTotalIssuePoint() {

        // Get list Issue object from sample.js file
        List<Issue> listIssueSample = new ArrayList<>();
        listIssueSample = readDataFromScanResult.parserIssueSample();

        Float issuePoint = 0f;

        for (Issue issue : listIssueSample) {

            System.out.println("Issue info:");
            System.out.println("The type code: " + issue.getTypeCode());
            System.out.println("The number of samples: " + issue.getSamples().size());

            // number of issue
            int score;
            int count = issue.getSamples().size();
            if (count <= 1) {
                score = 1;
            } else if (count <= 4) {
                score = 2;
            } else if (count <= 14) {
                score = 3;
            } else if (count <= 30) {
                score = 4;
            } else {
                score = 5;
            }

            int typeCode = issue.getTypeCode();
            int firstDigit = Integer.parseInt(Long.toString(typeCode).substring(0, 1));

            switch (firstDigit) {
                case 2:
                    issuePoint += score * 2;
                    break;
                case 3:
                    issuePoint += score * 3;
                    break;
                case 4:
                    issuePoint += score * 4;
                    break;
                case 5:
                    issuePoint += score * 5;
                    break;

                default:
                    issuePoint += score;
                    break;
            }
        }
        return issuePoint;
    }

    @Override
    public Float getTotalPoint() {
        // Get base data from template
        Map<String, List<Float>> result = new HashMap<>();
        result = readExcelTemplateService.getRawBaseDataMapping();

        // Get list Issue object from sample.js file
        List<Issue> listIssueSample = new ArrayList<>();
        listIssueSample = readDataFromScanResult.parserIssueSample();

        List<Float> listResult = new ArrayList<>();
        if (listIssueSample != null && listIssueSample.size() > 0) {
            // If listIssue has only one Object, return the first Obj
            listResult = readExcelTemplateService.getListValueByTypeCode(result, listIssueSample.get(0).getTypeCode());
            // If listIssue has more than one Object, get all Obj
            if (listIssueSample.size() > 1) {
                for (int i = 1; i < listIssueSample.size(); i++) {
                    listResult = sumListValue(listResult,
                            readExcelTemplateService.getListValueByTypeCode(result, listIssueSample.get(i).getTypeCode()));
                }
            }
        }

        Float totalPoint = 0f;
        for (Float f : listResult) {
            totalPoint += f;
        }
        return totalPoint;
    }

    /**
     * Sum all list value which extract from template
     *
     * @param currentList
     * @param listToAdd
     * @return
     */
    private List<Float> sumListValue(List<Float> currentList, List<Float> listToAdd) {
        for (int i = 0; i < currentList.size(); i++) {
            currentList.set(i, currentList.get(i) + listToAdd.get(i));
        }
        return currentList;
    }

    /**
     * Get value of all class for export
     *
     * @return Map<String, Float>
     */
    @Override
    public Map<String, Float> getPointForAllClass() {

        // Get all class value from base
        Map<String, Float> baseMap = getValueOfAllClassFromBase();

        // Get all class value base on scan result
        Map<String, Float> scanResultMap = getValueOfAllClassFromScanResult();

        // Get final result
        Map<String, Float> finalResult = new HashMap<>();
        List<String> lstClassName = Constant.getListClassName();
        for (String str : lstClassName) {
            Float base = baseMap.get(str);
            Float scan = scanResultMap.get(str);
            float finalNum = (1 - (scan / base)) * 100;
            finalNum = Math.round(finalNum * 100) / 100;
            finalResult.put(str, finalNum);
        }

        return finalResult;
    }

    @Override
    public String toSavePointOfAllClass() {
        String sb = "";
        Map<String, Float> finalResult = new HashMap<>();
        finalResult = getPointForAllClass();
        if (finalResult != null) {
            for (Map.Entry<String, Float> entry : finalResult.entrySet()) {
                sb += entry.getKey() + "," + entry.getValue() + ",";
            }
        }
        return sb;
    }

    /**
     * Get value of all class from sheet base
     *
     * @return Map<String, Float>
     */
    private Map<String, Float> getValueOfAllClassFromBase() {

        // Get map value from sheet base
        Map<String, List<Float>> hm = readExcelTemplateService.getRawBaseDataMapping();

        // Get list className
        List<String> listClassName = Constant.getListClassName();

        // Get list value of class after multiple with the factor base on typecode

        List<List<Float>> lstTemp = new ArrayList<>();
        for (Entry<String, List<Float>> entry : hm.entrySet()) {
            List<Float> temp = readExcelTemplateService.getListValueByTypeCode(hm, Integer.parseInt(entry.getKey()));
            lstTemp.add(temp);
        }

        List<Float> initArray = new ArrayList<>();
        initArray = lstTemp.get(0);
        for (int i = 1; i < lstTemp.size(); i++) {
            initArray = sumListValue(initArray, lstTemp.get(i));
        }

        Map<String, Float> classMap = new HashMap<>();

        classMap = readExcelTemplateService.getMapClassValue(initArray, listClassName);
        return classMap;

    }

    /**
     * Get value of all class base on the scan result
     *
     * @return Map<String, Float>
     */
    private Map<String, Float> getValueOfAllClassFromScanResult() {

        // Get base data from template
        Map<String, List<Float>> result = new HashMap<>();
        result = readExcelTemplateService.getRawBaseDataMapping();

        // Get list Issue object from sample.js file
        List<Issue> listIssueSample = new ArrayList<>();
        listIssueSample = readDataFromScanResult.parserIssueSample();

        List<Float> listResult = new ArrayList<>();
        if (listIssueSample != null && listIssueSample.size() > 0) {
            // If listIssue has only one Object, return the first Obj
            listResult = readExcelTemplateService.getListValueByTypeCode(result, listIssueSample.get(0).getTypeCode());
            // If listIssue has more than one Object, get all Obj
            if (listIssueSample.size() > 1) {
                for (int i = 1; i < listIssueSample.size(); i++) {
                    listResult = sumListValue(listResult,
                            readExcelTemplateService.getListValueByTypeCode(result, listIssueSample.get(i).getTypeCode()));
                }
            }
        }

        List<String> listClassName = Constant.getListClassName();
        Map<String, Float> classMap = new HashMap<>();
        classMap = readExcelTemplateService.getMapClassValue(listResult, listClassName);

        return classMap;

    }


    public Summary toScan(String url) {
        System.out.println("Okey babie");

//
//		ReadExcelTemplateService reader = new ReadExcelTemplateServiceImpl();
//		System.out.println(reader.getListClassNameFromTemplate());
        int pageId = 0;
        Page checkPage = pageService.checkExist(url);
        if (checkPage == null) {
            pageId = pageService.saveOrUpdate(new Page(url));
        } else {
            pageId = checkPage.getId();
        }

        AppParams app = appParamsService.findByName(Constant.TIME_SCAN);

        epScan.scan(url, new Long(app.getCode()).intValue());
//
        ReadDataFromScanResult read1 = new ReadDataFromScanResultImpl();

        Summary summary = read1.getSummaryObject();
        summary.setPageId(pageId);
        int summaryId = summaryService.saveOrUpdate(summary);
        // Du lieu day vao bang Issue

        List<Issue> lsIssue = read1.parserIssueSample();
        for (Issue issue : lsIssue) {
            issue.setSummaryId(summaryId);
            int issueId = issueService.saveOrUpdate(issue);
            for (Sample sample : issue.getSamples()) {
                sample.setIssueId(issueId);
                sampleService.saveOrUpdate(sample);
            }
        }

        // Cac thong tin cho CheckpointResult Object
        Float totalPoint = getTotalPoint();
        Float issuePoint = getTotalIssuePoint();
        Float fuzzyPoint = getTotalFuzzyPoint();
        String classesPoint = toSavePointOfAllClass();
        checkPointDataService.saveOrUpdate(new CheckpointResult(totalPoint, issuePoint, fuzzyPoint, classesPoint, pageId));

        return new Summary(summaryId, pageId);
    }
}

