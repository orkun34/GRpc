package com.wallet.rpc.impl.operation.enums;

public enum CurrencyType {

    EURO("EUR"),POUND("GBP"),DOLLAR("USD");

    private String name;

    CurrencyType(String name) {
        this.name = name;
    }

    public String getCurrency(){
        return this.name;
    }
}
