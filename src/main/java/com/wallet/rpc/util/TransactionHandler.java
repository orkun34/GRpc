package com.wallet.rpc.util;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;

@Component
public class TransactionHandler {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <V> V callInNewTransaction(final Callable<V> callable) throws Exception {
        return callable.call();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void runInNewTransaction(final Runnable runnable) {
        runnable.run();
    }
}
