package com.kingcjy.was.application.service;

import com.kingcjy.was.application.dto.TestDto;
import com.kingcjy.was.core.annotations.web.Service;

@Service
public class TestService {

    public TestDto hello() {
        TestDto dto = new TestDto();
        dto.setId(1L);
        dto.setName("가나다");
        dto.setAge(18);
        return dto;
    }
}
