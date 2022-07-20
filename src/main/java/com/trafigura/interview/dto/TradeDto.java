package com.trafigura.interview.dto;

import com.trafigura.interview.common.TradeAction;
import com.trafigura.interview.common.TranType;
import com.trafigura.interview.model.Trade;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
public class TradeDto {
    private Long tradeId;
    private Long version;
    private String secCode;
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private TranType tranType;
    @Enumerated(EnumType.STRING)
    private TradeAction tradeAction;

    public static TradeDto toDto(Trade trade){
     return TradeDto.builder()
             .tradeId(trade.getTradeId())
             .quantity(trade.getQuantity())
             .tradeAction(trade.getTradeAction())
             .version(trade.getVersion())
             .secCode(trade.getSecCode())
             .tranType(trade.getTranType())
             .build();
    }

    public static Trade toTrade(TradeDto dto){
        return Trade.builder()
                .tradeId(dto.getTradeId())
                .tradeAction(dto.getTradeAction())
                .secCode(dto.getSecCode())
                .quantity(dto.getQuantity())
                .tranType(dto.getTranType())
                .version(dto.getVersion())
                .build();
    }
}
