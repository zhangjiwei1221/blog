package cn.butterfly.back.config;

import cn.butterfly.back.mapper.SysUserMapper;
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
@MapperScan(basePackageClasses = SysUserMapper.class)
public class MybatisPlusConfig {
}
