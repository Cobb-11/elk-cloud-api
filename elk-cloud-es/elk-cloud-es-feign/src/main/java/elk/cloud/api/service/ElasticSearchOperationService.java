package elk.cloud.api.service;

import elk.cloud.api.dto.WriteRequest;
import elk.cloud.api.vo.WriteResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

public interface ElasticSearchOperationService {

    @PostMapping("/write")
    @ResponseBody
    WriteResponse wrtie(@RequestBody WriteRequest request) throws Exception;
}
