package cn.butterfly.encryption.interceptor;

import cn.butterfly.encryption.util.AESUtils;
import cn.butterfly.encryption.util.SQLUtils;
import cn.butterfly.encryption.util.ServletUtils;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 加密查询拦截器处理
 *
 * @author zjw
 * @date 2023-11-08
 */
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
})
public class EncryptQueryInterceptor implements Interceptor {
    
    @Override
    public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String sqlId = mappedStatement.getId();
        if (sqlId.contains("selectCount")) {
            return invocation.proceed();
        }
        Object proceed = invocation.proceed();
        HttpServletRequest request = ServletUtils.getRequest();
        if (!"/method5".equals(request.getServletPath())) {
            return proceed;
        }
        @SuppressWarnings("unchecked")
        List<Object> objectList = (List<Object>) proceed;
        if (objectList.isEmpty()) {
            return proceed;
        }
        Class<?> type = objectList.get(0).getClass();
        List<Object> resultList = new ArrayList<>();
        for (Object o : objectList) {
            Map<String, Object> map = JSONUtil.toBean(JSONUtil.toJsonStr(o), new TypeReference<Map<String, Object>>() {}, true);
            for (String keyword : SQLUtils.ENCRYPT_SET) {
                map.put(keyword, AESUtils.decrypt(String.valueOf(map.get(keyword))));
            }
            resultList.add(JSONUtil.toBean(JSONUtil.toJsonStr(map), type));
        }
        return resultList;
    }

}
