package com.trafigura.interview.model;

import com.trafigura.interview.common.TradeAction;
import com.trafigura.interview.common.TranType;

import java.util.UUID;

public class Trade {

    private final String tranId;
    private final Long tradeId;
    private final Long version;
    private final String secCode;
    private final Integer quantity;
    private final TranType tranType;
    private final TradeAction tradeAction;

    public Trade(Long tradeId, Long version, String secCode, Integer quantity, TranType tranType, TradeAction tradeAction) {
        this.tranId = UUID.randomUUID().toString();
        this.tradeId = tradeId;
        this.version = version;
        this.secCode = secCode;
        this.quantity = quantity;
        this.tranType = tranType;
        this.tradeAction = tradeAction;
    }

    public String getTranId() {
        return tranId;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public Long getVersion() {
        return version;
    }

    public String getSecCode() {
        return secCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public TranType getTranType() {
        return tranType;
    }

    public TradeAction getTradeAction() {
        return tradeAction;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId=" + tradeId +
                ", version=" + version +
                ", secCode='" + secCode + '\'' +
                ", quantity=" + quantity +
                ", tranType=" + tranType +
                ", tradeAction=" + tradeAction +
                '}';
    }
}
