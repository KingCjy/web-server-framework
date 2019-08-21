package com.kingcjy.was.application.board;

import com.kingcjy.was.core.annotations.Autowired;
import com.kingcjy.was.core.annotations.web.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public List<BoardDto.BoardResponseDto> findAllBoards() {
        return boardRepository.findAll().stream().map(BoardDto.BoardResponseDto::new).collect(Collectors.toList());
    }

    public BoardDto.BoardResponseDto findBoardById(Long id) {
        Board board = boardRepository.findById(id);

        return new BoardDto.BoardResponseDto(board);
    }

    public void addBoard(BoardDto.BoardRequestDto dto) {
        Board board = Board.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .build();

        boardRepository.save(board);
    }

    public void updateBoard(Long id, BoardDto.BoardRequestDto dto) {
        Board board = boardRepository.findById(id);

        board.setTitle(dto.getTitle());
        board.setContents(dto.getContents());

        boardRepository.save(board);
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
