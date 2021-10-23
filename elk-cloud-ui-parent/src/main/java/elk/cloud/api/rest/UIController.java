package elk.cloud.api.rest;

import com.rabbitmq.client.Channel;
import elk.cloud.api.dto.SearchBingRequest;
import elk.cloud.api.dto.SearchDevLogRequest;
import elk.cloud.api.feignclient.BaseDataClient;
import elk.cloud.api.messagequeue.rabbitmq.config.RabbitMqConfig;
import elk.cloud.api.vo.SearchBingResponse;
import elk.cloud.api.vo.SearchDevLogResponse;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    @RabbitListener(queues = {RabbitMqConfig.QUEUE_INFORM_EMAIL})
    public void rabbitMqListener(Object msg, Message message, Channel channel){
        System.out.println(msg);
    }
}
