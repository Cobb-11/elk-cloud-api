package elk.cloud.api.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * es配置服务
 *
 * @author Cobb
 *
 * @since 2021年8月29日14:58:04
 */
@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.host}")
    private String HOST;

    @Value("${elasticsearch.port}")
    private Integer PORT ;

    @Value("${elasticsearch.schema}")
    private String SCHEMA;



    @Bean(destroyMethod = "close")
    public RestHighLevelClient esRestClient(){
        RestHighLevelClient client= new RestHighLevelClient(RestClient.builder(new HttpHost(HOST, PORT, SCHEMA)));

        return client;
    }

}
