package me.kingcjy.was.application.board;

import me.kingcjy.was.core.annotations.Autowired;
import me.kingcjy.was.core.annotations.web.RequestMapping;
import me.kingcjy.was.core.annotations.web.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @RequestMapping(value = "/")
    public String test(HttpServletRequest request) {
        return boardService.getBoard();
    }
}
