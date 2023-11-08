package cn.butterfly.encryption.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法加密处理注解
 *
 * @author zjw
 * @date 2023-11-07
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface EncryptMethod {

	/**
	 * 需要处理对象在参数列表中的位置
	 */
	int[] value() default { 0 };

	/**
	 * 是否启用解密处理
	 */
	boolean enableDecrypt() default true;

}