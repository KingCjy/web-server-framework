package com.kingcjy.was.application;

import com.kingcjy.was.application.board.Board;
import com.kingcjy.was.core.WasApplication;
import com.kingcjy.was.core.db.jpa.JpaEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class WebApplication {

    public static void main(String[] args) throws Exception {
        WasApplication.run(WebApplication.class, args);

    }


}

