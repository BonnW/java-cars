package consumers;

import domains.carsdemo.CarsdemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CarConsumer
{
    @RabbitListener(queues = CarsdemoApplication.QUEUE_NAME)
    public void consumeMessage(final Message cm)
    {
        log.info("Looked up cars: ", cm.toString());
    }
}
