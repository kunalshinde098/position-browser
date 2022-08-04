package com.trafigura.interview;

import com.trafigura.interview.common.TradeAction;
import com.trafigura.interview.common.TranType;
import com.trafigura.interview.model.Position;
import com.trafigura.interview.model.Trade;
import com.trafigura.interview.service.PositionService;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class PositionApplicationTest {

    @Test
    public void testMain() {
        // Test Data
        Trade trade1 = new Trade(1L, 1L, "REL", 50, TranType.INSERT, TradeAction.BUY);
        Trade trade2 = new Trade(2L, 1L, "ITC", 40, TranType.INSERT, TradeAction.SELL);
        Trade trade3 = new Trade(3L, 1L, "INF", 70, TranType.INSERT, TradeAction.BUY);
        Trade trade4 = new Trade(1L, 2L, "REL", 60, TranType.UPDATE, TradeAction.BUY);
        Trade trade5 = new Trade(2L, 3L, "ITC", 30, TranType.CANCEL, TradeAction.BUY);
        Trade trade6 = new Trade(4L, 1L, "INF", 20, TranType.INSERT, TradeAction.SELL);

        PositionService service = new PositionService();
        service.apply(trade1);
        service.apply(trade2);
        service.apply(trade3);
        service.apply(trade4);
        service.apply(trade5);


        Collection<Position> positions = service.apply(trade6).values();
        assertEquals(0, positions.stream()
                                        .filter(position -> position.getSecCode().equals("ITC"))
                                        .findFirst().get().getQuantity().intValue());

        assertEquals(60, positions.stream()
                .filter(position -> position.getSecCode().equals("REL"))
                .findFirst().get().getQuantity().intValue());

        assertEquals(50, positions.stream()
                .filter(position -> position.getSecCode().equals("INF"))
                .findFirst().get().getQuantity().intValue());

    }
}