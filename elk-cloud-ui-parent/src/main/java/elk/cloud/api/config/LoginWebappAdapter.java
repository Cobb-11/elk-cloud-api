package elk.cloud.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册拦截器配置类
 *
 * @author Cobb
 *
 * @since 2021年10月17日18:19:35
 */
@Configuration
public class LoginWebappAdapter implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        LoginInterceptor loginInterceptor = new LoginInterceptor();

        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/swagger-resources/**").excludePathPatterns(loginInterceptor.getUris());

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
