package com.kingcjy.was.application.test;

import com.kingcjy.was.core.annotations.Autowired;
import com.kingcjy.was.core.annotations.web.Service;
import com.kingcjy.was.core.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public List<BoardDto.BoardResponseDto> findAllBoards() {
        return boardRepository.findAllBoardsOrderByIdDesc().stream().map(BoardDto.BoardResponseDto::new).collect(Collectors.toList());
    }
}
