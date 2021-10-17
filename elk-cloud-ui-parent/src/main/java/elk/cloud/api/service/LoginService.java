package elk.cloud.api.service;

import elk.cloud.api.service.dto.LoginDTO;
import elk.cloud.api.service.entity.User;
import elk.cloud.api.service.impl.UserService;
import elk.cloud.api.service.vo.LoginVO;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class LoginService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    /**
     * 登录服务
     * @param dto 入参登录信息
     * @return 登录结果
     */
    public LoginVO login(LoginDTO dto){
        LoginVO  vo = new LoginVO();

        try {
            if(Objects.isNull(dto) || Objects.isNull(dto.getUsername())){
                throw new Exception("登录信息不能为空！");
            }
            String username = dto.getUsername();

            User user = userService.selectByUserName(username);

            if(Objects.isNull(user)){
                throw new Exception("用户"+username+"不存在！");
            }

            String hex = DigestUtils.md5DigestAsHex(dto.getPassword().getBytes(StandardCharsets.UTF_8));

            if(!StringUtils.equals(hex,user.getPassword())){
                throw new Exception("密码不正确！");
            }

        }catch (Exception e){
            logger.error(e.getMessage());
            vo.setStatus(LoginVO.FAIL);
            vo.setMessage(e.getMessage());
        }

        return vo;
    }
}
