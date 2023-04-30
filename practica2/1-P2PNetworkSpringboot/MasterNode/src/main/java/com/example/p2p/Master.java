package com.example.p2p;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class Master {

  private Jedis redis;

  @Autowired
  public Master(Environment env) {
    Integer redisPort = Integer.parseInt(env.getProperty("redis.port", "6379"));
    String redisHost = env.getProperty("redis.host");
    this.redis = new Jedis(redisHost, redisPort);
  }

  public boolean register(JSONObject peerData) {
    JSONArray filesArray = peerData.getJSONArray("files");
    String[] files = new String[filesArray.length()];
    for (int i = 0; i < files.length; i++) {
      String fileName = filesArray.get(i).toString();
      files[i] = fileName;
    }

    PeerData peer = new PeerData(
      peerData.getString("ipAddress"),
      peerData.getString("port"),
      files
    );

    return this.saveData(peer);
  }

  public JSONObject getFileInformation(String fileName) {
    boolean found = false;
    JSONObject json = new JSONObject();

    Set<String> keys = redis.keys("*");
    for (String key : keys) {
      List<String> files = redis.lrange(key, 0, -1);
      for (String file : files) {
        if (file.equals(fileName)) {
          json.put("ipAddres", key);
          json.put("port", redis.get(key));
          json.put("port", fileName);
          found = true;
        }
      }
    }

    if (!found) {
      json.put("error", fileName + " Not Found");
    }

    return json;
  }

  public JSONObject getFiles() {
    ArrayList<String> availableFiles = new ArrayList<String>();
    Set<String> keys = redis.keys("*");
    for (String key : keys) {
      String peerData = redis.get(key);
      JSONObject json = new JSONObject(peerData);
      JSONArray filesArray = json.getJSONArray("files");
      for (int i = 0; i < filesArray.length(); i++) {
        String fileName = filesArray.get(i).toString();
        availableFiles.add(fileName);
      }
    }

    JSONObject json = new JSONObject();
    json.put("Files", availableFiles);
    System.out.println(json.toString());
    return json;
  }

  private boolean saveData(PeerData newPeer) {
    boolean exists = redis.exists(newPeer.getIpAddress());
    JSONObject peerData = new JSONObject();
    peerData.put("port", newPeer.getPort());
    peerData.put("files", newPeer.getFiles());
    this.redis.set(newPeer.getIpAddress(), peerData.toString());

    peerData.put("ip", newPeer.getIpAddress());
    System.out.println(peerData.toString());
    return exists;
  }
}
