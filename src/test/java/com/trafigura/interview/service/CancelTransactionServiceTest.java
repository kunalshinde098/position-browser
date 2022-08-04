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

public class CancelTransactionServiceTest {

    @Test
    public void testApplyTransaction() {

        TransactionFactory factory = new TransactionFactory();
        TransactionService transactionService = factory.getService(TranType.CANCEL).get();
        List<Trade> tradeList =  new LinkedList<>();
        tradeList.add(new Trade(1L, 1L, "ITC", 50, TranType.INSERT, TradeAction.BUY));
        tradeList.add(new Trade(1L, 2L, "ITC", 40, TranType.CANCEL, TradeAction.SELL));

        Trade testTrade = new Trade(2L, 1L, "ITC", 40, TranType.CANCEL, TradeAction.SELL);

        Position pos = transactionService.applyTransaction(testTrade, new Position("REL", 40), tradeList);
        assertEquals(0, pos.getQuantity().intValue());
        assertEquals(0, transactionService.calculateQuantity(tradeList).get());

        tradeList.add(new Trade(3L, 1L, "ITC", 10, TranType.INSERT, TradeAction.BUY));
        Position pos1 = transactionService.applyTransaction(testTrade, new Position("REL", 40), tradeList);

        assertEquals(10, pos1.getQuantity().intValue());




    }

}