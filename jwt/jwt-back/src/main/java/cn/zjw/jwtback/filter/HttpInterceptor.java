package cn.zjw.jwtback.filter;

import cn.zjw.jwtback.entity.ResultData;
import cn.zjw.jwtback.util.JwtUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zjw
 */
@Configuration
public class HttpInterceptor implements HandlerInterceptor {

    private final Gson gson;

    @Autowired
    public HttpInterceptor(Gson gson) {
        this.gson = gson;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token != null) {
            if (JwtUtil.verifyToken(token)) {
                return true;
            }
        }
        ResultData<String> resultData = new ResultData<>();
        resultData.setMsg("token verify failed!");
        resultData.setCode(-1);
        response.getWriter().write(gson.toJson(resultData));
        return false;
    }

}
