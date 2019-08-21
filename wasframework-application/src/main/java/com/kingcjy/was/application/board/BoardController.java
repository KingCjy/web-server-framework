package com.kingcjy.was.application.board;

import com.kingcjy.was.core.annotations.Autowired;
import com.kingcjy.was.core.annotations.web.RequestMapping;
import com.kingcjy.was.core.annotations.web.RestController;
import com.kingcjy.was.core.http.HttpStatus;
import com.kingcjy.was.core.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @RequestMapping("/board")
    public ResponseEntity<List<BoardDto.BoardResponseDto>> findAllBoards() {
        List<BoardDto.BoardResponseDto> dtoList = boardService.findAllBoards();

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
