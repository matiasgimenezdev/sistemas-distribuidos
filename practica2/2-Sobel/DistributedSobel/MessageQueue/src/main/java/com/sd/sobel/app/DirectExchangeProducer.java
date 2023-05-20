package com.sd.sobel.app;

import static com.sd.sobel.config.DirectExchangeConfig.*;

import com.sd.sobel.model.Task;
import com.sd.sobel.model.TaskRegister;
import com.sd.sobel.services.MessageLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectExchangeProducer {

  private final RabbitTemplate rabbitTemplate;

  public void sendMessage() {
    sendMsg(ROUTING_KEY_TASKS_COMPLETED);
    sendMsg(ROUTING_KEY_TASKS_REGISTER);
    sendMsg(ROUTING_KEY_TASKS_TODO);
    sendMsg("not-matching");
  }

  private void sendMsg(String routingKey) {
    Task message = new TaskRegister("id", "parts", "width", "height");
    rabbitTemplate.convertAndSend(EXCHANGE_TASKS_DIRECT, routingKey, message);
    MessageLogger.logSendMessage(EXCHANGE_TASKS_DIRECT, routingKey, message);
  }
}
