package elk.cloud.api.service;

import elk.cloud.api.dto.SearchDTO;
import elk.cloud.api.dto.WriteRequest;
import elk.cloud.api.vo.SearchVO;
import elk.cloud.api.vo.WriteResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ElasticSearchOperationService {

    @PostMapping("/write")
    WriteResponse wrtie(@RequestBody WriteRequest request) throws Exception;

    @PostMapping("/search")
    SearchVO search(@RequestBody SearchDTO dto);
}
