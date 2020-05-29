package application.util;

import application.constant.Constant;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Component
public class FuzzyServiceImpl implements FuzzyService {

    private ReadExcelTemplateService readExcelTemp;

    @Override
    public Map<String, Float> getFuzzyValueForInputTotalPoint(float inputValue) {
        // get the matrix template for input1
        Map<String, List<Float>> hm = new HashMap<>();
        Map<String, Float> hmResult = new HashMap<>();
        hm = getFuzzyMatrix(Constant.EXCEL_INPUT1_SHEET);
        for (Map.Entry<String, List<Float>> entry : hm.entrySet()) {
            String k = entry.getKey();
            ArrayList<Float> v = (ArrayList<Float>) entry.getValue();
            if (inputValue <= 30) {
                hmResult.put("VS", (float) 1);
                hmResult.put("S", (float) 0);
                break;
            } else if (inputValue >= 1026) {
                hmResult.put("VL", (float) 1);
                hmResult.put("L", (float) 0);
                break;
            } else if ((v.get(1) <= inputValue && inputValue < v.get(2)) || (v.get(1) < inputValue && inputValue <= v.get(2))) {
                float lowLevelValue = (v.get(2) - inputValue) / (v.get(2) - v.get(1));
                hmResult.put(k, lowLevelValue);
                if ("VS".equalsIgnoreCase(k)) {
                    hmResult.put("S", 1 - lowLevelValue);
                } else if ("S".equalsIgnoreCase(k)) {
                    hmResult.put("M", 1 - lowLevelValue);
                } else if ("M".equalsIgnoreCase(k)) {
                    hmResult.put("L", 1 - lowLevelValue);
                } else if ("L".equalsIgnoreCase(k)) {
                    hmResult.put("VL", 1 - lowLevelValue);
                }
                break;
            }
        }
        return hmResult;
    }

    @Override
    public Map<String, Float> getFuzzyValueForInputTotalIssuePoint(float inputValue) {
        // get the matrix template for input2
        Map<String, List<Float>> hm = new HashMap<>();
        Map<String, Float> hmResult = new HashMap<>();
        hm = getFuzzyMatrix(Constant.EXCEL_INPUT2_SHEET);
        for (Map.Entry<String, List<Float>> entry : hm.entrySet()) {
            String k = entry.getKey();
            ArrayList<Float> v = (ArrayList<Float>) entry.getValue();
            Float temp1 = v.get(1);
            Float temp2 = v.get(2);
            if (inputValue <= 10) {
                hmResult.put("VS", (float) 1);
                hmResult.put("S", (float) 0);
                break;
            } else if (inputValue >= 270) {
                hmResult.put("VC", (float) 1);
                hmResult.put("C", (float) 0);
                break;
            } else if ((v.get(1) <= inputValue && inputValue < v.get(2)) || (v.get(1) < inputValue && inputValue <= v.get(2))) {
                float lowLevelValue = (v.get(2) - inputValue) / (v.get(2) - v.get(1));
                hmResult.put(k, lowLevelValue);
                if ("VS".equalsIgnoreCase(k)) {
                    hmResult.put("S", 1 - lowLevelValue);
                } else if ("S".equalsIgnoreCase(k)) {
                    hmResult.put("M", 1 - lowLevelValue);
                } else if ("M".equalsIgnoreCase(k)) {
                    hmResult.put("C", 1 - lowLevelValue);
                } else if ("L".equalsIgnoreCase(k)) {
                    hmResult.put("VC", 1 - lowLevelValue);
                }
                break;
            }

        }
        return hmResult;
    }

    @Override
    public Map<String, List<Float>> getFuzzyMatrix(String sheetName) {
        readExcelTemp = new ReadExcelTemplateServiceImpl();
        Map<String, List<Float>> hm = new HashMap<>();
        hm = readExcelTemp.getRawTemplateMatrix(Constant.EXCEL_FUZZY_TEMPLATE, sheetName);
        return hm;
    }

    @Override
    public List<ArrayList<String>> getRuleMatrix() {
        readExcelTemp = new ReadExcelTemplateServiceImpl();
        List<ArrayList<String>> ruleMatrix = new ArrayList<>();
        ruleMatrix = readExcelTemp.getRuleMatrixFromTemplate(Constant.EXCEL_FUZZY_TEMPLATE, Constant.EXCEL_RULE_MATRIX);
        return ruleMatrix;
    }

    @Override
    public Map<String, List<Float>> getUnitVector() {
        return this.getFuzzyMatrix(Constant.EXCEL_UNIT_VECTOR);
    }

    @Override
    public float getFuzzyResult(float firstInput, float secondInput) {

        // get the ruleMatrix from template
        List<ArrayList<String>> ruleMatrix = new ArrayList<>();
        ruleMatrix = this.getRuleMatrix();


//		checkPoint = new CheckPointServiceImpl();
//
        // Get the first fuzzy-value as input base on total point.
//		Float firstInputValue = checkPoint.getTotalPoint();

        // get the fuzzy-value of the input 1
        Map<String, Float> fuzzyInput1 = new HashMap<>();
        fuzzyInput1 = this.getFuzzyValueForInputTotalPoint(firstInput);

        // Get the second fuzzy-value of the input 2
//		Float secondInputValue = checkPoint.getTtotalIssuePoint();

        // get the fuzzy-value of the input 2
        Map<String, Float> fuzzyInput2 = new HashMap<>();
        fuzzyInput2 = this.getFuzzyValueForInputTotalIssuePoint(secondInput);

        // get the list of fuzzy output result
        List<Map<String, Float>> listResult = new ArrayList<>();
        for (Map.Entry<String, Float> entry1 : fuzzyInput1.entrySet()) {
            for (Map.Entry<String, Float> entry2 : fuzzyInput2.entrySet()) {
                String key1 = entry1.getKey();
                Float value1 = entry1.getValue();
                String key2 = entry2.getKey();
                Float value2 = entry2.getValue();
                Float minValue = value1 < value2 ? value1 : value2;

                for (ArrayList<String> array : ruleMatrix) {
                    if (array.get(0).equalsIgnoreCase(key1) && array.get(1).equalsIgnoreCase(key2)) {
                        String key3 = array.get(2);
                        if (listResult.size() > 0) {
                            boolean check = false;
                            for (Map<String, Float> m : listResult) {
                                if (m.containsKey(key3)) {
                                    Float maxValue = minValue > m.get(key3) ? minValue : m.get(key3);
                                    m.replace(key3, maxValue);
                                    check = true;
                                    break;
                                }
                            }
                            if (!check) {
                                Map<String, Float> result = new HashMap<>();
                                result.put(key3, minValue);
                                listResult.add(result);
                            }
                        } else {
                            Map<String, Float> result = new HashMap<>();
                            result.put(key3, minValue);
                            listResult.add(result);
                        }
                        break;
                    }
                }
            }
        }

        // get the unit vector from template
        Map<String, List<Float>> unitVector = new HashMap<>();
        unitVector = this.getUnitVector();

        System.out.println("The Unit vector: " + unitVector.toString());

        // return the final result
        float finalResult = 0;
        float total = 0;
        float devide = 0;
        for (Map<String, Float> hm : listResult) {
            Map.Entry<String, Float> entry = hm.entrySet().iterator().next();
            String checkKey = entry.getKey();
            Float val = entry.getValue();
            for (Entry<String, List<Float>> entrys : unitVector.entrySet()) {
                if (entrys.getKey().equalsIgnoreCase(checkKey)) {
                    total += val * entrys.getValue().get(0);
                    devide += val;
                    break;
                }
            }
        }
        System.out.println("Defuzzy: " + total + "/" + devide);
        finalResult = total / devide;

        return finalResult;
    }

}