package application.util;

import application.data.entity.Issue;
import application.data.entity.Summary;

import java.util.List;


public interface ReadDataFromScanResult {
    List<Issue> parserIssueSample();

    Summary getSummaryObject();

    void checkRawData();

}