package com.example.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


import netscape.javascript.JSObject;

import org.json.JSONObject;

@RestController
@SpringBootApplication
public class Service {
	public static void main(String[] args) {
		SpringApplication.run(Service.class, args);
	}

	@GetMapping("/task")
	public String getRequest() {
		JSONObject json = new JSONObject();
		Integer result = ejecutarTarea(json);
		json.put("Tarea ejecutada: ", result.toString());
		return(json.toString());
	}

	private Integer ejecutarTarea(JSONObject parameters) {
		return 10;
	}

}
