package com.example.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;


@RestController
@SpringBootApplication
public class Service {

	public static void main(String[] args) {
		SpringApplication.run(Service.class, args);
	}

	@PostMapping("/task")
	public ResponseEntity<String> ejecutarTarea(@RequestBody String body) {
		try{
			Gson gson = new Gson();
			JSONObject parameters = new JSONObject(body);
			Tarea genericTask = gson.fromJson(parameters.get("genericTask").toString(), TareaGenerica.class); 
			JSONObject response = genericTask.ejecutar(parameters);
			return ResponseEntity.ok(response.toString());
		} catch(JSONException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JSON Error: " + e.getMessage());
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
		}
	}



}


