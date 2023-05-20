package com.sd.sobel.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueuesConfiguration {

  // Mensajes para que el servicio de unificacion inicie tarea en BDD
  public static final String QUEUE_TASKS_REGISTER = "q.tasks.register";
  // Mensajes para que los servicios de filtro sobel procesen parte de imagen
  public static final String QUEUE_TASKS_TODO = "q.tasks.todo";
  // Mensajes para que el servicio de unificacion actualice el progreso de tarea en BDD
  public static final String QUEUE_TASKS_COMPLETED = "q.tasks.completed";

  @Bean
  public Queue queueTasksRegister() {
    return new Queue(QUEUE_TASKS_REGISTER);
  }

  @Bean
  public Queue queueTasksTodo() {
    return new Queue(QUEUE_TASKS_TODO);
  }

  @Bean
  public Queue queueTasksCompleted() {
    return new Queue(QUEUE_TASKS_COMPLETED);
  }
}
