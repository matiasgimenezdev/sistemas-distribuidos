package com.sd.java.sobel.controllers;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskRegisterController {

  @PostMapping("/taskregister")
  public ResponseEntity<String> taskRegister(@RequestBody String body) {
    System.out.println("Saving REGISTER TASK in BDD : " + body);
    JSONObject taskData = new JSONObject(body);
    try {
      // Guardar tarea (taskData) en BDD.
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
