package com.wallet.rpc.impl.operation;

import com.wallet.rpc.round.RoundA;
import com.wallet.rpc.round.RoundB;
import com.wallet.rpc.round.RoundC;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OperateRounds {

    private final Random random = new Random();

    public void doRound(int userId){
        int round = random.nextInt(3)+1;
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
