package com.sd.sobel.app;

import static com.sd.sobel.config.DirectExchangeConfig.*;

import com.sd.sobel.model.Task;
import com.sd.sobel.model.TaskRegister;
import com.sd.sobel.model.TaskTodo;
import com.sd.sobel.services.MessageLogger;
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
    if (routingKey.equals(ROUTING_KEY_TASKS_REGISTER)) {
      String taskId = taskData.getString("taskId");
      String parts = taskData.getString("parts");
      String width = taskData.getString("width");
      String height = taskData.getString("height");
      String source = taskData.getString("source");
      String destination = taskData.getString("destination");
      message =
        new TaskRegister(taskId, parts, width, height, source, destination);
    } else if (routingKey.equals(ROUTING_KEY_TASKS_TODO)) {
      String taskId = taskData.getString("taskId");
      String source = taskData.getString("source");
      String destination = taskData.getString("destination");
      message = new TaskTodo(taskId, source, destination);
    } else if (routingKey.equals(ROUTING_KEY_TASKS_COMPLETED)) {
      // TODO: Implementar registro de tareas completadas por los workers
    } else {
      return;
    }

    rabbitTemplate.convertAndSend(EXCHANGE_TASKS_DIRECT, routingKey, message);
    MessageLogger.logSendMessage(EXCHANGE_TASKS_DIRECT, routingKey, message);
  }
}
