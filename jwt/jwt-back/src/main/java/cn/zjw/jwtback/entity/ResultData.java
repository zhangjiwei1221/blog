package cn.zjw.jwtback.entity;

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
public class ResultData<T> {

    private T data;
    private int code = 1;
    private String msg = "success";

    public ResultData(T data) {
        this.data = data;
    }
}
