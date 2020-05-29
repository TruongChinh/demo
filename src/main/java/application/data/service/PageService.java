package application.data.service;

import application.data.entity.Page;
import application.data.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class PageService {
    @Autowired
    PageRepository pageRepository;

    public int saveOrUpdate(Page p) {
        return pageRepository.save(p).getId();
    }

    public Page checkExist(String url) {
        return StreamSupport
                .stream(pageRepository.findPageByUrl(url).spliterator(), false)
                .findFirst().orElse(null);
    }

    public List<Page> fillAll() {
        return pageRepository.findAll();
    }

    public Page findPageById(int id) {
        return pageRepository.findPageById(id);
    }
}
