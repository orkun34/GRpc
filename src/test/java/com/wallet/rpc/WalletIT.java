package com.wallet.rpc;

import com.wallet.rpc.impl.WalletServiceImpl;
import com.wallet.rpc.wallet.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mockingDetails;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test-db-spring.properties")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@ContextConfiguration(classes = {TestDatabaseConfig.class, WalletIT.Config.class})
@SpringBootTest(classes = {App.class},properties = {"grpc.enableReflection=true",
        "grpc.port=6565"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WalletIT {

    private static final MapSqlParameterSource NULL_MAP = new MapSqlParameterSource();

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    WalletServiceImpl walletServiceImpl;

    private ManagedChannel channel;

    @Before
    public void setProtocolChannel() {

        channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
    }

    @Test
    public void t0001_withdraw200dollar(){

        Wallet wallet = Wallet.newBuilder().setUserId(1).setCurrency("USD").setAmount(200).build();

        WalletServiceGrpc.WalletServiceBlockingStub walletClient = WalletServiceGrpc.newBlockingStub(channel);
        WalletResponse response = walletClient.withdraw(WalletRequest.newBuilder().setWallet(wallet).build());
        assertEquals(response.getErrorMsg(),"insufficient_funds");
    }

    @Test
    public void t0002_deposit100dollar(){
        Wallet wallet = Wallet.newBuilder().setUserId(1).setCurrency("USD").setAmount(100).build();

        WalletServiceGrpc.WalletServiceBlockingStub walletClient = WalletServiceGrpc.newBlockingStub(channel);
        WalletResponse response = walletClient.deposit(WalletRequest.newBuilder().setWallet(wallet).build());
        assertEquals(response.getErrorMsg(),"");
    }

    @Test
    public void t0003_checkBalancePhase(){
        Balance balance = Balance.newBuilder().setUserId(1).build();

        WalletServiceGrpc.WalletServiceBlockingStub walletClient = WalletServiceGrpc.newBlockingStub(channel);
        BalanceResponse response = walletClient.checkBalance(BalanceRequest.newBuilder().setBalance(balance).build());
        Assert.assertTrue(response.getWalletCount() > 0);
    }

    @Test
    public void t0004_withdraw200dollar(){
        Wallet wallet = Wallet.newBuilder().setUserId(1).setCurrency("USD").setAmount(200).build();

        WalletServiceGrpc.WalletServiceBlockingStub walletClient = WalletServiceGrpc.newBlockingStub(channel);
        WalletResponse response = walletClient.withdraw(WalletRequest.newBuilder().setWallet(wallet).build());
        assertEquals(response.getErrorMsg(),"insufficient_funds");
    }

    @Test
    public void t0005_deposit100euro(){
        Wallet wallet = Wallet.newBuilder().setUserId(1).setCurrency("EUR").setAmount(100).build();

        WalletServiceGrpc.WalletServiceBlockingStub walletClient = WalletServiceGrpc.newBlockingStub(channel);
        WalletResponse response = walletClient.deposit(WalletRequest.newBuilder().setWallet(wallet).build());
        assertEquals(response.getErrorMsg(),"");
    }

    @Test
    public void t0006_checkBalancePhase(){
        Balance balance = Balance.newBuilder().setUserId(1).build();

        WalletServiceGrpc.WalletServiceBlockingStub walletClient = WalletServiceGrpc.newBlockingStub(channel);
        BalanceResponse response = walletClient.checkBalance(BalanceRequest.newBuilder().setBalance(balance).build());
        Assert.assertTrue(response.getWalletCount() > 0);
    }

    @Test
    public void t0007_withdraw200dollar(){
        Wallet wallet = Wallet.newBuilder().setUserId(1).setCurrency("USD").setAmount(200).build();

        WalletServiceGrpc.WalletServiceBlockingStub walletClient = WalletServiceGrpc.newBlockingStub(channel);
        WalletResponse response = walletClient.withdraw(WalletRequest.newBuilder().setWallet(wallet).build());
        assertEquals(response.getErrorMsg(),"insufficient_funds");
    }

    @Test
    public void t0008_deposit100dolar(){
        Wallet wallet = Wallet.newBuilder().setUserId(1).setCurrency("USD").setAmount(100).build();

        WalletServiceGrpc.WalletServiceBlockingStub walletClient = WalletServiceGrpc.newBlockingStub(channel);
        WalletResponse response = walletClient.deposit(WalletRequest.newBuilder().setWallet(wallet).build());
        assertEquals(response.getErrorMsg(),"");
    }

    @Test
    public void t0009_checkBalancePhase(){
        Balance balance = Balance.newBuilder().setUserId(1).build();

        WalletServiceGrpc.WalletServiceBlockingStub walletClient = WalletServiceGrpc.newBlockingStub(channel);
        BalanceResponse response = walletClient.checkBalance(BalanceRequest.newBuilder().setBalance(balance).build());
        Assert.assertTrue(response.getWalletCount() > 0);
    }

    @Test
    public void t0010_withdraw200dollar(){
        Wallet wallet = Wallet.newBuilder().setUserId(1).setCurrency("USD").setAmount(200).build();
        /*StreamObserver<WalletResponse> observer = mock(StreamObserver.class);
        walletServiceImpl.deposit(WalletRequest.newBuilder().setWallet(wallet).build(),observer);
        */
        WalletServiceGrpc.WalletServiceBlockingStub walletClient = WalletServiceGrpc.newBlockingStub(channel);
        WalletResponse response = walletClient.withdraw(WalletRequest.newBuilder().setWallet(wallet).build());
        assertEquals(response.getErrorMsg() == "" ? "ok" : "","ok");
    }

    @Test
    public void t0011_checkBalancePhase(){
        Balance balance = Balance.newBuilder().setUserId(1).build();

        WalletServiceGrpc.WalletServiceBlockingStub walletClient = WalletServiceGrpc.newBlockingStub(channel);
        BalanceResponse response = walletClient.checkBalance(BalanceRequest.newBuilder().setBalance(balance).build());
        Assert.assertTrue(response.getWalletCount() > 0);
    }

    @Test
    public void t0012_withdraw200dollar(){
        Wallet wallet = Wallet.newBuilder().setUserId(1).setCurrency("USD").setAmount(200).build();
        /*StreamObserver<WalletResponse> observer = mock(StreamObserver.class);
        walletServiceImpl.deposit(WalletRequest.newBuilder().setWallet(wallet).build(),observer);
        */
        WalletServiceGrpc.WalletServiceBlockingStub walletClient = WalletServiceGrpc.newBlockingStub(channel);
        WalletResponse response = walletClient.withdraw(WalletRequest.newBuilder().setWallet(wallet).build());
        assertEquals(response.getErrorMsg(),"insufficient_funds");
    }

    @Configuration
    static class Config {

        // prevent autowiring the mocks
        @Bean
        InstantiationAwareBeanPostProcessorAdapter mockBeanFactory() {
            return new InstantiationAwareBeanPostProcessorAdapter() {

                @Override
                public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
                    return !mockingDetails(bean).isMock();
                }
            };
        }

        @Bean
        @Autowired
        public InitDatabase initDatabase(@Autowired NamedParameterJdbcTemplate jdbcTemplate){
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                    .addValue("TABLE_SCHEMA","WALLET").addValue("TABLE_NAME","BANK");

            String isTableExistsQuery =
                    "SELECT count(*) FROM INFORMATION_SCHEMA.TABLES " +
                    "WHERE TABLE_SCHEMA = :TABLE_SCHEMA " +
                    "AND TABLE_NAME = :TABLE_NAME";

            if (jdbcTemplate.queryForObject(isTableExistsQuery
                    , mapSqlParameterSource, Long.class) > 0){
                jdbcTemplate.update("DROP TABLE BANK",NULL_MAP);
            }

            String createTestTableSql =
            "CREATE TABLE bank " +
                    "(id_transaction bigint not null auto_increment," +
                    " balance bigint, " +
                    "currency varchar(255), " +
                    "user_id bigint, " +
                    "primary key (id_transaction)) engine=MyISAM";
            jdbcTemplate.getJdbcOperations().execute(createTestTableSql);
            return new InitDatabase();
        }
        static class InitDatabase {}

    }
}
