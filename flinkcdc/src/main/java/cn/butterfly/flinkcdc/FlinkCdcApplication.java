package cn.butterfly.flinkcdc;

import cn.butterfly.flinkcdc.properties.CheckPointProperties;
import cn.butterfly.flinkcdc.properties.DataSourceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 启动类
 *
 * @author zjw
 * @date 2023-03-13
 */
@SpringBootApplication
@EnableConfigurationProperties({CheckPointProperties.class, DataSourceProperties.class})
public class FlinkCdcApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlinkCdcApplication.class, args);
    }

}
