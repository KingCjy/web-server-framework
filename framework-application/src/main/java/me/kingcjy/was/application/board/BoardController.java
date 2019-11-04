package me.kingcjy.was.application.board;

import me.kingcjy.was.application.config.TestComponent;
import me.kingcjy.was.core.annotations.Autowired;
import me.kingcjy.was.core.annotations.web.PathVariable;
import me.kingcjy.was.core.annotations.web.RequestMapping;
import me.kingcjy.was.core.annotations.web.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private TestComponent testComponent;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @RequestMapping(value = "/")
    public String test(HttpServletRequest request) {
        return boardService.getBoard();
    }

    @RequestMapping(value = "/test")
    public String test2() {
        String data = testComponent.getValue();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Board board = new Board("테스트 제목");
        entityManager.persist(board);
        transaction.commit();

        return board.getTitle();
    }

    @RequestMapping(value = "/test/{id}")
    public String test3(@PathVariable(name = "id") Long id) {
        return "test3 success L " + id;
    }

}
