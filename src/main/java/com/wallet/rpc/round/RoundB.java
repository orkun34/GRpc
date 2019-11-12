package com.wallet.rpc.round;

import com.wallet.rpc.wallet.*;
import io.grpc.ManagedChannelBuilder;

public class RoundB extends Round{

    private WalletServiceGrpc.WalletServiceBlockingStub walletClient;

    public RoundB()
    {
        channel = ManagedChannelBuilder.forAddress("localhost",6565)
                .usePlaintext()
                .build();
        walletClient = WalletServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public void doRound(int userId) {
        withdraw100GBP(userId);
        deposit300GBP(userId);
        withdraw100GBP(userId);
        withdraw100GBP(userId);
        withdraw100GBP(userId);
        channel.shutdown();
    }

    public void withdraw100GBP(int userId){
        Wallet wallet = Wallet.newBuilder().setUserId(userId).setCurrency("GBP").setAmount(100).build();
        walletClient.withdraw(WalletRequest.newBuilder().setWallet(wallet).build());
    }

    public void deposit300GBP(int userId){
        Wallet wallet = Wallet.newBuilder().setUserId(userId).setCurrency("GBP").setAmount(300).build();
        walletClient.deposit(WalletRequest.newBuilder().setWallet(wallet).build());
    }
}
