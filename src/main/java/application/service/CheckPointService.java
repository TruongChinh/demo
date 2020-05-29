package application.service;

import application.data.entity.Summary;

import java.util.Map;

public interface CheckPointService {

    Float getTotalFuzzyPoint();

    Float getTotalIssuePoint();

    Float getTotalPoint();

    Map<String, Float> getPointForAllClass();

    String toSavePointOfAllClass();

    Summary toScan(String url);

}