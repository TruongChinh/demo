package application.data.service;

import application.constant.Constant;
import application.data.entity.CheckpointResult;
import application.data.entity.Page;
import application.data.repository.CheckPointRepository;
import application.model.ClassesPointDTO;
import application.model.dto.AVG10PageDTO;
import application.model.dto.GeneralResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;


@Service
@Transactional
public class CheckPointDataService {
    @Autowired
    private CheckPointRepository checkPointRepository;

    public int saveOrUpdate(CheckpointResult s) {
        return checkPointRepository.save(s).getId();
    }

    public CheckpointResult findByPageId(int pageId) {
        return StreamSupport
                .stream(checkPointRepository.findByPageIdOrderByIdDesc(pageId).spliterator(), false)
                .findFirst().orElse(null);
    }

    public Double getAVGFuzzyPoint() {
        List<CheckpointResult> lstData = checkPointRepository.findAllByOrderByIdDesc();
        Double total = 0D;
        List lstPageIds = new ArrayList();
        for (CheckpointResult bo : lstData) {
            if (!lstPageIds.contains(bo.getPageId()) && bo.getFuzzyPoint() >= 80D) {
                lstPageIds.add(bo.getPageId());
                total += bo.getFuzzyPoint();
            }
            if (lstPageIds.size() == 10) break;
        }
        if (total == 0D) return 0D;
        return total / lstPageIds.size();
    }

    public GeneralResultDTO getColumnChart(int pageId) {
        GeneralResultDTO generalResultDTO = new GeneralResultDTO();
        CheckpointResult bo = findByPageId(pageId);
        List<String> key = new ArrayList<>();
        List<String> value = new ArrayList<>();
        List<String> data = Arrays.asList(bo.getClassesPoint().split(","));
        for (int i = 0; i < data.size(); i++) {
            if (i % 2 == 0) key.add(data.get(i));
            else value.add(data.get(i));
        }
        generalResultDTO.setValue(value);
        generalResultDTO.setKey(key);
        return generalResultDTO;
    }

    public GeneralResultDTO getAVGPage(int page, Double point) {
        GeneralResultDTO generalResultDTO = new GeneralResultDTO();
        List<AVG10PageDTO> data = checkPointRepository.getAVGPage(point);
        int size = data.size();
        Double FAU = 0D;
        Double FCO = 0D;
        Double FCS = 0D;
        Double FDP = 0D;
        Double FIA = 0D;
        Double FMT = 0D;
        Double FPR = 0D;
        Double FPT = 0D;
        Double FRU = 0D;
        Double FTA = 0D;
        Double FTP = 0D;
        List<String> key = new ArrayList<>();
        List<String> value = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (i == page) {
                size = page;
                break;
            }
            List<String> classesPoint = Arrays.asList(data.get(i).getClassesPoint().split(","));
            for (int j = 0; j < classesPoint.size(); j++) {
                if (j == 1) {
                    FAU += Double.parseDouble(classesPoint.get(j));
                } else if (j == 3) {
                    FCO += Double.parseDouble(classesPoint.get(j));
                } else if (j == 5) {
                    FCS += Double.parseDouble(classesPoint.get(j));
                } else if (j == 7) {
                    FDP += Double.parseDouble(classesPoint.get(j));
                } else if (j == 9) {
                    FIA += Double.parseDouble(classesPoint.get(j));
                } else if (j == 11) {
                    FMT += Double.parseDouble(classesPoint.get(j));
                } else if (j == 13) {
                    FPR += Double.parseDouble(classesPoint.get(j));
                } else if (j == 15) {
                    FPT += Double.parseDouble(classesPoint.get(j));
                } else if (j == 17) {
                    FRU += Double.parseDouble(classesPoint.get(j));
                } else if (j == 19) {
                    FTA += Double.parseDouble(classesPoint.get(j));
                } else if (j == 21) {
                    FTP += Double.parseDouble(classesPoint.get(j));
                }
            }
        }
        key.addAll(Constant.getListClassName());

        value.add(AVG(FAU, size));
        value.add(AVG(FCO, size));
        value.add(AVG(FCS, size));
        value.add(AVG(FDP, size));
        value.add(AVG(FIA, size));
        value.add(AVG(FMT, size));
        value.add(AVG(FPR, size));
        value.add(AVG(FPT, size));
        value.add(AVG(FRU, size));
        value.add(AVG(FTA, size));
        value.add(AVG(FTP, size));

        generalResultDTO.setKey(key);
        generalResultDTO.setValue(value);
        return generalResultDTO;

    }

    public Double getAVGFuzzyPoint(int page, Double point) {
        Double val = 0D;
        List<AVG10PageDTO> data = checkPointRepository.getAVGPage(point);
        int size = data.size();
        for (int i = 0; i < data.size(); i++) {
            if (i == page) {
                size = page;
                break;
            }
            val += data.get(i).getFuzzyPoint();
        }
        return val / size;
    }

    public Double getAVG10FuzzyPoint() {
        return getAVGFuzzyPoint(10, 80D);
    }

    public Double getAVG100FuzzyPoint() {
        return getAVGFuzzyPoint(100, 0D);
    }


    public GeneralResultDTO getAVG10Page() {
        return getAVGPage(10, 80D);
    }

    public GeneralResultDTO getAVG100Page() {
        return getAVGPage(100, 0D);
    }

    public GeneralResultDTO getPageReport(int page) {
        GeneralResultDTO generalResultDTO = new GeneralResultDTO();
        List<AVG10PageDTO> data = checkPointRepository.getAVGPage(0D);
        List<String> key = new ArrayList<>();
        List<String> value = new ArrayList<>();
        key.add("Base 100 Page");
        value.add(getAVG100FuzzyPoint().toString());

        key.add("Base 10 Page");
        value.add(getAVG10FuzzyPoint().toString());

        for (int i = 0; i < page; i++) {
            if (i >= data.size()) break;
            key.add(data.get(i).getUrl());
            value.add(data.get(i).getFuzzyPoint().toString());
        }
        generalResultDTO.setValue(value);
        generalResultDTO.setKey(key);
        return generalResultDTO;
    }


    public String AVG(Double d, int size) {
        Double s = d / size;
        return s.toString();
    }


}
