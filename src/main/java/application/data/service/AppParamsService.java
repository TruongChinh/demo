package application.data.service;

import application.data.entity.AppParams;
import application.data.repository.AppParamsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppParamsService {
    @Autowired
    AppParamsRepository appParamsRepository;

    public AppParams findByName(String name) {
        return appParamsRepository.findByName(name);
    }

    public void updateOrInsert(AppParams a) {
        appParamsRepository.save(a);
    }
}
