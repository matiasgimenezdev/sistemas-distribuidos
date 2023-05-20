package com.sd.sobel.controllers;

import static com.sd.sobel.config.DirectExchangeConfig.*;

import com.sd.sobel.app.DirectExchangeProducer;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @SpringBootApplication
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/taskmanager")
public class TasksController {

  private final DirectExchangeProducer directExchangeProducer;

  @PostMapping("/register")
  public String taskRegister(@RequestBody JSONObject taskData) {
    directExchangeProducer.sendMessage(ROUTING_KEY_TASKS_COMPLETED, taskData);
    return "Task registered: " + taskData.toString();
  }
}
