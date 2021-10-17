package elk.cloud.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录拦截器
 *
 * @since 2021年10月17日18:07:54
 * @author Cobb
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 用于存储排除拦截的url  （登录/login.html, /css,/js,/img）
     */
    private List<String> uris  = new ArrayList<>();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {
        HttpSession session = request.getSession();

        if(uris.contains(request.getRequestURI())){
            logger.info("无需拦截{}，放行！",request.getRequestURI());
            return true;
        }
        if (session.getAttribute("username") != null) {
            // 已登录，放行。。
            return true;
        } else {
            logger.info("未权限");
            // 未登录，拦截 返回login
            response.sendRedirect("/login.html");
            return false;
        }
    }

    public List<String> getUris() {
        uris.add("/login.html");
        uris.add("/login");

        uris.add("/swagger-ui.html");

        uris.add("/js/*");
        uris.add("/css/*");
        return uris;
    }

    public void setUris(List<String> uris) {
        this.uris = uris;
    }
}
