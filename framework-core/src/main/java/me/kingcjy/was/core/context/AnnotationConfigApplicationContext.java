package me.kingcjy.was.core.context;

import me.kingcjy.was.core.beans.AnnotationBeanDefinitionReader;
import me.kingcjy.was.core.beans.BeanDefinitionScannerProviderFactory;
import me.kingcjy.was.core.beans.ClassPathBeanDefinitionScanner;
import me.kingcjy.was.core.beans.factory.DefaultBeanFactory;
import me.kingcjy.was.core.beans.provider.BeanDefinitionScannerProvider;

import java.util.Set;

public class AnnotationConfigApplicationContext implements ApplicationContext {

    private AnnotationBeanDefinitionReader reader;
    private Set<BeanDefinitionScannerProvider> scanners;
    private DefaultBeanFactory beanFactory;

    public AnnotationConfigApplicationContext() {
        this.beanFactory = new DefaultBeanFactory();
        reader = new AnnotationBeanDefinitionReader(beanFactory);

        scanners = new BeanDefinitionScannerProviderFactory().getBeanDefinitionScannerProviders(beanFactory);
        scanners.add(new ClassPathBeanDefinitionScanner(beanFactory));
    }

    public AnnotationConfigApplicationContext(Class<?> baseClass) {
        this();

        String basePackage = baseClass.getPackage().getName();

        reader.register(basePackage);
        scanners.forEach(scanner -> scanner.scan(basePackage));

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
