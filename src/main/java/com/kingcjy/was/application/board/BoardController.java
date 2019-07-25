package com.kingcjy.was.application.board;

import com.kingcjy.was.core.annotations.Inject;
import com.kingcjy.was.core.annotations.web.Controller;
import com.kingcjy.was.core.annotations.web.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/board")
public class BoardController {

    @Inject
    private BoardService boardService;

    @RequestMapping("")
    public List<BoardDto.BoardListResponseDto> findBoards() {
        List<BoardDto.BoardListResponseDto> dtoList = boardService.findBoards();
        return dtoList;
    }
}
