package com.trafigura.interview.service;

import com.trafigura.interview.common.TradeAction;
import com.trafigura.interview.common.TranType;
import com.trafigura.interview.factory.TransactionFactory;
import com.trafigura.interview.model.Position;
import com.trafigura.interview.model.Trade;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InsertTransactionServiceTest {

    @Test
    public void testApplyTransaction() {
        TransactionFactory factory = new TransactionFactory();
        TransactionService transactionService = factory.getService(TranType.INSERT).get();
        List<Trade> tradeList =  new LinkedList<>();
        tradeList.add(new Trade(1L, 1L, "REL", 50, TranType.INSERT, TradeAction.BUY));
        tradeList.add(new Trade(2L, 1L, "ITC", 40, TranType.INSERT, TradeAction.SELL));

        Trade testTrade = new Trade(2L, 1L, "ITC", 40, TranType.INSERT, TradeAction.SELL);

        Position pos = transactionService.applyTransaction(testTrade, null, tradeList);

        assertEquals(-40, pos.getQuantity().intValue());

    }
}