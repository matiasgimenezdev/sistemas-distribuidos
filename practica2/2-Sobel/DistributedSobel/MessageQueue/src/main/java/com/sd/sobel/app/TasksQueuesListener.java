package com.sd.sobel.app;

import static com.sd.sobel.config.QueuesConfiguration.*;

import com.sd.sobel.model.Task;
import com.sd.sobel.model.TaskRegister;
import com.sd.sobel.services.MessageLogger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TasksQueuesListener {

  @RabbitListener(queues = { QUEUE_TASKS_REGISTER })
  public void listenOnQueueRegister(TaskRegister message) {
    MessageLogger.logReceivedMessage(QUEUE_TASKS_REGISTER, message);
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
