package com.example.service;

import org.json.JSONObject;

public class Tarea {

    public JSONObject ejecutar(JSONObject params) {
        JSONObject result = new JSONObject();
        Double min = params.getDouble("min");
        Double max = params.getDouble("max");
        double randomNumber = Math.random() * (max - min) + min;
        result.put("result", randomNumber);
        return result;
    }

}
