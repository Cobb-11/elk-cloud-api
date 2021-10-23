package elk.cloud.api.rest;

import com.rabbitmq.client.Channel;
import elk.cloud.api.service.CollectDataService;
import elk.cloud.api.messagequeue.rabbitmq.config.RabbitMqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    //监听email队列
    @RabbitListener(queues = {RabbitMqConfig.QUEUE_INFORM_EMAIL})
    public void receive_email(Object msg, Message message, Channel channel){
        System.out.println("QUEUE_INFORM_EMAIL msg"+msg);
    }
    //监听sms队列
    @RabbitListener(queues = {RabbitMqConfig.QUEUE_INFORM_SMS})
    public void receive_sms(Object msg, Message message, Channel channel){
        System.out.println("QUEUE_INFORM_SMS msg"+msg);
    }
}
