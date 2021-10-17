package elk.cloud.api.job.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ElasticJobListenerImpl implements ElasticJobListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private long beginTime = 0;

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        beginTime = System.currentTimeMillis();
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        long endTime = System.currentTimeMillis();

        logger.info("{}结束,耗时：{}ms。",shardingContexts.getJobName(),endTime-beginTime);
    }
}
