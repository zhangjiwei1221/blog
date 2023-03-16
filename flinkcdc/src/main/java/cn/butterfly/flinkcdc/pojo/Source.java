package cn.butterfly.flinkcdc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Source 类
 *
 * @author zjw
 * @date 2023-03-16
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Source {

    /**
     * 表名
     */
    private String table;

}
