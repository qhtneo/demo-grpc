package com.example.grpcclient.service;

import com.example.board.grpc.lib.BoardGrpc.BoardBlockingStub;
import com.example.board.grpc.lib.GrpcBoard;
import com.example.board.grpc.lib.GrpcBoardSaveRequest;
import com.example.grpcclient.controller.dto.BoardDto.BoardSaveRequestDto;
import com.example.grpcclient.domain.Board;
import com.example.grpcclient.domain.types.BoardStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class GrpcBoardClientService implements BoardClientService {
    private final BoardBlockingStub boardBlockingStub;

    @Override
    public Board save(Board board) {
        GrpcBoardSaveRequest dto = GrpcBoardSaveRequest.newBuilder()
                .setCategoryId(board.categoryId)
                .setTitle(board.title)
                .setContent(board.content)
                .addAllTag(board.tagList)
                .build();

        GrpcBoard savedBoard = boardBlockingStub.save(dto)
                .getBoard();

        return Board.builder()
                .id(savedBoard.getBoardId())
                .categoryId(savedBoard.getCategoryId())
                .title(savedBoard.getTitle())
                .content(savedBoard.getContent())
                .status(
                        switch (savedBoard.getStatus()) {
                            case PENDING -> BoardStatus.PENDING;
                            case ACTIVE -> BoardStatus.ACTIVE;
                            case BLIND -> BoardStatus.BLIND;
                            case REMOVED -> BoardStatus.REMOVED;
                            case UNRECOGNIZED -> null;
                        }
                )
                .createdAt(
                        Instant.ofEpochSecond(
                                savedBoard.getCreatedAt().getSeconds(),
                                savedBoard.getCreatedAt().getNanos()
                        )
                )
                .updatedAt(
                        Instant.ofEpochSecond(
                                savedBoard.getUpdatedAt().getSeconds(),
                                savedBoard.getUpdatedAt().getNanos()
                        )
                )
                .tagList(savedBoard.getTagList())
                .build();
    }

    @Override
    public Board save(BoardSaveRequestDto dto) {
        Board board = Board.builder()
                .categoryId(dto.categoryId())
                .title(dto.title())
                .content(dto.content())
                .tagList(dto.tagList())
                .status(dto.status())
                .build();

        return save(board);
    }
}
