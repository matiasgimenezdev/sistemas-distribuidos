package com.sd.sobel.controllers;

import static com.sd.sobel.config.DirectExchangeConfig.*;
import static com.sd.sobel.config.QueuesConfiguration.*;

import com.sd.sobel.app.DirectExchangeConsumer;
import com.sd.sobel.app.DirectExchangeProducer;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/taskmanager")
public class TasksController {

  private final DirectExchangeProducer directExchangeProducer;

  private final DirectExchangeConsumer directExchangeConsumer;

  @PostMapping("/register")
  public ResponseEntity<String> taskRegister(@RequestBody String body) {
    System.out.println("REGISTER TASK data: " + body);
    JSONObject taskData = new JSONObject(body);
    try {
      directExchangeProducer.sendMessage(ROUTING_KEY_TASKS_REGISTER, taskData);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/todo")
  public ResponseEntity<String> taskTodo(@RequestBody String body) {
    System.out.println("TODO TASK data: " + body);
    JSONObject taskData = new JSONObject(body);
    try {
      directExchangeProducer.sendMessage(ROUTING_KEY_TASKS_TODO, taskData);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/completed")
  public ResponseEntity<String> taskCompleted(@RequestBody String body) {
    System.out.println("COMPLETED TASK data: " + body);
    JSONObject taskData = new JSONObject(body);
    try {
      directExchangeProducer.sendMessage(ROUTING_KEY_TASKS_COMPLETED, taskData);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get/completed")
  public ResponseEntity<String> getTaskCompleted() {
    try {
      JSONObject completedTaskData = directExchangeConsumer.consumeMessage(
        QUEUE_TASKS_COMPLETED
      );
      return new ResponseEntity<>(completedTaskData.toString(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get/todo")
  public ResponseEntity<String> getTaskTodo() {
    try {
      JSONObject todoTaskData = directExchangeConsumer.consumeMessage(
        QUEUE_TASKS_TODO
      );
      return new ResponseEntity<>(todoTaskData.toString(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
