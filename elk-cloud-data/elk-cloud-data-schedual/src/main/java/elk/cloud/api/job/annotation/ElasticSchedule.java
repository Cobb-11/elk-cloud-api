package elk.cloud.api.job.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticSchedule {

    @AliasFor("cron")
    String value() default "";


    @AliasFor("value")
    String cron() default "";

    String jobName() default "";
}
