package elk.cloud.api.aspect;

import elk.cloud.api.utils.JsonWrapperMapper;
import elk.cloud.api.utils.XmlWrapperMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * aop收集接口日志
 */
@Aspect
@Component
public class InterfaceLogAspect {


    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void logPointCut(){

    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        RequestContent requestContent = new RequestContent();
        if(requestAttributes != null){
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

            requestContent.setIp(request.getRemoteAddr());

            requestContent.setUrl(request.getRequestURL().toString());

            requestContent.setClassMethod(request.getMethod());
        }

        requestContent.setArgs( XmlWrapperMapper.toXml(joinPoint.getArgs()));

        logger.info("接口{}入参：{}",requestContent.getClassMethod(),JsonWrapperMapper.toString(requestContent));
    }

    @AfterReturning(returning = "response",pointcut = "logPointCut()")
    public void doAfterReturning(Object response){
        logger.info("接口返回：{}",XmlWrapperMapper.toXml(response));
    }
}
