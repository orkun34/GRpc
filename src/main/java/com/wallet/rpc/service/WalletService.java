package com.wallet.rpc.service;

import com.wallet.rpc.dao.WalletRepo;
import com.wallet.rpc.util.ConstantHolder;
import com.wallet.rpc.wallet.BalanceResponse;
import com.wallet.rpc.wallet.Wallet;
import com.wallet.rpc.wallet.WalletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class WalletService implements IWallet {

    @Autowired
    WalletRepo walletRepo;

    AtomicInteger counter = new AtomicInteger(0);

    @Override
    public WalletResponse deposit(final Long userId, final Long amount, final String currency) throws Exception {
        if(ConstantHolder.CURRENCY_TYPES.contains(currency)) {
            BalanceResponse balanceResponse = checkBalance(userId);

            final boolean isPresent = balanceResponse.getWalletList()
                    .stream()
                    .anyMatch(value -> value.getCurrency().equals(currency) &&
                            value.getUserId() == userId);

            walletRepo.deposit(Wallet.getDefaultInstance().toBuilder()
                            .setAmount(isPresent ? balanceResponse.getWalletList().
                                    stream()
                                    .filter(value ->value.getUserId() == userId && value.getCurrency().equals(currency))
                                    .map(Wallet::getAmount).findAny()
                                    .get() + amount : amount)
                            .setCurrency(currency)
                            .setUserId(userId)
                            .build()
                    , isPresent);

            return WalletResponse.getDefaultInstance().toBuilder().setErrorMsg("").build();

        }

        return WalletResponse.getDefaultInstance().toBuilder().setErrorMsg("unknown_currency").build();
    }

    @Override
    public WalletResponse withdraw(final Long userId, final Long amount, final String currency) throws Exception {
        Wallet wallet = null;

        if(ConstantHolder.CURRENCY_TYPES.contains(currency))  {
            BalanceResponse balanceResponse = checkBalance(userId);
            final boolean isPresentAndSufficient = balanceResponse.getWalletList()
                    .stream()
                    .anyMatch(value -> value.getAmount() >= amount
                            && value.getCurrency().equals(currency));


            if (isPresentAndSufficient) {

                wallet = balanceResponse.getWalletList()
                        .stream()
                        .filter(value -> value.getCurrency().equals(currency))
                        .findFirst()
                        .get();

                final Long currentBalance = wallet.getAmount();
                wallet = wallet.toBuilder().setAmount(currentBalance - amount).setUserId(userId).build();
                walletRepo.withdraw(wallet, currentBalance.equals(amount));
            }

            return WalletResponse
                    .getDefaultInstance()
                    .toBuilder()
                    .setErrorMsg(wallet == null ? "insufficient_funds" : "").build();
        }

        return WalletResponse
                .getDefaultInstance()
                .toBuilder()
                .setErrorMsg("unknown_currency").build();

    }

    @Override
    public BalanceResponse checkBalance(final Long userId) throws Exception {
        return walletRepo.checkBalance(userId);
    }

    @Override
    public void purgeUsers(){
        walletRepo.purgeUsers();
    }
}
