package cn.butterfly.tree.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import static cn.butterfly.tree.constant.BaseConstants.ALL_ORIGIN;
import static cn.butterfly.tree.constant.BaseConstants.ALL_PATTERN;
import static org.springframework.http.HttpMethod.*;

/**
 * web 配置
 *
 * @author zjw
 * @date 2021-09-09
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

	/**
	 * 跨域配置
	 *
	 * @param registry 跨域配置 registry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping(ALL_PATTERN)
				.allowedOrigins(ALL_ORIGIN)
				.allowedMethods(GET.name(), POST.name(), DELETE.name(), HttpMethod.OPTIONS.name())
				.allowedHeaders(ALL_ORIGIN);
	}

}
