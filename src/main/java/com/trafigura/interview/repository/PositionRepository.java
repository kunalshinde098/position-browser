package com.trafigura.interview.repository;

import com.trafigura.interview.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Kunal
 */
public interface PositionRepository extends JpaRepository<Position, Long> {

    Optional<Position> findBySecCode(String secCode);
}
