package com.sd.sobel.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfig {

  public static final String EXCHANGE_TASKS_DIRECT = "x.tasks.direct";
  public static final String ROUTING_KEY_TASKS_TODO = "tasks.todo";
  public static final String ROUTING_KEY_TASKS_REGISTER = "tasks.register";
  public static final String ROUTING_KEY_TASKS_COMPLETED = "tasks.completed";

  @Bean
  public DirectExchange exchangeTasksDirect() {
    return new DirectExchange(EXCHANGE_TASKS_DIRECT);
  }

  @Bean
  public Declarables directExchangeBindings(
    DirectExchange exchangeTasksDirect,
    Queue queueTasksCompleted,
    Queue queueTasksRegister,
    Queue queueTasksTodo
  ) {
    return new Declarables(
      BindingBuilder
        .bind(queueTasksCompleted)
        .to(exchangeTasksDirect)
        .with(ROUTING_KEY_TASKS_COMPLETED),
      BindingBuilder
        .bind(queueTasksTodo)
        .to(exchangeTasksDirect)
        .with(ROUTING_KEY_TASKS_TODO),
      BindingBuilder
        .bind(queueTasksRegister)
        .to(exchangeTasksDirect)
        .with(ROUTING_KEY_TASKS_REGISTER)
    );
  }
}
