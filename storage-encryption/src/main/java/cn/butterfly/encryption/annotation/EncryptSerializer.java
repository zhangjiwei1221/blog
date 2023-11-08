package cn.butterfly.encryption.annotation;

import cn.butterfly.encryption.config.DecryptSerializer;
import cn.butterfly.encryption.config.EncryptDeserializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段加解密序列化注解
 *
 * @author zjw
 * @date 2023-10-23
 */
@JsonSerialize(using= DecryptSerializer.class)
@JsonDeserialize(using= EncryptDeserializer.class)
@JacksonAnnotationsInside
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptSerializer {
}
