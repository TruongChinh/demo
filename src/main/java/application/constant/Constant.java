package application.constant;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public final static String PREFIX_LINK_UPLOAD = "/link/";
    public final static int DEFAULT_PAGE_SIZE = 10;
    public final static int DEFAULT_PARENT_ID = 1;

    public static final String EXCEL_MAPPING_TEMPLATE = "tem.xlsx";

    public static final String SCAN_OUTPUT = "result";
    public static final String SCAN_LOG = "disp.txt";

    public static final String ISSUE_SEVERITY = "severity";

    public static final String ISSUE_TYPE = "type";

    public static final String ISSUE_SAMPLES = "samples";

    public static final String ISSUE_SUMMARY = "summary";

    public static final String SAMPLE_URL = "url";

    public static final String SAMPLE_DIR = "dir";

    public static final String SAMPLE_REQUEST = "request";

    public static final String SAMPLE_RESPONSE = "response";

    public static final String EXCEL_BASE_SHEET = "base";

    public static final String EXCEL_FAMILY_SHEET = "family";

    public static final String EXCEL_CLASS_SHEET = "class";

    public static final String EXCEL_FUZZY_TEMPLATE = "fuzzy.xlsx";

    public static final String EXCEL_INPUT1_SHEET = "input1";
    public static final String EXCEL_INPUT2_SHEET = "input2";
    public static final String EXCEL_OUTPUT_SHEET = "output";

    public static final String EXCEL_RULE_MATRIX = "rulematrix";
    public static final String EXCEL_UNIT_VECTOR = "unitvector";

    // Constant for Class
    public static final String FAU = "FAU";
    public static final String FCO = "FCO";
    public static final String FCS = "FCS";
    public static final String FDP = "FDP";
    public static final String FIA = "FIA";
    public static final String FMT = "FMT";
    public static final String FPR = "FPR";
    public static final String FPT = "FPT";
    public static final String FRU = "FRU";
    public static final String FTA = "FTA";
    public static final String FTP = "FTP";

    public static List<String> getListClassName() {
        List<String> lstClassName = new ArrayList<>();
        lstClassName.add(FAU);
        lstClassName.add(FCO);
        lstClassName.add(FCS);
        lstClassName.add(FDP);
        lstClassName.add(FIA);
        lstClassName.add(FMT);
        lstClassName.add(FPR);
        lstClassName.add(FPT);
        lstClassName.add(FRU);
        lstClassName.add(FTA);
        lstClassName.add(FTP);
        return lstClassName;
    }

    //Constant for Family
    public static final String FAU_ARP = "FAU_ARP";
    public static final String FAU_GEN = "FAU_GEN";
    public static final String FAU_SAA = "FAU_SAA";
    public static final String FAU_SAR = "FAU_SAR";
    public static final String FAU_STG = "FAU_STG";
    public static final String FAU_SEL = "FAU_SEL";

    public static List<String> getListFamilyOfFAU() {
        List<String> lstFamilyFAU = new ArrayList<>();
        lstFamilyFAU.add(FAU_ARP);
        lstFamilyFAU.add(FAU_GEN);
        lstFamilyFAU.add(FAU_SAA);
        lstFamilyFAU.add(FAU_SAR);
        lstFamilyFAU.add(FAU_STG);
        lstFamilyFAU.add(FAU_SEL);
        return lstFamilyFAU;
    }


    public static final String FCO_NRO = "FCO_NRO";
    public static final String FCO_NRR = "FCO_NRR";

    public static List<String> getListFamilyOfFCO() {
        List<String> lstFamilyFCO = new ArrayList<>();
        lstFamilyFCO.add(FCO_NRO);
        lstFamilyFCO.add(FCO_NRR);
        return lstFamilyFCO;
    }


    public static final String FCS_CKM = "FCS_CKM";
    public static final String FCS_COP = "FCS_COP";

    public static List<String> getListFamilyOfFCS() {
        List<String> lstFamilyFCS = new ArrayList<>();
        lstFamilyFCS.add(FCS_CKM);
        lstFamilyFCS.add(FCS_COP);
        return lstFamilyFCS;
    }


    public static final String FDP_ACC = "FDP_ACC";
    public static final String FDP_ACF = "FDP_ACF";
    public static final String FDP_DAU = "FDP_DAU";
    public static final String FDP_ETC = "FDP_ETC";
    public static final String FDP_IFC = "FDP_IFC";
    public static final String FDP_IFF = "FDP_IFF";
    public static final String FDP_ITC = "FDP_ITC";
    public static final String FDP_ITT = "FDP_ITT";
    public static final String FDP_SDI = "FDP_SDI";
    public static final String FDP_UCT = "FDP_UCT";
    public static final String FDP_UIT = "FDP_UIT";

    public static List<String> getListFamilyOfFDP() {
        List<String> lstFamilyFDP = new ArrayList<>();
        lstFamilyFDP.add(FDP_ACC);
        lstFamilyFDP.add(FDP_ACF);
        lstFamilyFDP.add(FDP_DAU);
        lstFamilyFDP.add(FDP_ETC);
        lstFamilyFDP.add(FDP_IFC);
        lstFamilyFDP.add(FDP_IFF);
        lstFamilyFDP.add(FDP_ITC);
        lstFamilyFDP.add(FDP_ITT);
        lstFamilyFDP.add(FDP_SDI);
        lstFamilyFDP.add(FDP_UCT);
        lstFamilyFDP.add(FDP_UIT);
        return lstFamilyFDP;
    }

    public static final String FIA_AFL = "FIA_AFL";
    public static final String FIA_ATD = "FIA_ATD";
    public static final String FIA_UAU = "FIA_UAU";
    public static final String FIA_UID = "FIA_UID";

    public static List<String> getListFamilyOfFIA() {
        List<String> lstFamilyFIA = new ArrayList<>();
        lstFamilyFIA.add(FIA_AFL);
        lstFamilyFIA.add(FIA_ATD);
        lstFamilyFIA.add(FIA_UAU);
        lstFamilyFIA.add(FIA_UID);
        return lstFamilyFIA;
    }


    public static final String FMT_MOF = "FMT_MOF";
    public static final String FMT_MSA = "FMT_MSA";
    public static final String FMT_MTD = "FMT_MTD";
    public static final String FMT_SAE = "FMT_SAE";
    public static final String FMT_SMF = "FMT_SMF";
    public static final String FMT_SMR = "FMT_SMR";

    public static List<String> getListFamilyOfFMT() {
        List<String> lstFamilyFMT = new ArrayList<>();
        lstFamilyFMT.add(FMT_MOF);
        lstFamilyFMT.add(FMT_MSA);
        lstFamilyFMT.add(FMT_MTD);
        lstFamilyFMT.add(FMT_SAE);
        lstFamilyFMT.add(FMT_SMF);
        lstFamilyFMT.add(FMT_SMR);
        return lstFamilyFMT;
    }


    public static final String FPR_ANO = "FPR_ANO";
    public static final String FPR_UNL = "FPR_UNL";
    public static final String FPR_UNO = "FPR_UNO";

    public static List<String> getListFamilyOfFPR() {
        List<String> lstFamilyFPR = new ArrayList<>();
        lstFamilyFPR.add(FPR_ANO);
        lstFamilyFPR.add(FPR_UNL);
        lstFamilyFPR.add(FPR_UNO);
        return lstFamilyFPR;
    }


    public static final String FPT_FLS = "FPT_FLS";
    public static final String FPT_ITA = "FPT_ITA";
    public static final String FPT_ITC = "FPT_ITC";
    public static final String FPT_ITI = "FPT_ITI";
    public static final String FPT_ITT = "FPT_ITT";
    public static final String FPT_PHP = "FPT_PHP";
    public static final String FPT_RCV = "FPT_RCV";

    public static List<String> getListFamilyOfFPT() {
        List<String> lstFamilyFPT = new ArrayList<>();
        lstFamilyFPT.add(FPT_FLS);
        lstFamilyFPT.add(FPT_ITA);
        lstFamilyFPT.add(FPT_ITC);
        lstFamilyFPT.add(FPT_ITI);
        lstFamilyFPT.add(FPT_ITT);
        lstFamilyFPT.add(FPT_PHP);
        lstFamilyFPT.add(FPT_RCV);
        return lstFamilyFPT;
    }


    public static final String FRU_FLT = "FRU_FLT";
    public static final String FRU_RSA = "FRU_RSA";

    public static List<String> getListFamilyOfFRU() {
        List<String> lstFamilyFRU = new ArrayList<>();
        lstFamilyFRU.add(FRU_FLT);
        lstFamilyFRU.add(FRU_RSA);
        return lstFamilyFRU;
    }


    public static final String FTA_LSA = "FTA_LSA";
    public static final String FTA_MCS = "FTA_MCS";
    public static final String FTA_SSL = "FTA_SSL";
    public static final String FTA_TSE = "FTA_TSE";

    public static List<String> getListFamilyOfFTA() {
        List<String> lstFamilyFTA = new ArrayList<>();
        lstFamilyFTA.add(FTA_LSA);
        lstFamilyFTA.add(FTA_MCS);
        lstFamilyFTA.add(FTA_SSL);
        lstFamilyFTA.add(FTA_TSE);
        return lstFamilyFTA;
    }


    public static final String FTP_ITC = "FTP_ITC";
    public static final String FTP_TRP = "FTP_TRP";

    public static List<String> getListFamilyOfFTP() {
        List<String> lstFamilyFTP = new ArrayList<>();
        lstFamilyFTP.add(FTP_ITC);
        lstFamilyFTP.add(FTP_TRP);
        return lstFamilyFTP;
    }


    public static final String userName = "admin";
    public static final String password = "Abc!234";

    public static final int warnings = 0;
    public static final int lowErrors = 1;
    public static final int mediumErrors = 2;
    public static final int highErrors = 3;
    public static final int criticalErrors = 4;
    public static final int numOfRate = 5;

    public static final String TIME_SCAN = "TIME_SCAN";
}
