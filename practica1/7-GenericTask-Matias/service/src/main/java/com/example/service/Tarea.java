package com.example.service;

import org.json.JSONObject;

public class Tarea {

    @Override
    public JSONObject ejecutar(JSONObject params) {
        String task = params.getString("task");
        JSONObject result = new JSONObject();
        
        if(task.equals("randomNumber")){
            Double min = params.getDouble("min");
            Double max = params.getDouble("max");
            Double randomNumber = min + (Math.random() * max); 
            result.put("taskResult", randomNumber);
        }
        
        return result;
    }
    
}
