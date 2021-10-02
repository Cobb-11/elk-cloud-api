package elk.cloud.api.rest;

import elk.cloud.api.dto.SearchBingRequest;
import elk.cloud.api.feignclient.BaseDataClient;
import elk.cloud.api.vo.SearchBingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UIController {

    @Autowired
    private BaseDataClient client;

    @GetMapping("/getData")
    public String getData(){
        return client.getEsIndex();
    }

    @PostMapping("/search")
    @ResponseBody
    public SearchBingResponse search(@RequestBody SearchBingRequest request){
        return client.searchBing(request);
    }
}
