package application.data.service;

import application.data.entity.Summary;
import application.data.repository.SummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class SummaryService {
    @Autowired
    SummaryRepository summaryRepository;

    public int saveOrUpdate(Summary s) {
        return summaryRepository.save(s).getId();
    }

    public List<Summary> findAllByPageId(int pageId) {
        return summaryRepository.findAllByPageId(pageId);
    }

    public List<Summary> findAll() {
        return summaryRepository.findAllByOrderByIdDesc();
    }

}
