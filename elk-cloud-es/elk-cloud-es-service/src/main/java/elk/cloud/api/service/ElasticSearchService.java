package elk.cloud.api.service;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ElasticSearchService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestHighLevelClient esRestClient;

    public void putData2Es(Object data) throws Exception {
        String source=JSON.toJSONString(data);
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
            indexRequest.source(JSON.toJSONString(result), XContentType.JSON);
            request.add(result);
        });


        BulkResponse response = null;
        //RestHighLevelClient esRestClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
        try {

            logger.info("请求es，执行添加数据操作，入参：{}",JSON.toJSONString(request));

            response = esRestClient.bulk(request, RequestOptions.DEFAULT);
            logger.info("es响应结果:{}",JSON.toJSONString(response));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        esRestClient.close();

        return response;
    }
}
