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
  private NetworkUtils networkUtils;

  @Autowired
  public PeerController(NetworkUtils networkUtils, Peer peer) {
    this.peer = peer;
    this.networkUtils = networkUtils;
    this.networkUtils.initFile();
    this.networkUtils.initFile();
    this.networkUtils.initFile();
  }

  @GetMapping("/register")
  public ResponseEntity<String> register() {
    try {
      String[] availableFiles = this.peer.getAvailableFiles();
      JSONObject registerInformation =
        this.networkUtils.register(availableFiles);
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
      return ResponseEntity.ok(this.networkUtils.listFiles().toString());
    } catch (Exception exception) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exception.getMessage());
    }
  }

  @GetMapping("/download")
  public ResponseEntity<String> download(@RequestParam String fileName) {
    try {
      JSONObject fileInformation = networkUtils.getFileInformation(fileName);
      if (fileInformation.has("error")) {
        throw new FileNotFoundException(fileInformation.getString("error"));
      }
      String[] oldAvailableFiles = this.peer.getAvailableFiles();

      this.networkUtils.downloadFile(fileInformation);
      String[] newAvailableFiles = this.peer.getAvailableFiles();
      this.networkUtils.register(newAvailableFiles);
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
