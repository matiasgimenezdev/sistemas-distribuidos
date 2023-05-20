package com.sd.sobel.app;

import static com.sd.sobel.config.DirectExchangeConfig.*;

import com.sd.sobel.model.Task;
import com.sd.sobel.model.TaskRegister;
import com.sd.sobel.services.MessageLogger;
import java.io.Console;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DirectExchangeProducer {

  private final RabbitTemplate rabbitTemplate;

  public void sendMessage(String routingKey, JSONObject taskData) {
    sendMsg(routingKey, taskData);
  }

  private void sendMsg(String routingKey, JSONObject taskData) {
    Task message = null;
    System.out.println("Routing key: " + routingKey);
    System.out.println(ROUTING_KEY_TASKS_REGISTER);
    if (routingKey.equals(ROUTING_KEY_TASKS_REGISTER)) {
      String taskId = taskData.getString("taskId");
      String parts = taskData.getString("parts");
      String width = taskData.getString("width");
      String height = taskData.getString("height");
      message = new TaskRegister(taskId, parts, width, height);
    } else {
      return;
    }

    rabbitTemplate.convertAndSend(EXCHANGE_TASKS_DIRECT, routingKey, message);
    MessageLogger.logSendMessage(EXCHANGE_TASKS_DIRECT, routingKey, message);
  }
}
