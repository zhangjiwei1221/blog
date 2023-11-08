package cn.butterfly.encryption.config;

import cn.butterfly.encryption.util.AESUtils;
import cn.butterfly.encryption.util.ServletUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 解密序列化处理器
 *
 * @author zjw
 * @date 2023-11-07
 */
@NoArgsConstructor
public class DecryptSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        HttpServletRequest request = ServletUtils.getRequest();
        if ("/method3".equals(request.getServletPath()) && StringUtils.isNotBlank(value)) {
			value = AESUtils.decrypt(value);
		}
        jsonGenerator.writeString(value);
    }

}
