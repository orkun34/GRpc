package com.wallet.rpc.model;

import javax.persistence.*;

@Entity
public class Bank {

    @Column(name = "id_transaction")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTransaction;

    @Column
    private long balance;

    @Column
    private String currency;

    @Column(name = "user_id")
    private long user;

    public long getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(long idTransaction) {
        this.idTransaction = idTransaction;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }
}
