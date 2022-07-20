package com.trafigura.interview.controller;

import com.trafigura.interview.dto.PositionDto;
import com.trafigura.interview.dto.TradeDto;
import com.trafigura.interview.model.Trade;
import com.trafigura.interview.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kunal
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TradeController {
    private static final Logger log = LoggerFactory.getLogger(TradeController.class);
    @Autowired private final TransactionService service;

    @GetMapping("/transactions")
    public ResponseEntity<List<TradeDto>> getAllTradeTran() {
        try {
            return new ResponseEntity<>(service.getAllTradeTrans(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/positions")
    public ResponseEntity<List<PositionDto>> getAllPositions() {
        try {
            return new ResponseEntity<>(service.getAllPositions(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/transactions")
    public ResponseEntity<TradeDto> createTransaction(@RequestBody TradeDto trade) {
        try {
           ;
            return new ResponseEntity<>(TradeDto.toDto(service.createTransaction(trade)), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
