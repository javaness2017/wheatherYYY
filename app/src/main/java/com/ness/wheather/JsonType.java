package com.ness.wheather;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class JsonType {
    public static JsonElement isJson(String Json) {

        Gson gson = new Gson();

        JsonElement element = null;
        JsonObject jsonObj = null;
        JsonArray jsonArr = null;

        try {
            element = gson.fromJson(Json, JsonElement.class);
        } catch (Exception ex) {
            return null;
        }
        try {
            jsonObj = element.getAsJsonObject();
        } catch (Exception ex) {
        }
        try {
            jsonArr = element.getAsJsonArray();
        } catch (Exception ex) {
        }
        if (jsonArr == null && jsonObj == null) return element;
        if (jsonObj != null) return jsonObj;
        return jsonArr;
    }
}
