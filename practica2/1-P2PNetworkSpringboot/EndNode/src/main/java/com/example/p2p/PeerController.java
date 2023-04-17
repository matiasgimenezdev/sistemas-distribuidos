package com.example.p2p;

import java.io.FileNotFoundException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class PeerController {

  private Peer peer;
  private NetworkService networkService;

  @Autowired
  public PeerController(NetworkService networkService, Peer peer) {
    this.peer = peer;
    this.networkService = networkService;
  }

  @GetMapping("/file")
  public ResponseEntity<Resource> getFile() {
    try {
      Resource resource = networkService.deliver("nombre archivo");
      return ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(
          HttpHeaders.CONTENT_DISPOSITION,
          "attachment; filename=\"" + resource.getFilename() + "\""
        )
        .body(resource);
    } catch (FileNotFoundException exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/register")
  public ResponseEntity<String> register() {
    try {
      String[] availableFiles = this.peer.getAvailableFiles();
      JSONObject registerResponse =
        this.networkService.register(availableFiles);
      System.out.println(registerResponse.toString(0));
      return ResponseEntity.ok(registerResponse.toString());
    } catch (Exception exception) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exception.getMessage());
    }
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
    } catch (Exception exception) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exception.getMessage());
    }
  }
}
