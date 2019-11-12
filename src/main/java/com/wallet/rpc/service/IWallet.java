package com.wallet.rpc.service;

import com.wallet.rpc.wallet.BalanceResponse;
import com.wallet.rpc.wallet.WalletResponse;

public interface IWallet {

    WalletResponse withdraw(Long userId, Long amount, String currency) throws Exception;

    WalletResponse deposit(Long userId,Long amount,String currency) throws Exception;

    BalanceResponse checkBalance(Long userId) throws Exception;

    void purgeUsers();
}
