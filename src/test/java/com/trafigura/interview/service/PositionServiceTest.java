package com.trafigura.interview.service;

import com.trafigura.interview.common.TradeAction;
import com.trafigura.interview.common.TranType;
import com.trafigura.interview.model.Position;
import com.trafigura.interview.model.Trade;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class PositionServiceTest {

    @Test
    public void testApply() {
        Trade trade1 = new Trade(1L, 1L, "REL", 50, TranType.INSERT, TradeAction.BUY);
        Trade trade2 = new Trade(2L, 1L, "ITC", 40, TranType.INSERT, TradeAction.SELL);

        PositionService service = new PositionService();
        service.apply(trade1);

        Collection<Position> positions = service.apply(trade2).values();
        assertEquals(-40, positions.stream()
                .filter(position -> position.getSecCode().equals("ITC"))
                .findFirst().get().getQuantity().intValue());


    }

}