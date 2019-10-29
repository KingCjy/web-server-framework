package me.kingcjy.was.core.context;

import me.kingcjy.was.core.di.BeanFactory;
import me.kingcjy.was.core.di.DefaultBeanFactory;

public class AnnotationConfigApplicationContext extends DefaultBeanFactory implements ApplicationContext {

    private BeanFactory beanFactory;

    public AnnotationConfigApplicationContext() {
        this.beanFactory = new DefaultBeanFactory();
    }

    @Override
    public long getStartupDate() {
        return 0;
    }

    @Override
    public String getApplicationName() {
        return null;
    }
}
