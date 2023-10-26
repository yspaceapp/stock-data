package br.com.app.stockdata.util;

import com.google.gson.Gson;


public class ConvertUtils {

    private ConvertUtils() {
    }

    public static <T> T convertJsonToObject(String jsonString, Class<T> targetType) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, targetType);
    }
}
