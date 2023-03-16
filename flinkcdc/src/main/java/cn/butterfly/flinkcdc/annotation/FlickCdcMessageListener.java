package cn.butterfly.flinkcdc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * FlickCdc 消息监听注解
 *
 * @author zjw
 * @date 2023-03-14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlickCdcMessageListener {

    /**
     * 表名
     */
    String value();

}
