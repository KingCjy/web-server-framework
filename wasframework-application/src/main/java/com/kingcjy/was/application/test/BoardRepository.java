package com.kingcjy.was.application.test;

import com.kingcjy.was.data.core.support.annotation.Query;
import com.kingcjy.was.data.core.support.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM board ORDER BY b.id DESC")
    List<Board> findAllBoardsOrderByIdDesc();
}
