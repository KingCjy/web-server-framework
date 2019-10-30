package me.kingcjy.was.application.board;

import me.kingcjy.was.application.config.TestComponent;
import me.kingcjy.was.core.annotations.Autowired;
import me.kingcjy.was.core.annotations.web.RequestMapping;
import me.kingcjy.was.core.annotations.web.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private TestComponent testComponent;

    @RequestMapping(value = "/")
    public String test(HttpServletRequest request) {
        return boardService.getBoard();
    }

    @RequestMapping(value = "/test")
    public String test2() {
        String data = testComponent.getValue();

        return data;
    }
}
