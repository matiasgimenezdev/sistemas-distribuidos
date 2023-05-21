package com.sd.sobel.app;

import static com.sd.sobel.config.QueuesConfiguration.*;

import com.sd.sobel.model.Task;
import com.sd.sobel.model.TaskRegister;
import com.sd.sobel.services.HttpRequests;
import com.sd.sobel.services.MessageLogger;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TasksQueuesListener {

  @RabbitListener(queues = { QUEUE_TASKS_REGISTER })
  public void listenOnQueueRegister(TaskRegister message) {
    MessageLogger.logReceivedMessage(QUEUE_TASKS_REGISTER, message);

    // Envia la tarea al servicio de unificacion de imagen para que la registre en la BDD.
    JSONObject task = new JSONObject(message);
    HttpRequests httpRequests = new HttpRequests();
    String url = "http//image-assembly-service:8080/register";
    try {
      HttpResponse<String> response = httpRequests.GetHttpRequest(url);
      if (response.statusCode() == HttpStatus.OK.value()) {
        System.out.println("Se envío la tarea de unificacion: " + task);
      } else {
        System.out.println("No se pudo enviar la tarea de unificacion");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @RabbitListener(queues = { QUEUE_TASKS_TODO })
  public void listenOnQueueTodo(Task message) {
    MessageLogger.logReceivedMessage(QUEUE_TASKS_TODO, message);
  }

  @RabbitListener(queues = { QUEUE_TASKS_COMPLETED })
  public void listenOnQueueCompleted(Task message) {
    MessageLogger.logReceivedMessage(QUEUE_TASKS_COMPLETED, message);
  }
}
