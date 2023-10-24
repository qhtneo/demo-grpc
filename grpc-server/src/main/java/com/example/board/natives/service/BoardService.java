package com.example.board.natives.service;

import com.example.board.natives.entity.BoardEntity;
import com.example.board.natives.domain.Board;
import com.example.board.natives.repository.BoardRepository;
import com.example.board.natives.entity.TagEntity;
import com.example.board.natives.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService implements BoardSaveUseCase {

    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;

    @Override
    public Board save(Board board) {
        BoardEntity entity = BoardEntity.builder()

                .build();

        BoardEntity savedEntity = boardRepository.save(entity);

        List<TagEntity> tagList = board.tagList.stream()
                .map((name) -> TagEntity.builder()
                        .boardId(savedEntity.getId())
                        .name(name)
                        .build()
                )
                .toList();

        List<TagEntity> savedTagList = tagRepository.saveAll(tagList);

       return Board.builder()
               .id(savedEntity.getId().toString())
               .categoryId(savedEntity.categoryId)
               .title(savedEntity.title)
               .content(savedEntity.content)
               .status(savedEntity.status)
               .createdAt(savedEntity.createdAt)
               .updatedAt(savedEntity.updatedAt)
               .tagList(
                       savedTagList.stream()
                               .map(TagEntity::toString)
                               .toList()
               )
               .build();
    }
}
