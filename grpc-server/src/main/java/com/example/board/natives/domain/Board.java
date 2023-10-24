package com.example.board.natives.domain;

import com.example.board.natives.entity.types.BoardStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Builder
public class Board {
    @Getter
    protected String id;
    public String categoryId;

    public String title;
    public Integer content;
    public List<String> tagList;
    public BoardStatus status;

    public Instant createdAt;
    public Instant updatedAt;
}
