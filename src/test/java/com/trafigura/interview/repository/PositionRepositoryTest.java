package com.trafigura.interview.repository;

import com.trafigura.interview.model.Position;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PositionRepositoryTest {
    @Autowired private PositionRepository repository;


    @Test
    public void testPersistPosition(){

        Position posRep = Position.builder().secCode("test").quantity(1).build();
        assertNull(posRep.getPosId());
        repository.save(posRep);
        assertNotNull(posRep.getPosId());
    }

    @Test
    public void testRetrievalPosition() {

        Position posRep = Position.builder().secCode("test").quantity(1).build();
        repository.save(posRep);

        assertEquals(1, repository.findBySecCode("test").stream().filter(pos -> pos.getSecCode().contains("test")).count());
    }

}
