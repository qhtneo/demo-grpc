package com.example.grpcclient.service;

import com.example.grpcclient.controller.dto.BoardDto.BoardSaveRequestDto;
import com.example.grpcclient.domain.Board;

public interface BoardClientService {
    Board save(Board board);
    Board save(BoardSaveRequestDto dto);
}
