package com.kingcjy.was.application.test;

import com.kingcjy.was.core.annotations.Inject;
import com.kingcjy.was.core.annotations.web.Controller;
import com.kingcjy.was.core.annotations.web.RequestBody;
import com.kingcjy.was.core.annotations.web.RequestMapping;
import com.kingcjy.was.core.annotations.web.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api")
public class TestController {

    @Inject
    private TestService testService;

    @RequestMapping("/test")
    public TestDto test() {
        return testService.hello();
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public TestDto testPost(HttpServletRequest request, HttpServletResponse response, @RequestBody TestDto dto) {

        return dto;
    }
}