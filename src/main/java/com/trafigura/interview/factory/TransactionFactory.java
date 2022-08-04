package com.trafigura.interview.factory;

import com.trafigura.interview.common.TranType;
import com.trafigura.interview.service.CancelTransactionService;
import com.trafigura.interview.service.InsertTransactionService;
import com.trafigura.interview.service.TransactionService;
import com.trafigura.interview.service.UpdateTransactionService;

import java.util.Optional;

public class TransactionFactory {

    public Optional<TransactionService> getService(TranType tranType){
        if(tranType == null)
            return Optional.empty();
        switch (tranType) {
            case INSERT: return Optional.of(new InsertTransactionService());
            case UPDATE: return Optional.of(new UpdateTransactionService());
            case CANCEL: return Optional.of(new CancelTransactionService());
        }
        return Optional.empty();
    }
}
