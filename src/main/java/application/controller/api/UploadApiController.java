package application.controller.api;

import application.constant.Constant;
import application.model.FileUploadResult;
import application.service.FileStorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class UploadApiController {

    Logger log = LogManager.getLogger(UploadApiController.class);

    @Autowired
    FileStorageService storageService;

    @PostMapping("/upload-image")
    public FileUploadResult uploadImage(
            @RequestParam("file") MultipartFile file) {
        String message = "";
        FileUploadResult result = new FileUploadResult();
        try {
            String newFilename = storageService.store(file);
            message = "You successfully uploaded " +
                    file.getOriginalFilename() + "!";
            result.setMessage(message);
            result.setHttp(200);
            result.setLink(Constant.PREFIX_LINK_UPLOAD +
                    newFilename);
        } catch (Exception e) {
            result.setHttp(500);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

}
