package cn.butterfly.encryption.interceptor;

import cn.butterfly.encryption.util.SQLUtils;
import cn.butterfly.encryption.util.ServletUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.http.HttpServletRequest;

/**
 * 加密更新拦截器处理
 *
 * @author zjw
 * @date 2023-10-27
 */
@Configuration
public class EncryptUpdateInterceptor implements InnerInterceptor {
    
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new EncryptUpdateInterceptor());
        return interceptor;
    }

    @Override
    public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) {
        HttpServletRequest request = ServletUtils.getRequest();
        if ("/method5".equals(request.getServletPath())) {
            SQLUtils.handleSql(ms.getConfiguration(), ms.getBoundSql(parameter));
        }
    }

}