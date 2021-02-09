package cn.zjw.study.controller;

import cn.zjw.study.entity.SendEmailEntity;
import cn.zjw.study.service.EmailService;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SendEmailController
 *
 * @author zjw
 * @createTime 2021/2/7 18:27
 */
@RestController
public class SendEmailController {

    private final EmailService service;

    @Autowired
    public SendEmailController(EmailService service) {
        this.service = service;
    }

    @PostMapping("/sendEmail")
    public void sendEmail(@RequestParam("date") String dateO, @RequestParam("email") String email) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateO);
        System.out.println(date);
        Runnable task = () -> {
            try {
                service.sendMail("1945192314@qq.com", "", email);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        scheduler.schedule(task, date);
    }

}
