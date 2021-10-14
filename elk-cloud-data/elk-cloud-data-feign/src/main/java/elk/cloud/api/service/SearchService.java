package elk.cloud.api.service;

import elk.cloud.api.dto.SearchBingRequest;
import elk.cloud.api.dto.SearchDevLogRequest;
import elk.cloud.api.vo.SearchBingResponse;
import elk.cloud.api.vo.SearchDevLogResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface SearchService {

    @GetMapping("/getEsIndex")
    String getEsIndex();

    @RequestMapping("/searchBing")
    SearchBingResponse searchBing(@RequestBody SearchBingRequest request);

    @PostMapping("/searchDevLog")
    List<SearchDevLogResponse> searchDevLog(@RequestBody SearchDevLogRequest request);
}
