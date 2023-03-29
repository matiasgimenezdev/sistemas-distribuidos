package com.client;

import org.json.JSONObject;

public class TareaGenerica implements Tarea {

    @Override
    public JSONObject ejecutar(JSONObject params) {
        Double min = params.getDouble("min");
        Double max = params.getDouble("max");
        JSONObject result = new JSONObject();
        Double randomNumber = min + (Math.random() * max); 
        result.put("taskResult", randomNumber);
        return result;
    }
    
}
