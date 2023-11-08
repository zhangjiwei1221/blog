package cn.butterfly.encryption.config;

import cn.butterfly.encryption.util.AESUtils;
import cn.butterfly.encryption.util.ServletUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.NoArgsConstructor;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 加密序列化处理器
 *
 * @author zjw
 * @date 2023-2023-11-07
 */
@NoArgsConstructor
public class EncryptDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        HttpServletRequest request = ServletUtils.getRequest();
        if (jsonParser != null && StringUtils.isNotBlank(jsonParser.getText())) {
            String text = jsonParser.getText();
            if (!"/method3".equals(request.getServletPath())) {
                return text;
            }
			return AESUtils.encrypt(text);
		}
		return null;
    }

}
