package com.trafigura.interview.service;

import com.trafigura.interview.common.TradeAction;
import com.trafigura.interview.common.TranType;
import com.trafigura.interview.factory.TransactionFactory;
import com.trafigura.interview.model.Position;
import com.trafigura.interview.model.Trade;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class UpdateTransactionServiceTest extends TestCase {

    @Test
    public void testApplyTransaction() {

        TransactionFactory factory = new TransactionFactory();
        TransactionService transactionService = factory.getService(TranType.UPDATE).get();
        List<Trade> tradeList =  new LinkedList<>();
        tradeList.add(new Trade(1L, 1L, "REL", 50, TranType.INSERT, TradeAction.BUY));
        tradeList.add(new Trade(1L, 2L, "REL", 60, TranType.UPDATE, TradeAction.BUY));

        Trade testTrade = new Trade(1L, 2L, "REL", 60, TranType.UPDATE, TradeAction.BUY);

        Position pos = transactionService.applyTransaction(testTrade, new Position("REL", 0), tradeList);

        assertEquals(60, pos.getQuantity().intValue());

    }
}