package com.kingcjy.was.application.test;

import com.kingcjy.was.core.annotations.Inject;
import com.kingcjy.was.core.annotations.web.RequestMapping;
import com.kingcjy.was.core.annotations.web.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @Inject
    private TestService testService;

    @RequestMapping("/test")
    public String test() {
        return testService.test();
    }
}
