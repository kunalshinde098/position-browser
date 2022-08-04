package com.trafigura.interview.common;

/**
 * @author Kunal
 */
public enum TranType {
    INSERT(0), UPDATE(1), CANCEL(2);

    public int value;
    private TranType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
