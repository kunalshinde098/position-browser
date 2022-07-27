package com.trafigura.interview.repository;

import com.trafigura.interview.common.TradeAction;
import com.trafigura.interview.common.TranType;
import com.trafigura.interview.model.Trade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TradeRepositoryTest {

    @Autowired private TradeRepository repository;


    @Test
    public void testPersistTrade() {
        Trade trade = Trade.builder().secCode("test")
                .tradeAction(TradeAction.BUY)
                .quantity(10)
                .tradeId(1L)
                .tranType(TranType.INSERT)
                .version(1L).build();

        assertNull(trade.getTranId());
        repository.save(trade);
        assertNotNull(trade.getTranId());



    }

    @Test
    public void testRetrievalTrade() {

        Trade trade = Trade.builder().secCode("test")
                .tradeAction(TradeAction.BUY)
                .quantity(10)
                .tradeId(1L)
                .tranType(TranType.INSERT)
                .version(1L).build();

        repository.save(trade);

        assertEquals(1, repository.findBySecCodeContaining("test").stream().filter(trade1 -> trade1.getSecCode().contains("test")).count());

        assertEquals(1, repository.findByTradeId(1l).stream().filter(trade1 -> trade1.getTradeId().equals(1L)).count());

        assertEquals(1, repository.findByTradeIdAndVersion(1l, 1L).stream().filter(trade1 -> trade1.getTradeId().equals(1L)).count());

    }

}
