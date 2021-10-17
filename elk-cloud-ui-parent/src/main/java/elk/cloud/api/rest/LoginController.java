package elk.cloud.api.rest;

import elk.cloud.api.service.LoginService;
import elk.cloud.api.service.dto.LoginDTO;
import elk.cloud.api.service.vo.LoginVO;
import elk.cloud.api.utils.JsonWrapperMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ResponseBody
    public LoginVO login(@RequestBody LoginDTO dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LoginVO loginVO = loginService.login(dto);

        HttpSession session = request.getSession();
        logger.info("登录session：{}", JsonWrapperMapper.toString(session));

        if(StringUtils.equals(loginVO.getStatus(),LoginVO.SUCCESS)){
            session.setAttribute("username",dto.getUsername());
        }else {
            throw new Exception(loginVO.getMessage());
        }
        String type = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(type)) {
            //处理AJAX请求，设置响应头信息
            response.setHeader("REDIRECT", "REDIRECT");
            //需要跳转页面的URL
            response.setHeader("CONTEXTPATH", "/index.html");
        }else {
            response.sendRedirect("/index.html");
        }
        return loginVO;
    }
}
