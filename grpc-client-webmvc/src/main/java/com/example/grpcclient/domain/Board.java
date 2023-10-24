package com.example.grpcclient.domain;

import com.example.grpcclient.domain.types.BoardStatus;
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
    public String content;
    public List<String> tagList;
    public BoardStatus status;

    public Instant createdAt;
    public Instant updatedAt;
}
