package com.trafigura.interview.dto;

import com.trafigura.interview.model.Position;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PositionDto {
    private final String secCode;
    private final Integer quantity;

    public static PositionDto toDto(Position position){
        return PositionDto.builder().quantity(position.getQuantity()).secCode(position.getSecCode()).build();
    }

    public static Position toPosition(PositionDto dto){
        return Position.builder()
                .quantity(dto.quantity)
                .secCode(dto.getSecCode())
                .build();
    }
}
