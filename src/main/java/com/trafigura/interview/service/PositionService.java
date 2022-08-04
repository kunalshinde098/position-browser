package com.trafigura.interview.service;

import com.trafigura.interview.factory.TransactionFactory;
import com.trafigura.interview.model.Position;
import com.trafigura.interview.model.Trade;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PositionService {

    private final ConcurrentHashMap<String, Position> positions;
    private final ConcurrentHashMap<String, List<Trade>> secCodeTrades;
    private final TransactionFactory factory;

    public PositionService() {
        this.secCodeTrades = new ConcurrentHashMap<>();
        this.positions  = new ConcurrentHashMap<>();
        this.factory = new TransactionFactory();
    }

    public ConcurrentHashMap<String, Position> apply(Trade trade){

        Optional<TransactionService> transactionService = this.factory.getService(trade.getTranType());

        if (transactionService.isPresent()){
            Position position = transactionService.get().applyTransaction(trade, positions.get(trade.getSecCode()),
                    populateTrade(trade).stream()
                            .sorted(Comparator.comparing((Trade::getTradeId))
                                    .thenComparing(Trade::getVersion)
                                    .thenComparing(Trade::getTranType))
                            .collect(Collectors.toList()));
            this.positions.put(trade.getSecCode(), position);

        }
        return this.positions;
    }



    private List<Trade> populateTrade(Trade trade){
        List<Trade> tradeVersions = null;
        if(secCodeTrades.get(trade.getSecCode()) == null){
            tradeVersions = new LinkedList<>();
            tradeVersions.add(trade);
            secCodeTrades.put(trade.getSecCode(), tradeVersions);
        }else {
            secCodeTrades.get(trade.getSecCode()).add(trade);
        }
        return secCodeTrades.get(trade.getSecCode());

    }


}
