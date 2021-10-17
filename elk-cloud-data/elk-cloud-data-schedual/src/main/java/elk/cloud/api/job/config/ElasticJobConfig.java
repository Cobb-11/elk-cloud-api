package elk.cloud.api.job.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import elk.cloud.api.job.annotation.ElasticSchedule;
import elk.cloud.api.job.listener.ElasticJobListenerImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;

@Configuration
public class ElasticJobConfig {

    @Resource
    private ApplicationContext applicationContext ;
    @Resource
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    @Value("${job-config.cron}") private String cron ;
    @Value("${job-config.shardCount}") private int shardCount ;
    @Value("${job-config.shardItem}") private String shardItem ;
    @Value("${elaticjob.zookeeper.serverLists}") private String serverList;
    @Value("${elaticjob.zookeeper.namespace}") private String namespace;

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter() {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
    }

    @PostConstruct
    public void initTaskJob() {
        Map<String, SimpleJob> jobMap = this.applicationContext.getBeansOfType(SimpleJob.class);

        Iterator iterator = jobMap.entrySet().iterator();
        while (iterator.hasNext()) {
            // 自定义注解管理
            Map.Entry<String, SimpleJob> entry = (Map.Entry)iterator.next();
            SimpleJob simpleJob = entry.getValue();
            ElasticSchedule elasticSchedule = simpleJob.getClass().getAnnotation(ElasticSchedule.class);
            if (elasticSchedule != null){
                String cron = elasticSchedule.cron() ;
                String jobName = elasticSchedule.jobName() ;
                // 生成配置
                SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration( JobCoreConfiguration.newBuilder(jobName, cron,  shardCount).shardingItemParameters(shardItem).jobParameter(jobName).build(),simpleJob.getClass().getCanonicalName());
                LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build();
                ElasticJobListenerImpl taskJobListener = new ElasticJobListenerImpl();
                // 初始化任务
                SpringJobScheduler jobScheduler = new SpringJobScheduler( simpleJob, zookeeperRegistryCenter, liteJobConfiguration, taskJobListener);
                jobScheduler.init();
            }
        }
    }

}
