package com.inventory.Inventory.kafka;

import com.base.base.dto.OrderEventDTO;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);
@KafkaListener(
        topics = "${spring.kafka.template.default-topic}",
        groupId = "${spring.kafka.consumer.group-id}"
)
    public void consumer(OrderEventDTO orderEventDTO) {
    LOGGER.info("consume orderEventDTO: {}",orderEventDTO.toString());
}

}
