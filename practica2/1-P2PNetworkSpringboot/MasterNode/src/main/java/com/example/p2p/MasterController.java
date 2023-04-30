package com.example.p2p;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MasterController {

  private Master master;

  @Autowired
  public MasterController(Master master) {
    this.master = master;
  }

  @GetMapping("/fileinfo")
  public String getFile(@RequestParam("file") String fileName) {
    return master.getFileInformation(fileName).toString();
  }

  @GetMapping("/list")
  public String getFiles() {
    System.out.println("GET /list");
    String files = master.getFiles().toString();
    System.out.println(files);
    return files;
  }

  @PostMapping("/register")
  public String register(@RequestBody String body) {
    JSONObject requestBody = null;
    JSONObject response = new JSONObject();
    try {
      requestBody = new JSONObject(body);
    } catch (JSONException exception) {
      response.put("error", exception.getMessage());
    } finally {
      if (!master.register(requestBody)) {
        response.put("response", "OK: Peer registered");
      } else {
        response.put(
          "response",
          "OK: Peer is already registered. Files updated"
        );
      }
    }
    return response.toString();
  }
}
