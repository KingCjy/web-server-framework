package me.kingcjy.was.application.board;

import me.kingcjy.was.data.core.annotation.Query;
import me.kingcjy.was.data.core.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board AS b ORDER BY b.id DESC")
    List<Board> findAllBoardsOrderByIdDesc();
}