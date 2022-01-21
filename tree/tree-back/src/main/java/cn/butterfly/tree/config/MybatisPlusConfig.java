package cn.butterfly.tree.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis 配置
 *
 * @author zjw
 * @date 2021-01-21
 */
@EnableTransactionManagement
@Configuration
@MapperScan("cn.butterfly.**.mapper.**")
public class MybatisPlusConfig {
}
