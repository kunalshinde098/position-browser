package com.trafigura.interview.service;

import com.trafigura.interview.common.TradeAction;
import com.trafigura.interview.model.Position;
import com.trafigura.interview.model.Trade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InsertTransactionService implements TransactionService {

    @Override
    public Position applyTransaction(Trade trade, Position position, List<Trade> tradeVersions) {
        if(position == null){
            int quantity = trade.getTradeAction().equals(TradeAction.BUY) ? trade.getQuantity() : (0 - trade.getQuantity());
            return new Position(trade.getSecCode(), quantity);
        }else {
            if(trade.getTradeAction().equals(TradeAction.BUY)){
               position.setQuantity(position.getQuantity() + trade.getQuantity());
            } else if (trade.getTradeAction().equals(TradeAction.SELL)) {
                position.setQuantity(position.getQuantity() - trade.getQuantity());
            }
        }
        return position;
    }

}
