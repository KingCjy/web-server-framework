package me.kingcjy.was.core.context;

import me.kingcjy.was.core.di.BeanFactory;

public interface ApplicationContext extends BeanFactory {
    long getStartupDate();
    String getApplicationName();
}
