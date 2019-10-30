package me.kingcjy.was.core.context;

import me.kingcjy.was.core.beans.factory.BeanFactory;

public interface ApplicationContext extends BeanFactory {
    long getStartupDate();
    String getApplicationName();
}
