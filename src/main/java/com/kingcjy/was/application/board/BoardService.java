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

    public BoardDto.BoardListResponseDto findBoardById(Long id) {
        return new BoardDto.BoardListResponseDto(boardRepository.findById(id));
    }

    public void updateBoardById(Long id, BoardDto.BoardRequestDto dto) {
        Board board = boardRepository.findById(id);

        board.setTitle(dto.getTitle());
        board.setContents(dto.getContents());

        boardRepository.save(board);
    }

    public void deleteBoardById(Long id) {
        boardRepository.deleteById(id);
    }
}
