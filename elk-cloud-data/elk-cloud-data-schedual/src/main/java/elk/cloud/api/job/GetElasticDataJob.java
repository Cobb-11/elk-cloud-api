package elk.cloud.api.job;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import elk.cloud.api.cache.redis.manage.RedisManage;
import elk.cloud.api.dto.SearchDevLogDTO;
import elk.cloud.api.job.annotation.ElasticSchedule;
import elk.cloud.api.service.SearchToElasticSearchService;
import elk.cloud.api.vo.SearchDevLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ElasticSchedule(cron = "0/5 * * * * ?",jobName = "获取es数据")
public class GetElasticDataJob implements SimpleJob {

    @Autowired
    private RedisManage redisManage;

    @Autowired
    private SearchToElasticSearchService searchToElasticSearchService;

    @Override
    public void execute(ShardingContext shardingContext) {
        SearchDevLogDTO dto = new SearchDevLogDTO();
        dto.setLevel("INFO");
        dto.setMessage("查询");
        List<SearchDevLogVO> voList = searchToElasticSearchService.searchDevLog(dto);

        redisManage.set("elasticsearch_",voList,3600);
    }
}
