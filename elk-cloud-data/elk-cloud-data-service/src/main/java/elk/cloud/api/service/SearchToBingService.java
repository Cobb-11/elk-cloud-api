package elk.cloud.api.service;

import com.alibaba.fastjson.JSON;
import elk.cloud.api.bean.SearchBingResut;
import elk.cloud.api.dto.WriteRequest;
import elk.cloud.api.feignclient.EsOperationServiceClient;
import elk.cloud.api.utils.SearchTypeEnum;
import elk.cloud.api.utils.SearchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchToBingService {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());


    @Autowired
    private EsOperationServiceClient esOperationServiceClient;


    public  List<SearchBingResut> searchBing(String keyWord,String type) throws IOException {
        logger.info("搜索引擎：{}，搜索关键字：{}", SearchTypeEnum.getSearchTypeByEngin(type).getName(),keyWord);

        List<SearchBingResut> resutList = new SearchUtil().search(keyWord,type);

        logger.info("搜索结果：{}",JSON.toJSONString(resutList));

        try {
            WriteRequest request = new WriteRequest();
            request.set_index(type);
            request.set_id(keyWord);
            List<String> sources= new ArrayList<>();
            if(!CollectionUtils.isEmpty(resutList)){
                resutList.forEach(searchBingResut -> {
                    sources.add(JSON.toJSONString(searchBingResut));
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
