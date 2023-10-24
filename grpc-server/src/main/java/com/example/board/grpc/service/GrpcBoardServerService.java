package com.example.board.grpc.service;

import com.example.board.natives.domain.Board;
import com.example.board.natives.entity.types.BoardStatus;
import com.example.board.grpc.lib.BoardGrpc.BoardImplBase;
import com.example.board.grpc.lib.GrpcBoard;
import com.example.board.grpc.lib.GrpcBoardSaveReply;
import com.example.board.grpc.lib.GrpcBoardSaveRequest;
import com.example.board.grpc.lib.GrpcBoardStatus;
import com.example.board.natives.service.BoardSaveUseCase;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.Instant;

@GrpcService
@RequiredArgsConstructor
public class GrpcBoardServerService extends BoardImplBase {
    private final BoardSaveUseCase boardSaveUseCase;

    @Override
    public void save(
            GrpcBoardSaveRequest request,
            StreamObserver<GrpcBoardSaveReply> responseObserver
    ) {

        BoardStatus boardStatus = switch (request.getStatus()) {
            case PENDING -> BoardStatus.PENDING;
            case ACTIVE -> BoardStatus.ACTIVE;
            case BLIND -> BoardStatus.BLIND;
            case REMOVED -> BoardStatus.REMOVED;
            default -> throw new IllegalArgumentException("해당하는 상태가 없음");
        };

        Board domain = Board.builder()
                .categoryId(request.getCategoryId())
                .title(request.getTitle())
                .content(request.getContent())
                .status(boardStatus)
                .createdAt(Instant.now())
                .build();

        Board savedDomain = boardSaveUseCase.save(domain);

        GrpcBoard grpcBoard = GrpcBoard.newBuilder()
                .setBoardId(savedDomain.getId())
                .setCategoryId(savedDomain.categoryId)
                .setTitle(savedDomain.title)
                .setContent(savedDomain.content)
                .setStatus(
                        switch (savedDomain.status) {
                            case PENDING -> GrpcBoardStatus.PENDING;
                            case ACTIVE -> GrpcBoardStatus.ACTIVE;
                            case BLIND -> GrpcBoardStatus.BLIND;
                            case REMOVED -> GrpcBoardStatus.REMOVED;
                        }
                )
                .addAllTag(savedDomain.tagList)
                .setCreatedAt(
                        Timestamp.newBuilder()
                                .setSeconds(savedDomain.createdAt.getEpochSecond())
                                .setNanos(savedDomain.createdAt.getNano())
                                .build()
                )
                .build();

        GrpcBoardSaveReply reply = GrpcBoardSaveReply.newBuilder()
                .setBoard(grpcBoard)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
