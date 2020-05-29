package application.controller.api;

import application.common.Common;
import application.constant.Constant;
import application.constant.StatusRegisterUserEnum;
import application.data.entity.AppParams;
import application.data.entity.Summary;
import application.data.entity.User;
import application.data.service.AppParamsService;
import application.data.service.UserService;
import application.model.DataApiResult;
import application.model.dto.ScanDTO;
import application.model.dto.ScanResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/app")
public class AppApiController {

    Logger log = LogManager.getLogger(AppApiController.class);

    @Autowired
    AppParamsService appParamsService;

    @Autowired
    private UserService userService;

    private DataApiResult rs = null;

    @PostMapping
    public DataApiResult save(@RequestBody AppParams app) {
        rs = new DataApiResult();
        try {
            appParamsService.updateOrInsert(app);
            return Common.setResult(rs, 200, null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Common.setResult(rs, 500, e.getMessage(), null);
        }
    }

    @GetMapping("/time_scan")
    public DataApiResult getTime() {
        rs = new DataApiResult();
        try {
            return Common.setResult(rs, 200, appParamsService.findByName(Constant.TIME_SCAN));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Common.setResult(rs, 500, e.getMessage(), null);
        }
    }

    @RequestMapping(path = "/register-user", method = RequestMethod.POST)
    public DataApiResult registerNewUser(@RequestBody User user) {
        rs = new DataApiResult();
        try {
            StatusRegisterUserEnum statusRegisterUserEnum = userService.registerNewUser(user);
            log.info(statusRegisterUserEnum.toString());
            return Common.setResult(rs, 200, null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Common.setResult(rs, 500, e.getMessage(), null);
        }
    }
}
