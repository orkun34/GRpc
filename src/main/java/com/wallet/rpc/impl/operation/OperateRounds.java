package com.wallet.rpc.impl.operation;

import com.wallet.rpc.round.Round;
import com.wallet.rpc.round.RoundA;
import com.wallet.rpc.round.RoundB;
import com.wallet.rpc.round.RoundC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class OperateRounds {

    private final Random random = new Random();

    public void doRound(int userId){
        int round = random.nextInt(1)+1;
        switch (round){
            case 1:
                new RoundA().doRound(userId);
                break;
            case 2:
                new RoundB().doRound(userId);
                break;
            case 3:
                new RoundC().doRound(userId);
                break;

        }
    }


}
