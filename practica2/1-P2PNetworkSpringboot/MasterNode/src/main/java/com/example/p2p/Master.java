package com.example.p2p;

import java.util.ArrayList;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class Master {

  private JedisPool jedisPool;

  @Autowired
  public Master(Environment env) {
    Integer redisPort = Integer.parseInt(env.getProperty("redis.port", "6379"));
    String redisHost = env.getProperty("redis.host");
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxTotal(100);
    poolConfig.setMaxIdle(50);
    poolConfig.setMinIdle(10);

    JedisPool jedisPool = new JedisPool(poolConfig, redisHost, redisPort);
    this.jedisPool = jedisPool;
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
    JSONObject response = new JSONObject();
    try (Jedis jedis = jedisPool.getResource()) {
      Set<String> keys = jedis.keys("*");
      for (String key : keys) {
        String peerData = jedis.get(key);
        JSONObject json = new JSONObject(peerData);
        JSONArray filesArray = json.getJSONArray("files");
        for (int i = 0; i < filesArray.length(); i++) {
          if (fileName.equals(filesArray.get(i))) {
            response.put("ipAddres", key);
            response.put("port", json.getString("port"));
            response.put("port", fileName);
            found = true;
          }
        }
      }
    } catch (Exception e) {
      response.put("error", e.getMessage());
    }

    if (!found) {
      response.put("error", fileName + " Not Found");
    }

    return response;
  }

  public JSONObject getFiles() {
    ArrayList<String> availableFiles = new ArrayList<String>();
    JSONObject response = new JSONObject();
    try (Jedis jedis = jedisPool.getResource()) {
      Set<String> keys = jedis.keys("*");
      for (String key : keys) {
        String peerData = jedis.get(key);
        System.out.println(peerData);
        JSONObject json = new JSONObject(peerData);
        JSONArray filesArray = json.getJSONArray("files");
        for (int i = 0; i < filesArray.length(); i++) {
          String fileName = filesArray.get(i).toString();
          availableFiles.add(fileName);
        }
      }
      response.put("Files", availableFiles);
    } catch (Exception e) {
      response.put("error", e.getMessage());
    }
    return response;
  }

  private boolean saveData(PeerData newPeer) {
    try (Jedis jedis = jedisPool.getResource()) {
      boolean exists = jedis.exists(newPeer.getIpAddress());
      JSONObject peerData = new JSONObject();
      peerData.put("port", newPeer.getPort());
      peerData.put("files", newPeer.getFiles());
      jedis.set(newPeer.getIpAddress(), peerData.toString());
      peerData.put("ip", newPeer.getIpAddress());
      System.out.println(peerData.toString());
      return exists;
    } catch (Exception e) {
      return false;
    }
  }
}
