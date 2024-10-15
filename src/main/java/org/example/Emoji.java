package org.example;

public enum Emoji {
    SMILE("😎"),
    WARNING("⚠️"),
    ERROR("❌"),
    INFO("ℹ️"),
    CORRECT("✅");

    private final String symbol;

    Emoji(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}