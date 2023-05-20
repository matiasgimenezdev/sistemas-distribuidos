package com.sd.sobel.controllers;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/taskmanager")
@SpringBootApplication
public class TasksController {

  @PostMapping("/register")
  public void taskRegister() {}
}
