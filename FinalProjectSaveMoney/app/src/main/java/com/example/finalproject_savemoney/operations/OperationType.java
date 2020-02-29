package com.example.finalproject_savemoney.operations;

public enum OperationType {
    INCOME("INCOME"),
    CONSUMPTION("CONSUMPTION");

    private final String text;

    OperationType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
