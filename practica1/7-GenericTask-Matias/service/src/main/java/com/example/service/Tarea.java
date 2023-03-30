package com.example.service;

import org.json.JSONObject;

public class Tarea {
    
    public JSONObject ejecutar(JSONObject params) {
        JSONObject result = new JSONObject();
        Double min = params.getDouble("min");
        Double max = params.getDouble("max");
        Double randomNumber = Math.ceil(min + (Math.random() * max)); 
        result.put("result", randomNumber);
        return result;
    }
    
}
