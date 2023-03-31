package com.example.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;

@SpringBootApplication
@RestController
public class Service {
	@PostMapping("/task")
	public ResponseEntity<String> ejecutarTarea(@RequestBody String body) {
		try{
			JSONObject parameters = new JSONObject(body);
			Tarea genericTask = new Tarea();
			JSONObject response = genericTask.ejecutar(parameters);
			return ResponseEntity.ok(response.toString());
		} catch(JSONException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}


