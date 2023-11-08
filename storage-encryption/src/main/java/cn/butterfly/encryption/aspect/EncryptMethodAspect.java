package cn.butterfly.encryption.aspect;

import cn.butterfly.encryption.annotation.EncryptField;
import cn.butterfly.encryption.annotation.EncryptMethod;
import cn.butterfly.encryption.util.AESUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.function.UnaryOperator;

/**
 * 处理加密注解切面
 * 特别注意, 这里的排序需要 + 1, 否则会报错, 具体原因参考链接: 
 * <a href="https://blog.csdn.net/qq_18300037/article/details/128626005">...</a>
 *
 * @author zjw
 * @date 2023-11-07
 */
@Slf4j
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class EncryptMethodAspect {

	/**
	 * 处理加密方法注解
	 *
	 * @param joinPoint 切点
	 * @param encryptMethod 加密方法注解
	 * @return 结果
	 */
	@Around("@annotation(encryptMethod)")
    public Object around(ProceedingJoinPoint joinPoint, EncryptMethod encryptMethod) throws Throwable {
        try {
			int[] indexArr = encryptMethod.value();
			Object[] args = joinPoint.getArgs();
			for (int i = 0; i < indexArr.length; i++) {
				if (i >= args.length) {
					break;
				}
				handleEncrypt(args[i]);
			}
            Object result = joinPoint.proceed();
			if (encryptMethod.enableDecrypt()) {
				return handleDecrypt(result);
			}
			return result;
        } catch (Throwable throwable) {
            log.error("加密注解处理出现异常", throwable);
			throw throwable;
        }
    }

	/**
	 * 对添加了 EncryptField 注解的字段进行加密
	 *
	 * @param obj 要处理的对象
	 */
	private void handleEncrypt(Object obj) throws IllegalAccessException {
		handleEnDecrypt(obj, AESUtils::encrypt);
	}

	/**
	 * 对添加了 EncryptField 注解的字段进行解密, <b>只考虑了返回值是对象的情况</b>
	 *
	 * @param obj 要处理的对象
	 * @return 结果
	 */
	private Object handleDecrypt(Object obj) throws IllegalAccessException {
		return handleEnDecrypt(obj, AESUtils::decrypt);
	}
	
	/**
	 * 对添加了 EncryptField 注解的字段进行加解密处理
	 *
	 * @param obj 要处理的对象
	 * @param handleFun 处理函数
	 * @return 结果
	 */
	private Object handleEnDecrypt(Object obj, UnaryOperator<String> handleFun) throws IllegalAccessException {
		if (obj == null) {
            return null;
        }
		Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean hasSecureField = field.isAnnotationPresent(EncryptField.class);
            if (hasSecureField) {
                field.setAccessible(true);
                String val = (String) field.get(obj);
                String result = handleFun.apply(val);
                field.set(obj, result);
            }
        }
		return obj;
	}

}
