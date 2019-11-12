package com.wallet.rpc.impl;

import com.wallet.rpc.service.IWallet;
import com.wallet.rpc.wallet.*;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Implemented GRpc class that handles requests
 */
@GRpcService
public class WalletServiceImpl extends WalletServiceGrpc.WalletServiceImplBase  {

    private static final Logger LOGGER = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Autowired
    IWallet walletServce;

    @Autowired
    RequestHelper requestHelper;

    @Override
    public void clientCLI(ClientRequest request, StreamObserver<ClientResponse> responseObserver) {
        walletServce.purgeUsers();
        try {
            requestHelper.handleRequest(request.getUserCount(),request.getThreadPerUser(),request.getThreadPerRound());
        } catch (InterruptedException e) {
            LOGGER.error("Thread interruption error",e);
        }
        responseObserver.onNext(null);
        responseObserver.onCompleted();
    }

    @Override
    public void withdraw(WalletRequest request, StreamObserver<WalletResponse> responseObserver) {
        WalletResponse response = null;
        try {
           response = walletServce.withdraw(request.getWallet().getUserId(),request.getWallet().getAmount(),request.getWallet().getCurrency());
        } catch (Exception e) {
            LOGGER.error("Unable to process withdraw , ex {}",e);
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void checkBalance(BalanceRequest request, StreamObserver<BalanceResponse> responseObserver) {
        try {
            responseObserver.onNext(walletServce.checkBalance(request.getBalance().getUserId()));
        } catch (Exception e) {
            LOGGER.error("Unable to process checkBalance , ex {}",e);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void deposit(WalletRequest request, StreamObserver<WalletResponse> responseObserver) {

        WalletResponse response = null;
        try {
            response = walletServce
                    .deposit(request.getWallet().getUserId(),request.getWallet().getAmount(),request.getWallet().getCurrency());
        } catch (Exception e) {
            LOGGER.error("Unable to process deposit , ex {}",e);
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    /**
     * Echoes request headers with the specified key(s) from a client into response trailers only.
     */
    private static ServerInterceptor echoRequestMetadataInTrailers(final Metadata.Key<?>... keys) {
        final Set<Metadata.Key<?>> keySet = new HashSet<Metadata.Key<?>>(Arrays.asList(keys));
        return new ServerInterceptor() {
            @Override
            public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
                    ServerCall<ReqT, RespT> call,
                    final Metadata requestHeaders,
                    ServerCallHandler<ReqT, RespT> next) {
                return next.startCall(new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
                    @Override
                    public void sendHeaders(Metadata responseHeaders) {
                        super.sendHeaders(responseHeaders);
                    }

                    @Override
                    public void close(Status status, Metadata trailers) {
                        trailers.merge(requestHeaders, keySet);
                        super.close(status, trailers);
                    }
                }, requestHeaders);
            }
        };
    }

}
