package elk.cloud.api.feignclient;

import elk.cloud.api.service.ElasticSearchOperationService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "es-server")
public interface EsOperationServiceClient extends ElasticSearchOperationService{
}
