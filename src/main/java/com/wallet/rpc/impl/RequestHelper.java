package com.wallet.rpc.impl;

import com.wallet.rpc.impl.operation.OperateRounds;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class RequestHelper extends OperateRounds {


    ScheduledExecutorService executor;

    protected void handleRequest(int numberOfUsers, int threadPerUser, int roundPerThread) throws InterruptedException {

        /*executor = Executors.newScheduledThreadPool(threadPerUser);

        for (int i=1;i<=numberOfUsers;i++) {
            final int userId = i;
                executor.schedule(() -> {
                    for (int j = 1; j <= roundPerThread; j++) {
                            doRound(userId);
                    }
                }, 0, TimeUnit.SECONDS);
        }*/
        for (int i=1;i<=numberOfUsers;i++) {
            final int userId = i;
            for (int j=1;j<=threadPerUser;j++){
                Thread thead = new Thread(() ->{
                    for (int k=1;k<=roundPerThread;k++){
                        doRound(userId);
                    }
                });
                thead.start();
                thead.join();
            }
        }

    }
}
