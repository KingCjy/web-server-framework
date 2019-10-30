package me.kingcjy.was.core.context;

import me.kingcjy.was.core.di.AnnotationBeanDefinitionReader;
import me.kingcjy.was.core.di.BeanFactory;
import me.kingcjy.was.core.di.ClassPathBeanDefinitionScanner;
import me.kingcjy.was.core.di.DefaultBeanFactory;

public class AnnotationConfigApplicationContext implements ApplicationContext {

    private AnnotationBeanDefinitionReader reader;
    private ClassPathBeanDefinitionScanner scanner;
    private DefaultBeanFactory beanFactory;

    public AnnotationConfigApplicationContext() {
        this.beanFactory = new DefaultBeanFactory();
        reader = new AnnotationBeanDefinitionReader(beanFactory);
        scanner = new ClassPathBeanDefinitionScanner(beanFactory);
    }

    public AnnotationConfigApplicationContext(Class<?> baseClass) {
        this();

        String basePackage = baseClass.getPackage().getName();

        reader.register(basePackage);
        scanner.scan(basePackage);

        beanFactory.instantiateBeans();
    }

    @Override
    public long getStartupDate() {
        return 0;
    }

    @Override
    public String getApplicationName() {
        return null;
    }

    @Override
    public Object getBean(String name) {
        return this.beanFactory.getBean(name);
    }

    @Override
    public <T> T getBean(String name, Class<?> returnType) {
        return this.beanFactory.getBean(name, returnType);
    }

    @Override
    public Object[] getBeans() {
        return this.beanFactory.getBeans();
    }
}
