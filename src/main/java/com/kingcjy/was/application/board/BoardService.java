package com.kingcjy.was.application.board;

import com.kingcjy.was.core.annotations.Inject;
import com.kingcjy.was.core.annotations.web.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Inject
    private BoardRepository boardRepository;

    public List<BoardDto.BoardListResponseDto> findBoards() {

        List<Board> boards = boardRepository.findAll();

        return boards.stream().map(BoardDto.BoardListResponseDto::new).collect(Collectors.toList());
    }

    public void addBoard(BoardDto.BoardRequestDto dto) {
        Board board = Board.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .build();
        boardRepository.save(board);
    }
}
