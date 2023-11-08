package cn.butterfly.encryption.util;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * sql 工具类
 *
 * @author zjw
 * @date 2023-11-08
 */
public class SQLUtils {
    
    public static final Set<String> ENCRYPT_SET = new HashSet<>(Arrays.asList("username", "password"));
    
    private SQLUtils() {}
    
    /**
     * 处理 sql
     *
	 * @param configuration 配置
	 * @param boundSql sql
     */
    public static void handleSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings.isEmpty() || parameterObject == null) {
           return;
        }
        MetaObject metaObject = configuration.newMetaObject(parameterObject);
        for (ParameterMapping parameterMapping : parameterMappings) {
            String propertyName = parameterMapping.getProperty().toLowerCase();
            Object value = metaObject.getValue(propertyName);
            if (ENCRYPT_SET.contains(propertyName.substring(propertyName.indexOf(".") + 1))) {
                metaObject.setValue(propertyName, AESUtils.encrypt(String.valueOf(value)));
            }
        }
    }
    
}
