package com.example.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import netscape.javascript.JSObject;

import org.json.JSONObject;

@RestController
@SpringBootApplication
public class Service {
	public static void main(String[] args) {
		SpringApplication.run(Service.class, args);
	}

	@PostMapping("/task")
	public String getRequest(@RequestBody String body) {
		JSONObject params = new JSONObject(body);
		JSONObject response = ejecutarTarea(params);
		return(response.toString());
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
