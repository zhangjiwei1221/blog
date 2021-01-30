package cn.zjw.jwtback.filter;

import cn.zjw.jwtback.entity.ResultEntity;
import cn.zjw.jwtback.util.JwtUtil;
import cn.zjw.jwtback.util.UserSecurityUtil;
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
    private final UserSecurityUtil userSecurityUtil;

    @Autowired
    public HttpInterceptor(Gson gson, UserSecurityUtil userSecurityUtil) {
        this.gson = gson;
        this.userSecurityUtil = userSecurityUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        boolean isOk = userSecurityUtil.verifyWebToken(request, response);

        if (!isOk) {
            ResultEntity<String> resultEntity = new ResultEntity<>();
            resultEntity.setErrMsg("请重新登录");
            resultEntity.setStatus(false);
            response.getWriter().write(gson.toJson(resultEntity));
            return false;
        }
        return true;
    }

}
