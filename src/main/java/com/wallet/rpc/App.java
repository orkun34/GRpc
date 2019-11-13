package com.wallet.rpc;

import com.wallet.rpc.impl.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class App {

    @Autowired
    WalletServiceImpl walletService;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(App.class, args);
    }
}
