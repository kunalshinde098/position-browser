package com.trafigura.interview.service;

import com.trafigura.interview.common.TradeAction;
import com.trafigura.interview.common.TranType;
import com.trafigura.interview.dto.PositionDto;
import com.trafigura.interview.dto.TradeDto;
import com.trafigura.interview.model.Position;
import com.trafigura.interview.model.Trade;
import com.trafigura.interview.repository.PositionRepository;
import com.trafigura.interview.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock private PositionRepository positionRepository;
    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TransactionService service;

    private Trade trade;

    private TradeDto tradeDto;

    private Position position;

    private PositionDto positionDto;

    @BeforeEach
    public void setup(){
         trade = Trade.builder().secCode("test")
                .tradeAction(TradeAction.BUY)
                .quantity(10)
                .tradeId(1L)
                .tranType(TranType.INSERT)
                .version(1L).build();

        tradeDto = TradeDto.toDto(trade);

        position = Position.builder().secCode("test").quantity(1).build();

        positionDto = PositionDto.toDto(position);
    }
    @Test
    void getAllTradeTrans() {
        Mockito.when(tradeRepository.findAll()).thenReturn(Collections.singletonList(trade));
        assertTrue(service.getAllTradeTrans().size() > 0);
    }

    @Test
    void getAllPositions() {
        Mockito.when(positionRepository.findAll()).thenReturn(Collections.singletonList(position));
        assertTrue(service.getAllPositions().size() > 0);
    }

    @Test
    void createBuyTransactionWithInsertWithPosition() {

        Mockito.when(tradeRepository.findBySecCodeContaining(Mockito.anyString())).thenReturn(Collections.singletonList(trade));
        Mockito.when(tradeRepository.save(Mockito.any())).thenReturn(trade);
        Mockito.when(positionRepository.save(Mockito.any())).thenReturn(position);
        Mockito.when(positionRepository.findBySecCode(Mockito.anyString())).thenReturn(Optional.of(position));

        assertNotNull(service.createTransaction(tradeDto));

    }

    @Test
    void createSellTransactionWithInsertWithPosition() {

        trade.setTradeAction(TradeAction.SELL);
        tradeDto = TradeDto.toDto(trade);
        Mockito.when(tradeRepository.findBySecCodeContaining(Mockito.anyString())).thenReturn(Collections.singletonList(trade));
        Mockito.when(tradeRepository.save(Mockito.any())).thenReturn(trade);
        Mockito.when(positionRepository.save(Mockito.any())).thenReturn(position);
        Mockito.when(positionRepository.findBySecCode(Mockito.anyString())).thenReturn(Optional.of(position));

        assertNotNull(service.createTransaction(tradeDto));
        trade.setTradeAction(TradeAction.BUY);
        Mockito.when(tradeRepository.findByTradeId(Mockito.anyLong())).thenReturn(Optional.of(trade));
        assertNull(service.createTransaction(tradeDto));

    }
    @Test
    void createTransactionWithInsertWithEmptyPosition() {

        Mockito.when(tradeRepository.findBySecCodeContaining(Mockito.anyString())).thenReturn(Collections.singletonList(trade));
        Mockito.when(tradeRepository.save(Mockito.any())).thenReturn(trade);
        Mockito.when(positionRepository.save(Mockito.any())).thenReturn(position);
        Mockito.when(positionRepository.findBySecCode(Mockito.anyString())).thenReturn(Optional.empty());

        assertNotNull(service.createTransaction(tradeDto));

    }

    @Test
    void createTransactionWithCancel() {

        trade.setTranType(TranType.CANCEL);
        tradeDto = TradeDto.toDto(trade);

        Mockito.when(tradeRepository.findBySecCodeContaining(Mockito.anyString())).thenReturn(Collections.singletonList(trade));
        Mockito.when(tradeRepository.save(Mockito.any())).thenReturn(trade);
        Mockito.when(positionRepository.save(Mockito.any())).thenReturn(position);
        Mockito.when(positionRepository.findBySecCode(Mockito.anyString())).thenReturn(Optional.of(position));

        Mockito.when(tradeRepository.findByTradeId(Mockito.anyLong())).thenReturn(Optional.of(trade));

        assertNotNull(service.createTransaction(tradeDto));

    }

    @Test
    void createTransactionWithUpdate() {

        trade.setTranType(TranType.UPDATE);
        tradeDto = TradeDto.toDto(trade);

        Mockito.when(tradeRepository.findBySecCodeContaining(Mockito.anyString())).thenReturn(Collections.singletonList(trade));
        Mockito.when(tradeRepository.save(Mockito.any())).thenReturn(trade);
        Mockito.when(positionRepository.save(Mockito.any())).thenReturn(position);
        Mockito.when(positionRepository.findBySecCode(Mockito.anyString())).thenReturn(Optional.of(position));

        Mockito.when(tradeRepository.findByTradeId(Mockito.anyLong())).thenReturn(Optional.of(trade));

        assertNotNull(service.createTransaction(tradeDto));

    }
}