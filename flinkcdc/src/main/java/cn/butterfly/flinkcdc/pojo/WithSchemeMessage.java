package cn.butterfly.flinkcdc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 包含 scheme 消息
 *
 * @author zjw
 * @date 2023-03-16
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WithSchemeMessage<T> {

    /**
     * payload
     */
    private Payload<T> payload;

}
