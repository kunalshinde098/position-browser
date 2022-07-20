package com.trafigura.interview.repository;

import com.trafigura.interview.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findBySecCodeContaining(String secCode);
    Optional<Trade> findByTradeIdAndVersion(Long tradeId, Long version);
    Optional<Trade> findByTradeId(Long tradeId);
}
