package application.controller.api;

import application.common.Common;
import application.constant.Constant;
import application.constant.StatusRegisterUserEnum;
import application.data.entity.AppParams;
import application.data.entity.User;
import application.data.repository.PageRepository;
import application.data.service.AppParamsService;
import application.data.service.PageService;
import application.data.service.UserService;
import application.model.DataApiResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class Test {

    @Autowired
    PageRepository pageRepository;

    private DataApiResult rs = null;

    @GetMapping("/")
    public DataApiResult getTime() {
        rs = new DataApiResult();
        try {
            return Common.setResult(rs, 200, pageRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return Common.setResult(rs, 500, e.getMessage(), null);
        }
    }
}

