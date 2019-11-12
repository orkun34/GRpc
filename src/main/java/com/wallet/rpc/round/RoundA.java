package com.wallet.rpc.round;

import com.wallet.rpc.wallet.*;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

public class RoundA extends Round{

    private WalletServiceGrpc.WalletServiceBlockingStub walletClient;

    public RoundA()
    {
        channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
        walletClient = WalletServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public void doRound(int userId) {
        deposit100USD(userId);
        withdraw200USD(userId);
        deposit100EUR(userId);
        checkBalance(userId);
        withdraw100USD(userId);
        checkBalance(userId);
        withdraw100USD(userId);
        channel.shutdown();
    }

    public void deposit100USD(int userId){
        Wallet wallet = Wallet.newBuilder().setUserId(userId).setCurrency("USD").setAmount(100).build();
        walletClient.deposit(WalletRequest.newBuilder().setWallet(wallet).build());
    }

    public void withdraw200USD(int userId){
        Wallet wallet = Wallet.newBuilder().setUserId(userId).setCurrency("USD").setAmount(200).build();
        walletClient.withdraw(WalletRequest.newBuilder().setWallet(wallet).build());
    }

    public void checkBalance(int userId){
        Balance balance = Balance.newBuilder().setUserId(userId).build();
        walletClient.checkBalance(BalanceRequest.newBuilder().setBalance(balance).build());
    }

    public void deposit100EUR(int userId){
        Wallet wallet = Wallet.newBuilder().setUserId(userId).setCurrency("EUR").setAmount(100).build();
        walletClient.deposit(WalletRequest.newBuilder().setWallet(wallet).build());
    }

    public void withdraw100USD(int userId){
        Wallet wallet = Wallet.newBuilder().setUserId(userId).setCurrency("USD").setAmount(100).build();
        walletClient.withdraw(WalletRequest.newBuilder().setWallet(wallet).build());
    }

}
