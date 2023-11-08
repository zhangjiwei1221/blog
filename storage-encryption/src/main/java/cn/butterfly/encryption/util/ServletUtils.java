package cn.butterfly.encryption.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

/**
 * 请求工具类
 *
 * @author ruoyi
 * @date 2023-11-07
 */
public class ServletUtils {

	private ServletUtils() {}

	/**
	 * 获取 request
	 *
	 * @return request 对象
	 */
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes requestAttributes = getRequestAttributes();
		if (requestAttributes == null) {
			throw new RuntimeException("获取请求信息失败");
		}
		return requestAttributes.getRequest();
	}

	/**
	 * 获取请求属性
	 *
	 * @return 请求属性
	 */
	public static ServletRequestAttributes getRequestAttributes() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		return (ServletRequestAttributes) attributes;
	}

}
