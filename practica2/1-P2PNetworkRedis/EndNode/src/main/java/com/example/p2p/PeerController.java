package com.example.p2p;

import java.io.FileNotFoundException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class PeerController {

  private Peer peer;
  private NetworkUtils networkService;

  @Autowired
  public PeerController(NetworkUtils networkService, Peer peer) {
    this.peer = peer;
    this.networkService = networkService;
    this.networkService.initFile();
    this.networkService.initFile();
    this.networkService.initFile();
  }

  @GetMapping("/register")
  public ResponseEntity<String> register() {
    try {
      String[] availableFiles = this.peer.getAvailableFiles();
      JSONObject registerInformation =
        this.networkService.register(availableFiles);
      if (registerInformation.has("error")) {
        throw new Exception(registerInformation.getString("error"));
      }
      return ResponseEntity.ok(registerInformation.getString("response"));
    } catch (Exception exception) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exception.getMessage());
    }
  }

  @GetMapping("/list")
  public ResponseEntity<String> list() {
    try {
      return ResponseEntity.ok(this.networkService.listFiles().toString());
    } catch (Exception exception) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exception.getMessage());
    }
  }

  @GetMapping("/download")
  public ResponseEntity<String> download(@RequestParam String fileName) {
    try {
      JSONObject fileInformation = networkService.getFileInformation(fileName);
      if (fileInformation.has("error")) {
        throw new FileNotFoundException(fileInformation.getString("error"));
      }
      String[] oldAvailableFiles = this.peer.getAvailableFiles();

      this.networkService.downloadFile(fileInformation);
      String[] newAvailableFiles = this.peer.getAvailableFiles();
      this.networkService.register(newAvailableFiles);
      JSONObject response = new JSONObject();
      response.put("Pre-Download-Files", oldAvailableFiles);
      response.put("Post-Download-Files", newAvailableFiles);
      return ResponseEntity.ok(response.toString());
    } catch (Exception exception) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exception.getMessage());
    }
  }

  @GetMapping("/file")
  public ResponseEntity<Resource> get(@RequestParam String fileName) {
    try {
      Resource file = new FileSystemResource("/usr/src/files/" + fileName);
      if (!file.exists()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }

      return ResponseEntity
        .ok()
        .header(
          HttpHeaders.CONTENT_DISPOSITION,
          "attachment; filename=\"" + file.getFilename() + "\""
        )
        .body(file);
    } catch (Exception exception) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
