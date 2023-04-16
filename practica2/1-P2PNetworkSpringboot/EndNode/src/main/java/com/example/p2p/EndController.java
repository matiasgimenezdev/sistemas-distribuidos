package com.example.p2p;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class EndController {

  @Autowired
  private EndModel end;

  @Autowired
  private NetworkService networkService;

  @GetMapping("/file")
  public String getFile() {
    // TODO Entregar el archivo que el peer esta solicitando
    networkService.deliver("nombre archivo");
    return "Here is your file";
  }

  @GetMapping("/download")
  public ResponseEntity<String> downloadFile(
    @RequestParam("file") String file
  ) {
    try {
      JSONObject fileInformation = networkService.getFileInformation(file);
      System.out.println(fileInformation.toString());

      // TODO Descargar el archivo desde el peer indicado.
      // networkService.download(fileInformation);

      return ResponseEntity.ok(fileInformation.toString());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}
