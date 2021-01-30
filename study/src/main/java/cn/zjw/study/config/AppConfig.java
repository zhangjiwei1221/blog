package cn.zjw.study.config;

import com.google.gson.Gson;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig
 *
 * @author zjw
 * @createTime 2021/1/29 17:09
 */
@Configuration
public class AppConfig {

    @Bean
    public Gson gson() {
        return new Gson();
    }

}
