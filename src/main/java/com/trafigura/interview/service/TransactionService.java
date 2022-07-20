package com.trafigura.interview.service;

import com.trafigura.interview.common.TradeAction;
import com.trafigura.interview.common.TranType;
import com.trafigura.interview.dto.PositionDto;
import com.trafigura.interview.dto.TradeDto;
import com.trafigura.interview.model.Position;
import com.trafigura.interview.model.Trade;
import com.trafigura.interview.repository.PositionRepository;
import com.trafigura.interview.repository.TradeRepository;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Getter
@ToString
@Builder
@RequiredArgsConstructor
public class TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    @Autowired private final PositionRepository positionRepository;
    @Autowired private final TradeRepository tradeRepository;

    public List<TradeDto> getAllTradeTrans(){
        final List<TradeDto> lstTrade = tradeRepository.findAll()
                .stream().map(TradeDto::toDto).collect(Collectors.toUnmodifiableList());
        return lstTrade;
    }

    public List<PositionDto> getAllPositions(){
        final List<PositionDto> lstPosition = positionRepository.findAll()
                .stream().map(PositionDto::toDto).collect(Collectors.toUnmodifiableList());
        log.debug(lstPosition.toString());
        return lstPosition;
    }

    public Trade createTransaction(TradeDto tradeDto) {

        Trade trade = TradeDto.toTrade(tradeDto);
        Optional<Trade> dbTrade = tradeRepository.findByTradeId(trade.getTradeId());

        if(dbTrade.isPresent() && trade.getTranType().equals(TranType.UPDATE)){
            Trade tradeRef = dbTrade.get();
            tradeRef.setSecCode(trade.getSecCode());
            tradeRef.setQuantity(trade.getQuantity());
            tradeRef.setVersion(1+tradeRef.getVersion()); // increment version
            tradeRef.setTranType(trade.getTranType());
            trade = tradeRef;


        }else if (dbTrade.isPresent() && trade.getTranType().equals(TranType.CANCEL)){
            Trade tradeRef = dbTrade.get();
            tradeRef.setSecCode(trade.getSecCode());
            tradeRef.setQuantity(trade.getQuantity());
            tradeRef.setVersion(1+tradeRef.getVersion()); // increment version
            tradeRef.setTranType(trade.getTranType());
            trade = tradeRef;

        } else if(dbTrade.isPresent() && trade.getTranType().equals(TranType.INSERT) && ! dbTrade.get().getTradeAction().equals(trade.getTradeAction())){
            log.error("UPDATE is already received :: Skipping thi transaction");
            return null;

        } else if(dbTrade.isPresent() && ! trade.getTranType().equals(TranType.INSERT)) {
            log.error("Skipping as not valid transaction type");
            return null;
        }
        trade = tradeRepository.save(trade);

        List<Trade> latestTrade = tradeRepository.findBySecCodeContaining(trade.getSecCode());

        Optional<Position> pos = positionRepository.findBySecCode(trade.getSecCode());

        AtomicReference<Position> finalPos = new AtomicReference<>();

        if(pos.isEmpty() && trade.getTranType().equals(TranType.INSERT)){
            finalPos.set(Position.builder().quantity(trade.getQuantity()).secCode(trade.getSecCode()).build());
        } else if(trade.getTranType().equals(TranType.INSERT)){
            if(pos.isPresent()){
                if (trade.getTradeAction().equals(TradeAction.BUY)) {
                    pos.get().setQuantity(pos.get().getQuantity() + trade.getQuantity());
                } else {
                    pos.get().setQuantity(pos.get().getQuantity() - trade.getQuantity());
                }
            }
        } else if (trade.getTranType().equals(TranType.UPDATE)) {
            if(pos.isPresent()){
                pos.get().setQuantity(trade.getQuantity());
            }

        } else if (trade.getTranType().equals(TranType.CANCEL)){
            pos.get().setQuantity(0);
        }

        if(pos.isEmpty()){
            positionRepository.save(finalPos.get());
        }else{
            positionRepository.save(pos.get());
        }

        log.debug("Trade filtered {} for secCode {}", latestTrade, trade.getSecCode());

        return trade;
    }
}
