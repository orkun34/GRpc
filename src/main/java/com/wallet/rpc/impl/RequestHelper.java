package com.wallet.rpc.impl;

import com.wallet.rpc.impl.operation.OperateRounds;
import org.springframework.stereotype.Component;

@Component
public class RequestHelper extends OperateRounds {

    /**
     *  Handles ClientCLI service in order to run proper scenario.
     *
     * @param numberOfUsers
     * @param threadPerUser
     * @param roundPerThread
     * @throws InterruptedException
     */
    protected void handleRequest(final int numberOfUsers,final int threadPerUser,final int roundPerThread) throws InterruptedException {

        //long before = System.currentTimeMillis();
        for (int i=0;i<numberOfUsers;i++) {
            final int userId = i+1;
            for (int j=0;j<threadPerUser;j++){
                Thread thead = new Thread(() ->{
                    for (int k=0;k<roundPerThread;k++){
                        doRound(userId);
                    }
                });
                thead.start();
                thead.join();
            }
        }

        //long after = System.currentTimeMillis();
        //System.out.println("GAP ===>>>"+(after-before));

    }
}
