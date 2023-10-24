package com.example.board.grpc.server;

import com.example.board.grpc.service.GrpcBoardServerService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcServer implements ApplicationRunner {
    private static final int PORT = 13080;
    private final GrpcBoardServerService grpcBoardServerService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Server server = ServerBuilder.forPort(PORT)
                .addService(grpcBoardServerService)
                .build();

        server.start();
    }
}
