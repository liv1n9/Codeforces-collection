package com.cfcollection.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtils {
    public static String toJsonString(Object obj) {
        return new Gson().toJson(obj);
    }
    public static JsonObject stringToJsonObject(String s) {
        return new JsonParser().parse(s).getAsJsonObject();
    }
    public static JsonObject ToJsonObject(Object obj) {
        return new JsonParser().parse(toJsonString(obj)).getAsJsonObject();
    }
}
