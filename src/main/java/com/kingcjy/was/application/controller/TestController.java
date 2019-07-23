package com.kingcjy.was.application.controller;

import com.kingcjy.was.application.dto.TestDto;
import com.kingcjy.was.application.service.TestService;
import com.kingcjy.was.core.annotations.Inject;
import com.kingcjy.was.core.annotations.web.Controller;
import com.kingcjy.was.core.annotations.web.RequestBody;
import com.kingcjy.was.core.annotations.web.RequestMapping;
import com.kingcjy.was.core.annotations.web.RequestMethod;

import javax.servlet.http.HttpServletRequest;

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
    public TestDto testPost(HttpServletRequest request, @RequestBody TestDto dto) {

        return dto;
    }
}
