package com.sd.sobel;

import com.sd.sobel.controllers.TasksController;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class App {

  public static void main(String[] args) {
    SpringApplication.run(TasksController.class, args);
  }
}
