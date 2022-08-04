package com.trafigura.interview.service;

import com.trafigura.interview.model.Position;
import com.trafigura.interview.model.Trade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class UpdateTransactionService implements TransactionService {
    @Override
    public Position applyTransaction(Trade trade, Position position, List<Trade> tradeVersions) {
        AtomicInteger quantity = new AtomicInteger(0);
        Map<Long, List<Trade>> tradeIdListMap = tradeVersions.stream()
                .collect(Collectors.groupingBy(Trade::getTradeId, ConcurrentHashMap::new, Collectors.toCollection(ArrayList::new)));
        tradeIdListMap.forEach ((key, value) -> {
            quantity.getAndAdd(calculateQuantity(value).get());
        });
        position.setQuantity(quantity.get());
        return position;
    }

}
