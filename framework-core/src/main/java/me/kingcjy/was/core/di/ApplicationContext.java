package me.kingcjy.was.core.di;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext implements BeanFactory {

    protected Map<String, Object> beans = new HashMap<>();

    @Override
    public Object getBean(String name) {
        return beans.get(name);
    }

    @Override
    public <T> T getBean(String name, Class<?> type) {
        return (T) beans.get(name);
    }

    @Override
    public Object[] getBeans() {
        return beans.values().toArray(new Object[]{});
    }
}
