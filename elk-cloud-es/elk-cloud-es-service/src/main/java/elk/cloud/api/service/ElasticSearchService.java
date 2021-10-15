package elk.cloud.api.service;

import elk.cloud.api.utils.JsonWrapperMapper;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class ElasticSearchService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestHighLevelClient esRestClient;

    public ElasticSearchService(RestHighLevelClient esRestClient) {
        this.esRestClient = esRestClient;
    }

    public void putData2Es(Object data) throws Exception {
        String source= JsonWrapperMapper.toString(data);
        logger.info(source);
        List<IndexRequest> resutList = new ArrayList<>();
        IndexRequest request = new IndexRequest();
        request.source(source);
        resutList.add(request);

        this.writeToEs("bing",resutList);

    }

    public BulkResponse writeToEs(String index, List<IndexRequest> resutList) throws IOException {
        BulkRequest request =new BulkRequest();

        resutList.forEach(result->{
            IndexRequest indexRequest = new IndexRequest();

            indexRequest.index(index);
            indexRequest.source(JsonWrapperMapper.toString(result), XContentType.JSON);
            request.add(result);
        });


        BulkResponse response = null;
        //RestHighLevelClient esRestClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
        try {

            logger.info("请求es，执行添加数据操作，入参：{}",JsonWrapperMapper.toString(request));

            response = esRestClient.bulk(request, RequestOptions.DEFAULT);
            logger.info("es响应结果:{}",JsonWrapperMapper.toString(response));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        esRestClient.close();

        return response;
    }

    /**
     * 查询es
     * @param queryMap 查询参数
     * @return 结果
     */
    public List<Map<String,Object>> search( String index ,Map<String,Object> queryMap) {
        List<Map<String,Object>> resultList = new ArrayList<>();
        SearchResponse response = null;
        SearchRequest request = new SearchRequest(index);
        request.indices(index);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        for (String key : queryMap.keySet()) {
            builder.query(QueryBuilders.matchQuery(key,queryMap.get(key)));
        }
        request.source(builder);
        try {
            logger.info("查询elasticsearch入参：{}", JsonWrapperMapper.toString(queryMap));
            response = esRestClient.search(request,RequestOptions.DEFAULT);
            logger.info("查询elasticsearch返回：{}",JsonWrapperMapper.toString(response));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for(SearchHit searchHit : searchHits){
            Map<String,Object> map =  searchHit.getSourceAsMap();
            resultList.add(map);
        }
        return resultList;
    }
}
