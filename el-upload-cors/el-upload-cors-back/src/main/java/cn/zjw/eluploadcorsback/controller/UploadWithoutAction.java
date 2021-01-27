package cn.zjw.eluploadcorsback.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

/**
 * UploadWithoutAction
 *
 * @author zjw
 * @createTime 2021/1/27 10:47
 */
@RestController
public class UploadWithoutAction {

    @Value("${upload.filePath}")
    private String filePath;

    @PostMapping("/uploadWithoutAction")
    public String upload(@RequestPart("file") MultipartFile file) {
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
