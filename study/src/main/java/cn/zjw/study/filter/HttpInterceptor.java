package cn.zjw.study.filter;

import cn.zjw.study.entity.ResultData;
import cn.zjw.study.util.JwtUtil;
import com.auth0.jwt.interfaces.Claim;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

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
        return true;
//        if (request.getMethod().equals("OPTIONS")) {
//            response.setStatus(HttpServletResponse.SC_OK);
//            return true;
//        }
//        String token = request.getHeader("Authorization");
//        if (token != null) {
//            if (JwtUtil.verifyToken(token)) {
//                return true;
//            }
//        }
//        ResultData<String> resultData = new ResultData<>();
//        resultData.setMsg("token verify failed!");
//        resultData.setCode(-1);
//        response.getWriter().write(gson.toJson(resultData));
//        return false;
    }

}
