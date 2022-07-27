package com.trafigura.interview.dto;

import com.trafigura.interview.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionDtoTest {

    @Test
    void toDto() {
        Position pos = Position.builder().secCode("test").build();
        assertEquals("test", PositionDto.toDto(pos).getSecCode());
    }

    @Test
    void toPosition() {
        PositionDto dto = PositionDto.builder().secCode("test").build();
        assertEquals("test", PositionDto.toPosition(dto).getSecCode());
    }
}