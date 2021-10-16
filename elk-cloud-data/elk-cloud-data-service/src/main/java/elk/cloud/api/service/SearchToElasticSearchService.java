package elk.cloud.api.service;

import elk.cloud.api.dto.SearchDTO;
import elk.cloud.api.dto.SearchDevLogDTO;
import elk.cloud.api.feignclient.EsOperationServiceClient;
import elk.cloud.api.utils.DateUtils;
import elk.cloud.api.utils.JsonWrapperMapper;
import elk.cloud.api.vo.SearchDevLogVO;
import elk.cloud.api.vo.SearchVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String date = format.format(new Date());
        searchDTO.setIndex("logstash-elk-"+date);
        Map<String,Object > queryMap = JsonWrapperMapper.fromString(JsonWrapperMapper.toString(dto),Map.class);
        searchDTO.setQueryMap(queryMap);
        logger.info("查询开发日志入参：{}", JsonWrapperMapper.toString(searchDTO));
        SearchVO search = esOperationServiceClient.search(searchDTO);

        logger.info("查询开发日志返回：{}", JsonWrapperMapper.toString(search.getResultList()));

        for (Map<String, Object> map : search.getResultList()) {
            SearchDevLogVO vo = new SearchDevLogVO();
            vo.setLevel((String) map.get("level"));
            vo.setTimestamp(DateUtils.formatDate(DateUtils.formatUTCDate((String) map.get("@timestamp")),"yyyy-MM-dd HH:mm:ss SSS"));
            vo.setHost((String) map.get("host"));
            vo.setPort(String.valueOf( map.get("port")));
            vo.setMessage((String) map.get("message"));
            voList.add(vo);
        }

        return voList;

    }
}
