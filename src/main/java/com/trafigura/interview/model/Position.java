package com.trafigura.interview.model;


import java.util.UUID;

public class Position {
    private String posId;
    private final String secCode;
    private Integer quantity;

    public Position(String secCode, Integer quantity) {
        this.posId = UUID.randomUUID().toString();
        this.secCode = secCode;
        this.quantity = quantity;
    }


    public String getSecCode() {
        return secCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Position{" +
                "secCode='" + secCode + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
