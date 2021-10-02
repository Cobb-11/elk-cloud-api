package elk.cloud.api.feignclient;

import elk.cloud.api.service.SearchService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "basedata")
public interface BaseDataClient extends SearchService {
}
