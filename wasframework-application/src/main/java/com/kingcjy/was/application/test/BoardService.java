package com.kingcjy.was.application.test;

import com.kingcjy.was.core.annotations.Autowired;
import com.kingcjy.was.core.annotations.web.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public String test() {
        return "test";
    }
}
