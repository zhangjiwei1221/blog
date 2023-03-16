package cn.butterfly.back.exception;

import cn.butterfly.back.constant.BaseConstants;
import lombok.Getter;
import lombok.ToString;

/**
 * 自定义接口异常类
 *
 * @author zjw
 * @date 2021-12-18
 */
@Getter
@ToString
public class ApiException extends RuntimeException {

	/**
	 * 状态码
	 */
	private final int code;

	/**
	 * 错误信息
	 */
	private final String message;

	public ApiException(String message) {
		this.code = BaseConstants.ERROR_CODE;
		this.message = message;
	}

}
