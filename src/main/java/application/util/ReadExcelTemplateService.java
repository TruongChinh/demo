package application.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ReadExcelTemplateService {

    /**
     * Get raw mapping data from sheet base
     *
     * @return a hash-map with format <"type_code","a row in sheet base">
     */
    public List<Float> getLineDataByTypeCode(int typeCode);

    /**
     * Get raw mapping data from sheet base
     *
     * @return a hash-map with format <"type_code","a row in sheet base">
     */
    public List<Float> getListValueByTypeCode(Map<String, List<Float>> map, int key);

    /**
     * Get raw mapping data from sheet base
     *
     * @return a hash-map with format <"type_code","a row in sheet base">
     */
    public Map<String, List<Float>> getRawBaseDataMapping();

    /**
     * Get raw mapping data from sheet base
     *
     * @return a hash-map with format <"Key","a row in sheet base">
     */
    public Map<String, List<Float>> getRawTemplateMatrix(String fileName, String sheetName);

    /**
     * Get a list of className in sheet Class
     *
     * @return List<String>
     */
    public List<String> getListClassNameFromTemplate();

    /**
     * Get a list of familyName in sheet Family
     *
     * @return
     */

    public List<String> getListFamilyNameFromTemplate();

    public Map<String, List<Integer>> getFuzzyMatrix();

    public List<ArrayList<String>> getRuleMatrixFromTemplate(String fileName, String sheetName);

}
