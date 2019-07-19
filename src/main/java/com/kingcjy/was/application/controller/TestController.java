package com.kingcjy.was.application.controller;

import com.kingcjy.was.application.service.TestService;
import com.kingcjy.was.core.annotations.Inject;
import com.kingcjy.was.core.annotations.web.Controller;
import com.kingcjy.was.core.annotations.web.RequestMapping;

@Controller
@RequestMapping("/api")
public class TestController {

    @Inject
    private TestService testService;

    @RequestMapping("/test")
    public String test() {
        return testService.hello();
    }
}
