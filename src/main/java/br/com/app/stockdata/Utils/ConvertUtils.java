package br.com.app.stockdata.Utils;

import br.com.app.stockdata.model.dto.AssetsDTO;
import com.google.gson.Gson;


public class ConvertUtils {

    public static <T> T convertJsonToObject(String jsonString, Class<T> targetType) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, targetType);
    }
}
