package com.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;

@RestController
@SpringBootApplication
public class Server {

	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);
	}

	@GetMapping("/remotetask")
	public String getRequest() {
		JSONObject json = new JSONObject();    
		json.put("name","Esteban");    
		json.put("surname","Quito");      
		JSONObject result = ejecutarTareaRemota(json);
		return(result.toString());
	}

	private JSONObject ejecutarTareaRemota(JSONObject parameters) {
        //Usa los parametros para ejecutar el servicio con la tarea generica
		JSONObject result = new JSONObject();
		result.put("resultado", "Resultado tarea genérica");
		return result;
    }
}
