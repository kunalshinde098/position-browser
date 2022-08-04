package com.trafigura.interview.service;

import com.trafigura.interview.common.TradeAction;
import com.trafigura.interview.common.TranType;
import com.trafigura.interview.model.Position;
import com.trafigura.interview.model.Trade;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@FunctionalInterface
public interface TransactionService {

    Position applyTransaction(Trade trade, Position position, List<Trade> tradeVersions);

    default AtomicInteger calculateQuantity(List<Trade> tradeVersions){
        AtomicInteger quantity  = new AtomicInteger(0);
        tradeVersions.stream().sorted(Comparator.comparing(Trade::getTradeId)
                        .thenComparing(Trade::getVersion)
                        .thenComparing(Trade::getTranType)).collect(Collectors.toList())
                .forEach(trade -> {
                    if (trade.getTranType().equals(TranType.CANCEL)) {
                        quantity.set(0);
                    } else if (trade.getTranType().equals(TranType.UPDATE)) {
                        quantity.set(trade.getQuantity());

                    } else if (trade.getTranType().equals(TranType.INSERT)) {
                        if (trade.getTradeAction().equals(TradeAction.BUY)) {
                            quantity.set(quantity.get() + trade.getQuantity());
                        } else if (trade.getTradeAction().equals(TradeAction.SELL)) {
                            quantity.set(quantity.get() - trade.getQuantity());
                        }
                    }
                });
        return quantity;
    }

}
