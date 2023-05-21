package com.sd.java.sobel;

import com.sd.java.sobel.app.AssemblyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
    ApplicationContext context = SpringApplication.run(App.class, args);
    // Obtén una instancia de AssemblyService del contexto de la aplicación
    context.getBean(AssemblyService.class);
  }
}
