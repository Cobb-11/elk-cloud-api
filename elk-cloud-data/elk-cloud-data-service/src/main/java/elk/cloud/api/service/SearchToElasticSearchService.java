package elk.cloud.api.service;

import com.alibaba.fastjson.JSONObject;
import elk.cloud.api.dto.SearchDTO;
import elk.cloud.api.dto.SearchDevLogDTO;
import elk.cloud.api.feignclient.EsOperationServiceClient;
import elk.cloud.api.vo.SearchDevLogVO;
import elk.cloud.api.vo.SearchVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchToElasticSearchService {

    /**
     * 日志
     */
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * es服务
     */
    private final EsOperationServiceClient esOperationServiceClient;

    public SearchToElasticSearchService(EsOperationServiceClient esOperationServiceClient) {
        this.esOperationServiceClient = esOperationServiceClient;
    }

    /**
     * 查询开发日志
     * @param dto 入参
     * @return 开发日志
     */
    public List<SearchDevLogVO> searchDevLog(SearchDevLogDTO dto){
        List<SearchDevLogVO> voList = new ArrayList<>();
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setIndex("logstash-elk-2021.10.10");
        Map<String,Object > queryMap = new HashMap<>();
        BeanUtils.copyProperties(dto,queryMap);
        searchDTO.setQueryMap(queryMap);
        logger.info("查询开发日志入参：{}", JSONObject.toJSONString(dto));
        SearchVO search = esOperationServiceClient.search(searchDTO);

        logger.info("查询开发日志返回：{}",JSONObject.toJSONString(search));

        for (Map<String, Object> map : search.getResultList()) {
            SearchDevLogVO vo = new SearchDevLogVO();
            BeanUtils.copyProperties(map,vo);
            voList.add(vo);
        }

        return voList;

    }
}
