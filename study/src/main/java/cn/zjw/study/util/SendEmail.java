package cn.zjw.study.util;

import cn.zjw.study.service.EmailService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * SendEmail
 *
 * @author zjw
 * @createTime 2021/2/6 21:52
 */
@Component
@Configurable
@EnableScheduling
public class SendEmail {

    @Scheduled(cron = "0 0,58 0,21 6 2 ? ")
    public void send() throws Exception {
        new EmailService().sendMail("1945192314@qq.com", "", "1945192314@qq.com");
    }
}
