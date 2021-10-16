package elk.cloud.api.rest;

import elk.cloud.api.dto.SearchBingRequest;
import elk.cloud.api.dto.SearchDevLogRequest;
import elk.cloud.api.feignclient.BaseDataClient;
import elk.cloud.api.vo.SearchBingResponse;
import elk.cloud.api.vo.SearchDevLogResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UIController {

    private final BaseDataClient client;

    public UIController(BaseDataClient client) {
        this.client = client;
    }

    @GetMapping("/getData")
    public String getData(){
        return client.getEsIndex();
    }

    @PostMapping("/search")
    public SearchBingResponse search(@RequestBody SearchBingRequest request){
        return client.searchBing(request);
    }

    @PostMapping("/searchDevLog")
    public List<SearchDevLogResponse> searchDevLog(@RequestBody SearchDevLogRequest request){
        return client.searchDevLog(request);
    }
}
