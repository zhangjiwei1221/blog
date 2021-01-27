package cn.zjw.eluploadcorsback.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * UploadByAction
 *
 * @author zjw
 * @createTime 2021/1/27 09:40
 */
@RestController
public class UploadByAction {

    @Value("${upload.filePath}")
    private String filePath;

    @PostMapping("/nginx/uploadByAction")
    public String uploadByNginx(@RequestPart("file") MultipartFile file) {
        return getUploadMsg(file);
    }

    @PostMapping("/springboot/uploadByAction")
    public String uploadBySpringBoot(@RequestPart("file") MultipartFile file) {
        return getUploadMsg(file);
    }

    private String getUploadMsg(@RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

}
