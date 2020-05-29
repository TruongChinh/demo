package application.util;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.constant.Constant;
import application.util.ReadExcelTemplateService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import static org.apache.poi.ss.usermodel.CellType.*;

@Component
public class ReadExcelTemplateServiceImpl implements ReadExcelTemplateService {


    public ReadExcelTemplateServiceImpl() {
    }


    @Override
    public List<Float> getLineDataByTypeCode(int typeCode) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Get list value by type code
     *
     * @return List<Float>
     */
    @Override
    public List<Float> getListValueByTypeCode(Map<String, List<Float>> map, int key) {

        List<Float> listValue = new ArrayList<>();

        if (map != null && map.size() > 0) {

            listValue = (List<Float>) map.get(String.valueOf(key));

            int firstDigit = Integer.parseInt(Long.toString(key).substring(0, 1));
            switch (firstDigit) {
                case 2:
                    List<Float> list2 = updateList(listValue, (float) 1.1);
                    return list2;

                case 3:
                    List<Float> list3 = updateList(listValue, (float) 1.2);
                    return list3;

                case 4:
                    List<Float> list4 = updateList(listValue, (float) 1.3);
                    return list4;
                case 5:
                    List<Float> list5 = updateList(listValue, (float) 1.4);
                    return list5;

                default:
                    return listValue;
            }
        }
        return listValue;
    }


    private List<Float> updateList(List<Float> list, float factor) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i) * factor);
        }
        return list;
    }

    /**
     * Get raw mapping data from sheet base
     *
     * @return a hash-map with format <"type_code","a row in sheet base">
     */
    @Override
    public Map<String, List<Float>> getRawBaseDataMapping() {
        Map<String, List<Float>> hm = new HashMap<>();
        String filePath = Constant.EXCEL_MAPPING_TEMPLATE;
        String sheetName = Constant.EXCEL_BASE_SHEET;
        int startRow = 4;
        hm = getDataFromSheetBase(filePath, sheetName, startRow);
        return hm;
    }

    /**
     * Get the raw data from the sheet base
     *
     * @param filePath
     * @param sheetName
     * @param startRow
     * @return
     */
    private Map<String, List<Float>> getDataFromSheetBase(String filePath, String sheetName, int startRow) {
        try {

            // Create an object of file class to open xlsx file
            File file = ResourceUtils.getFile("classpath:" + filePath);


            // Create an object of FileInputStrean class to read data

            Workbook workbook = null;

            // find the file extension of file
            String fileExtension = filePath.substring(filePath.indexOf("."));
            if (fileExtension.equals(".xlsx")) {

                FileInputStream fi = new FileInputStream(file);
                workbook = new XSSFWorkbook(fi);
//                workbook.write(fi);
            }
//            else if (fileExtension.equals(".xls")) {
//
//                // If it is xlsx file then create object of HSSFWorkbook class
////                FileInputStream fi = new FileInputStream(file);
//                workbook = new HSSFWorkbook(filePath);
//
//            }
            // Read sheet inside the workbook by its name

            Map<String, List<Float>> hm = new HashMap<>();

            if (workbook != null) {
                Sheet theSheet = workbook.getSheet(sheetName);
                if (theSheet != null) {
                    int rowCount = theSheet.getLastRowNum();
                    for (int i = startRow; i < rowCount + 1; i++) {
                        String key = null;
                        List<Float> listFl = new ArrayList<>();
                        Row row = theSheet.getRow(i);
                        for (Cell cell : row) {
                            int value = getCellValue(cell);
                            if (value > 1) {
                                key = String.valueOf(value);
                            } else {
                                listFl.add((float) value);
                            }
                        }

                        hm.put(key, listFl);
                    }

                } else {
                    System.out.println("Can not get the Sheet in template file!");
                    return null;
                }
            } else {
                System.out.println("Can not create workbook !!!");
                return null;
            }

            return hm;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the value in a cell
     *
     * @param cell
     * @return
     */
    private int getCellValue(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case NUMERIC: // CELL_TYPE_NUMERIC
                if (DateUtil.isCellDateFormatted(cell)) {
                    return 0;
                } else {
                    int numValue = (int) cell.getNumericCellValue();
                    return numValue;
                }
            case STRING: // CELL_TYPE_STRING
                String value = cell.getRichStringCellValue().getString();
                if (value.equals(" ") || value.trim().equals("")) {
                    return 0;
                } else {
                    return (Integer.parseInt(value));
                }
            case FORMULA: // CELL_TYPE_FORMULA
                return Integer.parseInt(cell.getCellFormula());
            case BLANK: // CELL_TYPE_BLANK
                return 0;
            case BOOLEAN: // CELL_TYPE_BOOLEAN
                return 0;
            default:
                return 0;
        }
    }

    /**
     * Get a list of className in sheet class
     *
     * @return List<String>
     */
    @Override
    public List<String> getListClassNameFromTemplate() {
        List<String> listClassName = new ArrayList<>();
        String filePath = Constant.EXCEL_MAPPING_TEMPLATE;
        String sheetName = Constant.EXCEL_CLASS_SHEET;
        int rowNum = 0;
        listClassName = getListClassOrFamilyFromTemplate(filePath, sheetName, rowNum);
        return listClassName;
    }

    /**
     * Get a list of familyName in sheet family
     *
     * @return
     */
    @Override
    public List<String> getListFamilyNameFromTemplate() {
        List<String> listFamilyName = new ArrayList<>();
        String filePath = Constant.EXCEL_MAPPING_TEMPLATE;
        String sheetName = Constant.EXCEL_FAMILY_SHEET;
        int rowNum = 0;
        listFamilyName = getListClassOrFamilyFromTemplate(filePath, sheetName, rowNum);
        return listFamilyName;
    }

    /**
     * Get list ClassName or FamilyName from template
     *
     * @param filePath
     * @param sheetName
     * @param rowNum
     * @return
     */
    private List<String> getListClassOrFamilyFromTemplate(String filePath, String sheetName, int rowNum) {
        List<String> listName = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath:" + filePath);
            FileInputStream fi;
            fi = new FileInputStream(file);
            Workbook workbook = null;
            String fileExtension = filePath.substring(filePath.indexOf("."));
            if (fileExtension.equals(".xlsx")) {
                workbook = new XSSFWorkbook(fi);
            } else if (fileExtension.equals(".xls")) {
                workbook = new HSSFWorkbook(fi);
            }
            if (workbook != null) {
                Sheet theSheet = workbook.getSheet(sheetName);
                if (theSheet != null) {
                    Row row = theSheet.getRow(rowNum);
                    for (Cell cell : row) {
                        String className = cell.getStringCellValue();
                        if (className != null && className.trim().length() > 0) {
                            listName.add(className);
                        } else {
                            break;
                        }

                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listName;
    }

    @Override
    public Map<String, List<Integer>> getFuzzyMatrix() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, List<Float>> getRawTemplateMatrix(String fileName, String sheetName) {
        int startRow = 1;
        try {

            // Create an object of file class to open xlsx file
            File file = ResourceUtils.getFile("classpath:" + fileName);

            // Create an object of FileInputStrean class to read data
            FileInputStream fi = new FileInputStream(file);
            Workbook workbook = null;

            // find the file extension of file
            String fileExtension = fileName.substring(fileName.indexOf("."));
            if (fileExtension.equals(".xlsx")) {

                // If it is xlsx file then create object of XSSFWorkbook class
                workbook = new XSSFWorkbook(fi);

            } else if (fileExtension.equals(".xls")) {

                // If it is xlsx file then create object of HSSFWorkbook class
                workbook = new HSSFWorkbook(fi);

            }
            // Read sheet inside the workbook by its name

            Map<String, List<Float>> hm = new HashMap<>();

            if (workbook != null) {
                Sheet theSheet = workbook.getSheet(sheetName);
                if (theSheet != null) {
                    int rowCount = theSheet.getLastRowNum();
                    for (int i = startRow; i < rowCount + 1; i++) {

                        List<Float> listFl = new ArrayList<>();
                        Row row = theSheet.getRow(i);

                        String key = null;
                        key = row.getCell(0).getStringCellValue();

                        for (int j = 1; j < row.getLastCellNum(); j++) {
                            Cell cell = row.getCell(j);
                            if (cell != null) {
                                listFl.add((float) row.getCell(j).getNumericCellValue());
                            }
                        }
                        hm.put(key, listFl);
                    }

                } else {
                    System.out.println("Can not get the Sheet in template file!");
                    return null;
                }
            } else {
                System.out.println("Can not create workbook !!!");
                return null;
            }

            return hm;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ArrayList<String>> getRuleMatrixFromTemplate(String fileName, String sheetName) {
        int rowNum = 1;
        List<ArrayList<String>> ruleMatrix = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath:" + fileName);
            FileInputStream fi;
            fi = new FileInputStream(file);
            Workbook workbook = null;
            String fileExtension = fileName.substring(fileName.indexOf("."));
            if (fileExtension.equals(".xlsx")) {
                workbook = new XSSFWorkbook(fi);
            } else if (fileExtension.equals(".xls")) {
                workbook = new HSSFWorkbook(fi);
            }
            if (workbook != null) {
                Sheet theSheet = workbook.getSheet(sheetName);
                if (theSheet != null) {
                    int rowCount = theSheet.getLastRowNum();
                    for (int i = rowNum; i < rowCount + 1; i++) {
                        Row row = theSheet.getRow(i);
                        ArrayList<String> rules = new ArrayList<String>();
                        for (Cell cell : row) {
                            String element = cell.getStringCellValue();
                            if (element != null && element.trim().length() > 0) {
                                rules.add(element);
                            } else {
                                break;
                            }
                        }
                        ruleMatrix.add(rules);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ruleMatrix;
    }


    /**
     * Get the value of a Class in raw data
     *
     * @param array
     * @param startPosition
     * @param endPosition
     * @return float
     */
    private float getValueOfClass(List<Float> array, int startPosition, int endPosition) {
        float values = 0;
        if (array != null && array.size() > 0 && startPosition >= 0 && endPosition >= 0) {
            try {
                List<Float> arr = array.subList(startPosition, endPosition);
                if (array != null && arr.size() > 0) {
                    for (int i = 0; i < arr.size(); i++) {
                        values += arr.get(i);
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
                return 0;
            }
        }
        return values;
    }

    /**
     * Get the value of a Class in raw data
     *
     * @param initArray
     * @param listClassName
     * @return Map<String, Float>
     */
    public Map<String, Float> getMapClassValue(List<Float> initArray, List<String> listClassName) {
        HashMap<String, Float> classMap = new HashMap<>();
        if (initArray != null && listClassName != null) {
            for (String str : listClassName) {
                switch (str.trim().toUpperCase()) {
                    case Constant.FAU:
                        classMap.put(str, getValueOfClass(initArray, 0, 10));
                        break;

                    case Constant.FCO:
                        classMap.put(str, getValueOfClass(initArray, 10, 16));
                        break;

                    case Constant.FCS:
                        classMap.put(str, getValueOfClass(initArray, 16, 19));
                        break;

                    case Constant.FDP:
                        classMap.put(str, getValueOfClass(initArray, 19, 39));
                        break;

                    case Constant.FIA:
                        classMap.put(str, getValueOfClass(initArray, 39, 44));
                        break;

                    case Constant.FMT:
                        classMap.put(str, getValueOfClass(initArray, 44, 51));
                        break;

                    case Constant.FPR:
                        classMap.put(str, getValueOfClass(initArray, 51, 54));
                        break;

                    case Constant.FPT:
                        classMap.put(str, getValueOfClass(initArray, 54, 61));
                        break;

                    case Constant.FRU:
                        classMap.put(str, getValueOfClass(initArray, 61, 63));
                        break;

                    case Constant.FTA:
                        classMap.put(str, getValueOfClass(initArray, 63, 67));
                        break;

                    case Constant.FTP:
                        classMap.put(str, getValueOfClass(initArray, 67, 73));
                        break;

                    default:
                        classMap.put(str, 0f);
                        break;
                }
            }
        }
        return classMap;
    }


}
