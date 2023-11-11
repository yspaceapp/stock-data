package br.com.app.stockdata.util;

import java.util.regex.Pattern;

public class ValidatorUtils {

    private ValidatorUtils() {
    }

    public static boolean isEmailValido(String email) {
        return Pattern.matches("^[_a-zA-Z0-9-]+([.+][_a-zA-Z0-9-]+)*+@[_a-zA-Z0-9-]+\\.[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*+$", email);
    }
}
