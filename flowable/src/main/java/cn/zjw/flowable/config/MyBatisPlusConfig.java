package cn.zjw.flowable.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatisPlusConfig
 *
 * @author zjw
 * @date 2021/3/19
 */
@Configuration
@MapperScan("cn.zjw.flowable.mapper")
public class MyBatisPlusConfig {
}
