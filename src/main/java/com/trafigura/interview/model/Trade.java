package com.trafigura.interview.model;

import com.trafigura.interview.common.TradeAction;
import com.trafigura.interview.common.TranType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tranId;
    private Long tradeId;
    private Long version;
    private String secCode;
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private TranType tranType;
    @Enumerated(EnumType.STRING)
    private TradeAction tradeAction;

}
