package cn.butterfly.flinkcdc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.debezium.data.Envelope;
import lombok.Data;

/**
 * Payload 类
 *
 * @author zjw
 * @date 2023-03-16
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload<T> {

    /**
     * source
     */
    private Source source;

    /**
     * 修改前
     */
    private T before;

    /**
     * 修改后
     */
    private T after;

    /**
     * 操作
     */
    private Envelope.Operation op;

}
