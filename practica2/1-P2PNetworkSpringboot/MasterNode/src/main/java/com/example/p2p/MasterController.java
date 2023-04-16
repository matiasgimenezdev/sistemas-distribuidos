package com.example.p2p;

import org.json.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MasterController {

  // private final String IP = "127.0.0.1";
  private MasterModel master;

  public MasterController() {
    this.master = new MasterModel();
  }

  @GetMapping("/getFile")
  public String getFile(@RequestParam("file") String fileName) {
    JSONObject response = new JSONObject();
    response.put("response", master.getFileInformation(fileName).toString());
    return response.toString();
  }

  @PostMapping("/register")
  public String register(@RequestBody String body) {
    JSONObject requestBody = new JSONObject(body);
    JSONObject response = new JSONObject();
    if (master.register(requestBody)) {
      response.put("response", "OK: Peer registered");
    } else {
      response.put("response", "OK: Peer is already registered. Files updated");
    }
    return response.toString();
  }
}
