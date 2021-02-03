package cn.zjw.chatback.config;

import cn.zjw.chatback.entity.ResultEntity;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Configuration
@RestControllerAdvice
class ResultDataAdvice implements ResponseBodyAdvice<Object> {

    private final Gson gson;

    @Autowired
    public ResultDataAdvice(Gson gson) {
        this.gson = gson;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (body instanceof ResultEntity) {
            return body;
        }
        if (body instanceof String) {
            return gson.toJson(new ResultEntity<>(body));
        }
        return new ResultEntity<>(body);
    }

}
