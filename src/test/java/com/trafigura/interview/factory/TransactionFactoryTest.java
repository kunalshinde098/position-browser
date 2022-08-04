package com.trafigura.interview.factory;

import com.trafigura.interview.common.TranType;
import com.trafigura.interview.service.CancelTransactionService;
import com.trafigura.interview.service.InsertTransactionService;
import com.trafigura.interview.service.UpdateTransactionService;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class TransactionFactoryTest {

    private TransactionFactory factory;

    @Test
    public void testGetService() {
        this.factory = new TransactionFactory();
        assertTrue(factory.getService(TranType.INSERT).get() instanceof InsertTransactionService);
        assertTrue(factory.getService(TranType.UPDATE).get() instanceof UpdateTransactionService);
        assertTrue(factory.getService(TranType.CANCEL).get() instanceof CancelTransactionService);
        assertTrue(factory.getService(null).isEmpty());
    }
}