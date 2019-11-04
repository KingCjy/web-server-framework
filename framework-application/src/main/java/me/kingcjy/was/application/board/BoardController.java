package me.kingcjy.was.application.board;

import me.kingcjy.was.application.config.TestComponent;
import me.kingcjy.was.core.annotations.Autowired;
import me.kingcjy.was.core.annotations.web.*;
import me.kingcjy.was.core.http.HttpStatus;
import me.kingcjy.was.core.http.ResponseEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @RequestMapping("/boards")
    public ResponseEntity<List<BoardDto.BoardResponseDto>> findAllBoards() {
        List<BoardDto.BoardResponseDto> dtoList = boardService.findAllBoards();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/boards/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findBoardById(@PathVariable(name = "id") Long id) {
        BoardDto.BoardResponseDto dto = boardService.findBoardById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/boards", method = RequestMethod.POST)
    public ResponseEntity<?> addBoard(@RequestBody BoardDto.BoardRequestDto dto) {
        boardService.addBoard(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/boards/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateBoardById(@PathVariable(name = "id", required = true) Long id,
                                             @RequestBody BoardDto.BoardRequestDto dto) {
        boardService.updateBoard(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/boards/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBoardById(@PathVariable(name = "id") Long id) {
        boardService.deleteBoard(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}