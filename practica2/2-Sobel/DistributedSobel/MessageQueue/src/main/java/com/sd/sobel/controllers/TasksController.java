package com.sd.sobel.controllers;

import static com.sd.sobel.config.DirectExchangeConfig.*;

import com.sd.sobel.app.DirectExchangeProducer;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/taskmanager")
public class TasksController {

  private final DirectExchangeProducer directExchangeProducer;

  @PostMapping("/register")
  public String taskRegister(@RequestBody String body) {
    System.out.println("Task data: " + body);
    JSONObject taskData = new JSONObject(body);
    directExchangeProducer.sendMessage(ROUTING_KEY_TASKS_REGISTER, taskData);

    return "Task registered: " + taskData.toString();
  }
}
