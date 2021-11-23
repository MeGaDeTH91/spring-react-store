package com.example.restapi.util;

import net.minidev.json.JSONObject;

import java.util.stream.Stream;

public class JSONResponse {
    public static JSONObject jsonFromString(String message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", message);

        return jsonObject;
    }

    public static JSONObject jsonFromStream(Stream<?> message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", message);

        return jsonObject;
    }
}
