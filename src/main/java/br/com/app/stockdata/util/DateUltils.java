package br.com.app.stockdata.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUltils {

    public static String dateCurrent() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

}
