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

    return this.peerExists(peer);
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
      List<String> files = redis.lrange(key, 0, -1);
      for (String file : files) {
        if (!availableFiles.contains(file)) {
          availableFiles.add(file);
        }
      }
    }
    JSONObject json = new JSONObject();
    json.put("Files", availableFiles);
    return json;
  }

  private boolean peerExists(PeerData newPeer) {
    boolean exists = false;
    String peerPort = this.redis.get(newPeer.getIpAddress());
    this.redis.rpush(newPeer.getIpAddress(), newPeer.getFiles());
    if (peerPort != null) {
      exists = true;
      this.redis.set(newPeer.getIpAddress(), newPeer.getPort());
      System.out.println("Peer information updated.");
    } else {
      exists = false;
      System.out.println(newPeer.toString());
    }
    return exists;
  }
}
