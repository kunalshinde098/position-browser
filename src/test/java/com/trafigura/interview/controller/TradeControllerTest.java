package com.trafigura.interview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trafigura.interview.common.TradeAction;
import com.trafigura.interview.common.TranType;
import com.trafigura.interview.dto.PositionDto;
import com.trafigura.interview.dto.TradeDto;
import com.trafigura.interview.model.Position;
import com.trafigura.interview.model.Trade;
import com.trafigura.interview.service.TransactionService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TradeController.class)
class TradeControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TransactionService service;

    private TradeDto tradeDto;

    private PositionDto positionDto;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        tradeDto = TradeDto.toDto(Trade.builder().secCode("test")
                .tradeAction(TradeAction.BUY)
                .quantity(10)
                .tradeId(1L)
                .tranType(TranType.INSERT)
                .version(1L).build());

        positionDto = PositionDto.toDto(Position.builder().secCode("test").quantity(1).build());
    }


    @Test
    @SneakyThrows
    void getAllTradeTran() {

        Mockito.when(service.getAllTradeTrans()).thenReturn(Collections.singletonList(tradeDto));
        ResultActions result = mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @SneakyThrows
    void getAllPositions() {
        Mockito.when(service.getAllPositions()).thenReturn(Collections.singletonList(positionDto));
        ResultActions result = mockMvc.perform(get("/api/positions"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @SneakyThrows
    void createTransaction() {
        Mockito.when(service.createTransaction(Mockito.any(TradeDto.class))).thenReturn(TradeDto.toTrade(tradeDto));
        ResultActions result = mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON)
                                .content(this.mapper.writeValueAsString(tradeDto)))
                    .andExpect(status().isCreated())
                    .andDo(print());

    }
}