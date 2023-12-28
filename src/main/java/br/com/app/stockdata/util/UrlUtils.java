package br.com.app.stockdata.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UrlUtils {

    public static String tratarUrl(String url) {
        return url.replaceAll("\\[\\s]+|\\s+\\]", "").replaceAll(" ", "");

    }
}
