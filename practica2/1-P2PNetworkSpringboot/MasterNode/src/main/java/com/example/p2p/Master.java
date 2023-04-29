package com.example.p2p;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class Master {

  HashMap<Integer, PeerData> peers = new HashMap<>();

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

    if (!peerExists(peer)) {
      peers.put((peers.size()), peer);
      JSONObject json = new JSONObject(peers);
      System.out.println(json.toString());
      return true;
    } else {
      return false;
    }
  }

  public JSONObject getFileInformation(String fileName) {
    boolean found = false;
    JSONObject json = new JSONObject();

    for (Map.Entry<Integer, PeerData> peer : peers.entrySet()) {
      if (!peer.getValue().getFile(fileName).equals("NF")) {
        json.put("ipAddress", peer.getValue().getIpAddress());
        json.put("port", peer.getValue().getPort());
        json.put("fileName", fileName);
        found = true;
      }
    }
    if (!found) {
      json.put("error", fileName + " Not Found");
    }

    return json;
  }

  private boolean peerExists(PeerData newPeer) {
    boolean exists = false;

    for (Map.Entry<Integer, PeerData> peer : peers.entrySet()) {
      if (
        peer.getValue().getIpAddress().equals(newPeer.getIpAddress()) &&
        peer.getValue().getPort().equals(newPeer.getPort())
      ) {
        exists = true;
        peer.getValue().setFiles(newPeer.getFiles());
        System.out.println(new JSONObject(peer.getValue()).toString());
      }
    }
    return exists;
  }
}
