package com.garygriffaw.tickettracker.helpers;

public class StringBuilderPlus {
    private final StringBuilder sb;

    public StringBuilderPlus() {
        this.sb = new StringBuilder();
    }

    public <T> StringBuilderPlus append(T t) {
        sb.append(t);
        return this;
    }

    public <T> StringBuilderPlus appendLine(T t) {
        sb.append(t).append(System.lineSeparator());
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }

    public StringBuilder getStringBuilder() {
        return sb;
    }
}
