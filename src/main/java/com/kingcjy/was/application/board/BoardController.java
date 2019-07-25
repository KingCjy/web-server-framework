package com.kingcjy.was.application.board;

import com.kingcjy.was.core.annotations.Inject;
import com.kingcjy.was.core.annotations.web.Controller;
import com.kingcjy.was.core.annotations.web.RequestBody;
import com.kingcjy.was.core.annotations.web.RequestMapping;
import com.kingcjy.was.core.annotations.web.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/api/boards")
public class BoardController {

    @Inject
    private BoardService boardService;

    @RequestMapping("")
    public List<BoardDto.BoardListResponseDto> findBoards() {
        List<BoardDto.BoardListResponseDto> dtoList = boardService.findBoards();
        return dtoList;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addBoard(@RequestBody BoardDto.BoardRequestDto dto) {
        boardService.addBoard(dto);
    }

    @RequestMapping("/{id}")
    public Board findBoardById() {


        return null;
    }
}
