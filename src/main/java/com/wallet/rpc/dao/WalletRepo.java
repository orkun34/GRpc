package com.wallet.rpc.dao;

import com.wallet.rpc.util.TransactionHandler;
import com.wallet.rpc.wallet.BalanceResponse;
import com.wallet.rpc.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Repository
public class WalletRepo{

    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void startTask() {
       scheduledExecutorService.scheduleAtFixedRate(() -> {
           System.out.println("Transaction per second ===>>>"+counter);
           counter = 0;
       },0,1,TimeUnit.SECONDS);
    }

    static int counter = 0;


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionHandler transactionHandler;

    public void withdraw(Wallet wallet,boolean isTotalAmount) throws Exception {
        counter++;
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("USER_ID",wallet.getUserId())
                .addValue("AMOUNT",wallet.getAmount())
                .addValue("CURRENCY",wallet.getCurrency());


        transactionHandler.callInNewTransaction(() -> jdbcTemplate.update(
                "UPDATE BANK " +
                        "SET BALANCE = :AMOUNT " +
                        "WHERE USER_ID = :USER_ID " +
                        "AND CURRENCY = :CURRENCY",mapSqlParameterSource));

    }

    public BalanceResponse checkBalance(Long userId) throws Exception {
        //counter++;
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("USER_ID",userId);

           List<Wallet> fetchedResult = transactionHandler.callInNewTransaction(() -> jdbcTemplate
                    .query("SELECT USER_ID,BALANCE,CURRENCY FROM BANK WHERE USER_ID = :USER_ID",mapSqlParameterSource,(rs,rowNum)
                            ->Wallet.newBuilder()
                            .setAmount(rs.getLong("BALANCE"))
                            .setCurrency(rs.getString("CURRENCY"))
                            .setUserId(rs.getLong("USER_ID")).build()));

        return BalanceResponse.newBuilder().addAllWallet(fetchedResult).build();

    }

    public void deposit(Wallet wallet,boolean isPresent){
        //counter++;
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("USER_ID",wallet.getUserId())
                .addValue("AMOUNT",wallet.getAmount())
                .addValue("CURRENCY",wallet.getCurrency());

        if (!isPresent)
            transactionHandler.runInNewTransaction(() -> jdbcTemplate.update(
                    "INSERT INTO BANK(USER_ID,BALANCE,CURRENCY) " +
                        "VALUES (:USER_ID,:AMOUNT,:CURRENCY)",mapSqlParameterSource));
        else
            transactionHandler.runInNewTransaction(() -> jdbcTemplate.update(
                    "UPDATE BANK " +
                            "SET BALANCE = :AMOUNT " +
                            "WHERE USER_ID = :USER_ID " +
                            "AND CURRENCY = :CURRENCY",mapSqlParameterSource));

    }

    public void purgeUsers(){
        transactionHandler.runInNewTransaction(() -> jdbcTemplate.getJdbcOperations().execute("TRUNCATE TABLE BANK"));
    }

}
