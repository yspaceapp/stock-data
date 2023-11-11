package br.com.app.stockdata.model;

public enum Type {
    STOCK("Stock"),
    FII("FII"),
    CURRENCY("Currency"),
    CRYPTO("Crypto");

    private String name;

    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
