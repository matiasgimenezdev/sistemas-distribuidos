package com.sd.sobel;

import com.sd.sobel.app.DirectExchangeConsumer;
import com.sd.sobel.app.DirectExchangeProducer;
import com.sd.sobel.controllers.TasksController;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  public TasksController tasksController(
    DirectExchangeProducer directExchangeProducer,
    DirectExchangeConsumer directExchangeConsumer
  ) {
    return new TasksController(directExchangeProducer, directExchangeConsumer);
  }
}
