package cn.zjw.chatback.config;

import cn.zjw.chatback.filter.HttpInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 * @author zjw
 */
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

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")
				.addResourceLocations("classpath:/static/");
		super.addResourceHandlers(registry);
	}

}
