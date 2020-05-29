package application.data.service;

import application.data.entity.Sample;
import application.data.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SampleService {
    @Autowired
    private SampleRepository sampleRepository;

    public int saveOrUpdate(Sample s) {
        return sampleRepository.save(s).getId();
    }

    public List<Sample> findAllByIssueId(int issueId) {
        return sampleRepository.findAllByIssueId(issueId);
    }

    public Sample findById(int id) {
        return sampleRepository.findById(id);
    }

}


