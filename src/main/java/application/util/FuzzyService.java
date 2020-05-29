package application.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface FuzzyService {


    public Map<String, Float> getFuzzyValueForInputTotalPoint(float inputValue);

    public Map<String, Float> getFuzzyValueForInputTotalIssuePoint(float inputValue);

    public Map<String, List<Float>> getFuzzyMatrix(String sheetName);

    public List<ArrayList<String>> getRuleMatrix();

    public Map<String, List<Float>> getUnitVector();

    public float getFuzzyResult(float firstInput, float secondInput);

}