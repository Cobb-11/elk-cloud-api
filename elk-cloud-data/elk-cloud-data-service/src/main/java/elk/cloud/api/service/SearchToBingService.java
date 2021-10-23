package elk.cloud.api.service;

import elk.cloud.api.bean.SearchBingResut;
import elk.cloud.api.cache.redis.manage.RedisManage;
import elk.cloud.api.dto.WriteRequest;
import elk.cloud.api.feignclient.EsOperationServiceClient;
import elk.cloud.api.utils.JsonWrapperMapper;
import elk.cloud.api.utils.SearchTypeEnum;
import elk.cloud.api.utils.SearchUtil;
import elk.cloud.api.messagequeue.rabbitmq.config.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchToBingService {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());


    private final EsOperationServiceClient esOperationServiceClient;

    private final RedisManage redisManage;

    private final RabbitTemplate rabbitTemplate;

    public SearchToBingService(EsOperationServiceClient esOperationServiceClient, RedisManage redisManage, RabbitTemplate rabbitTemplate) {
        this.esOperationServiceClient = esOperationServiceClient;
        this.redisManage = redisManage;
        this.rabbitTemplate = rabbitTemplate;
    }


    public  List<SearchBingResut> searchBing(String keyWord,String type) throws IOException {
        logger.info("搜索引擎：{}，搜索关键字：{}", SearchTypeEnum.getSearchTypeByEngin(type).getName(),keyWord);

        List<SearchBingResut> resutList = new SearchUtil().search(keyWord,type);

        logger.info("搜索结果：{}", JsonWrapperMapper.toString(resutList));

        redisManage.set("searchBing_"+keyWord,resutList,3600);

        logger.info("redis:{}",redisManage.get("searchBing_"+keyWord));

        try {
            rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_TOPICS_INFORM,"inform.email", keyWord+"-"+type);

            WriteRequest request = new WriteRequest();
            request.set_index(type);
            request.set_id(keyWord);
            List<String> sources= new ArrayList<>();
            if(!CollectionUtils.isEmpty(resutList)){
                resutList.forEach(searchBingResut -> {
                    sources.add(JsonWrapperMapper.toString(searchBingResut));
                });
            }
            request.set_source(sources);
            esOperationServiceClient.wrtie(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return resutList;

    }



}
