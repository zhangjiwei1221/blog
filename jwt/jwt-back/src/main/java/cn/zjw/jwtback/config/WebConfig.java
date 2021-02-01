package cn.zjw.jwtback.config;

import cn.zjw.jwtback.filter.HttpInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.MappedInterceptor;


@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

	private final HttpInterceptor httpInterceptor;

	@Autowired
	public WebConfig(HttpInterceptor httpInterceptor) {
		this.httpInterceptor = httpInterceptor;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 跨域相关配置, 并让 authorization 可在响应头中出现
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("POST", "GET", "PUT", "PATCH", "OPTIONS", "DELETE")
				.exposedHeaders("authorization")
				.allowedHeaders("*")
				.maxAge(3600);
	}

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		// 设置自定义的拦截器, 拦截所有界面
		// 排除 /login 请求, 未防止 /login 失效, 将 /error 也加入
		registry.addInterceptor(httpInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/login", "/error");
		super.addInterceptors(registry);
	}

}
