package com.sd.sobel.app;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DirectExchangeConsumer {

  private final RabbitTemplate rabbitTemplate;
  private final MessageConverter messageConverter;

  public JSONObject consumeMessage(String queue) {
    return consumeMsg(queue);
  }

  private JSONObject consumeMsg(String queue) {
    Message queueMessage = rabbitTemplate.receive(queue);
    JSONObject task = new JSONObject("");
    System.out.println(queue);

    if (queueMessage != null) {
      String message = (String) messageConverter.fromMessage(queueMessage);
      task = new JSONObject(message);
    }

    return task;
  }
}
