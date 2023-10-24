package com.example.grpcclient.config;

import com.example.board.grpc.lib.BoardGrpc;
import com.example.board.grpc.lib.BoardGrpc.BoardBlockingStub;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcStubConfig {

    @Bean
    public BoardBlockingStub boardBlockingStub() {
        return BoardGrpc.newBlockingStub(
                ManagedChannelBuilder
                        .forAddress("localhost", 13080)
                        .usePlaintext()
                        .build()
        );
    }

}
