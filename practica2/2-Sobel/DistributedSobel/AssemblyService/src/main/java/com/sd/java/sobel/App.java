package com.sd.java.sobel;

import com.sd.java.sobel.app.AssemblyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

  public static void main(String[] args) {
    SpringApplication.run(AssemblyService.class, args);
  }
}
