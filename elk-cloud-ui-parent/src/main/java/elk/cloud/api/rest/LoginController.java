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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO dto, HttpServletRequest request) throws Exception {
        LoginVO loginVO = loginService.login(dto);

        HttpSession session = request.getSession();
        logger.info("登录session：{}", JsonWrapperMapper.toString(session));

        if(StringUtils.equals(loginVO.getStatus(),LoginVO.SUCCESS)){
            session.setAttribute("username",dto.getUsername());
        }else {
            throw new Exception(loginVO.getMessage());
        }
        return "index.html";
    }
}
