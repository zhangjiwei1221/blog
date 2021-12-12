package cn.butterfly.tree.base;

import cn.butterfly.tree.constant.BaseConstants;
import lombok.Data;

/**
 * 控制器返回结果类
 *
 * @author zjw
 * @date 2021-12-11
 */
@Data
public class BaseResult<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 返回成功
     *
     * @return 结果
     */
    public static <T> BaseResult<T> success() {
        return success(null);
    }

    /**
     * 返回成功
     *
     * @param data 返回数据
     * @return 结果
     */
    public static <T> BaseResult<T> success(T data) {
        return success(null, data);
    }

    /**
     * 返回成功
     *
     * @param msg 成功信息
     * @param data 返回数据
     * @return 结果
     */
    public static <T> BaseResult<T> success(String msg, T data) {
        return get(BaseConstants.SUCCESS_CODE, msg, data);
    }

    /**
     * 返回错误
     *
     * @param msg 错误信息
     * @return 结果
     */
    public static <T> BaseResult<T> error(String msg) {
        return error(BaseConstants.ERROR_CODE, msg);
    }

    /**
     * 返回错误
     *
     * @param code 错误码
     * @param msg 错误信息
     * @return 结果
     */
    public static <T> BaseResult<T> error(int code, String msg) {
        return get(code, msg, null);
    }

    /**
     * 获取返回信息实体
     *
	 * @param code 状态码
	 * @param msg 信息
	 * @param data 数据
     * @return 结果
     */
    private static <T> BaseResult<T> get(int code, String msg, T data) {
        BaseResult<T> result = new BaseResult<>();
        result.setCode(code);
        result.setMessage(msg);
        result.setData(data);
        return result;
    }

}
