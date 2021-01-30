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
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("POST", "GET", "PUT", "PATCH", "OPTIONS", "DELETE")
				.exposedHeaders("authorization")
				.allowedHeaders("*")
				.maxAge(3600);
	}

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(httpInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/login", "/error");
		super.addInterceptors(registry);
	}

}
