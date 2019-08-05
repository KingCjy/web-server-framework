package com.kingcjy.was.application.board;

import com.kingcjy.was.core.annotations.Inject;
import com.kingcjy.was.core.annotations.web.*;

import java.util.List;

@Controller
@RequestMapping("/api/boards")
public class BoardController {

    @Inject
    private BoardService boardService;

    @RequestMapping("")
    public List<BoardDto.BoardResponseDto> findBoards() {
        List<BoardDto.BoardResponseDto> dtoList = boardService.findBoards();
        return dtoList;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addBoard(@RequestBody BoardDto.BoardRequestDto dto) {
        boardService.addBoard(dto);
    }

    @RequestMapping("/{id}")
    public BoardDto.BoardResponseDto findBoardById(@PathVariable(name = "id") Long id) {
        return boardService.findBoardById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public void updateBoardById(@PathVariable(name = "id") Long id,
                                @RequestBody BoardDto.BoardRequestDto dto) {
        boardService.updateBoardById(id, dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteBoardById(@PathVariable(name = "id") Long id) {
        boardService.deleteBoardById(id);
    }
}
