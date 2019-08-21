package com.kingcjy.was.data.core.support;

import com.kingcjy.was.core.db.RepositorySupport;

import java.util.Collections;
import java.util.Map;

public class JpaRepositoryFactory extends RepositorySupport {
    @Override
    public Map<String, Object> initializeReposiroties(String basePackage) {

        return Collections.EMPTY_MAP;
    }
}
