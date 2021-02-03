package cn.zjw.chatback.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * ResultData
 *
 * @author zjw
 * @createTime 2021/1/29 16:16
 */
@Data
@NoArgsConstructor
public class ResultEntity<T> {

    private T data;
    private boolean status = true;
    private String errMsg = "";

    public ResultEntity(T data) {
        this.data = data;
    }
}
