package com.kingcjy.was.application.test;

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
