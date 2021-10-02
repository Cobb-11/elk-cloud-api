package elk.cloud.api.rest;

import elk.cloud.api.service.CollectDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private CollectDataService collectDataService;

    @RequestMapping("/collectData2ES")
    public void collectData2ES() throws Exception {
        collectDataService.sendToEs();
    }
}
