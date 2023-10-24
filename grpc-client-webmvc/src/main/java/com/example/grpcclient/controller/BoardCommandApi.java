package com.example.grpcclient.controller;

import com.example.grpcclient.controller.dto.BoardDto.BoardSaveRequestDto;
import com.example.grpcclient.controller.dto.BoardDto.BoardSaveResponseDto;
import com.example.grpcclient.domain.Board;
import com.example.grpcclient.service.BoardClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("board")
@RequiredArgsConstructor
public final class BoardCommandApi {
    private final BoardClientService boardClientService;

    @PostMapping("")
    public BoardSaveResponseDto save(BoardSaveRequestDto dto){
        Board board =boardClientService.save(dto);

        return BoardSaveResponseDto.builder()
                .boardId(board.getId())
                .build();
    }
}
