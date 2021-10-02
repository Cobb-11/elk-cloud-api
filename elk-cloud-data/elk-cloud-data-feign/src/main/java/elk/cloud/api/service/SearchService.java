package elk.cloud.api.service;

import elk.cloud.api.dto.SearchBingRequest;
import elk.cloud.api.vo.SearchBingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public interface SearchService {

    @GetMapping("/getEsIndex")
    String getEsIndex();

    @RequestMapping("/searchBing")
    @ResponseBody
    SearchBingResponse searchBing(@RequestBody SearchBingRequest request);
}
