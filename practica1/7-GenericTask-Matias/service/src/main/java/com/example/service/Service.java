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

@RestController
@SpringBootApplication
public class Service {
	public static void main(String[] args) {
		SpringApplication.run(Service.class, args);
	}

	@PostMapping("/task")
	public ResponseEntity<String> getRequest(@RequestBody String body) {
		try{
			JSONObject params = new JSONObject(body);
			JSONObject response = ejecutarTarea(params);
			return ResponseEntity.ok(response.toString());
		} catch(JSONException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JSON Error: " + e.getMessage());
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
		}
	}

	private JSONObject ejecutarTarea(JSONObject parameters) {
		Integer min = parameters.getInt("min");
		Integer max = parameters.getInt("max");
		Double randomNumber = Math.random() * min + max;
		JSONObject response = new JSONObject();
		response.put("result", randomNumber.toString());
		return response;
	}

}
