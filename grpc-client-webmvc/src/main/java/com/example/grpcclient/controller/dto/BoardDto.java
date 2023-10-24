package com.example.grpcclient.controller.dto;

import com.example.board.grpc.lib.GrpcBoardStatus;
import com.example.grpcclient.domain.types.BoardStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Collections;
import java.util.List;

public final class BoardDto {

    private BoardDto() {}

    @Builder
    public record BoardSaveRequestDto(
            @NotBlank
            String categoryId,

            @NotBlank
            String title,

            @NotBlank
            String content,

            List<String> tagList,

            @NotNull
            BoardStatus status // 원래는 도메인 거 제공받아서 사용. (데모라서 편의상 대충 넘김.)
    ) {
        public BoardSaveRequestDto {
            if (tagList == null) {
                tagList = Collections.emptyList();
            }

            switch (status) {
                case BLIND, REMOVED -> throw new RuntimeException("...");
            }
        }
    }

    @Builder
    public record BoardSaveResponseDto(
            String boardId
    ) {}
}
