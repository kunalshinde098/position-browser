package com.trafigura.interview.dto;

import com.trafigura.interview.common.TradeAction;
import com.trafigura.interview.model.Trade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TradeDtoTest {

    @Test
    void toDto() {
        Trade trade = Trade.builder().secCode("test").tradeAction(TradeAction.BUY).build();
        assertEquals("test", TradeDto.toDto(trade).getSecCode());
    }

    @Test
    void toTrade() {
        TradeDto dto = TradeDto.builder().secCode("test").build();
        assertEquals("test", TradeDto.toTrade(dto).getSecCode());
    }
}