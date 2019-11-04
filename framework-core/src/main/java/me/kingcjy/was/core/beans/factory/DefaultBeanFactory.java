package me.kingcjy.was.core.beans.factory;

import me.kingcjy.was.core.annotations.Autowired;
import me.kingcjy.was.core.beans.definition.AnnotationBeanDefinition;
import me.kingcjy.was.core.beans.definition.BeanDefinition;
import me.kingcjy.was.core.beans.definition.BeanDefinitionRegistry;
import me.kingcjy.was.core.beans.definition.InstanceBeanDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private Set<BeanDefinition> beanDefinitions = new HashSet<>();
    private Map<String, Object> beans = new HashMap<>();

    public void instantiateBeans() {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            instantiateDefinition(beanDefinition);
        }
    }

    private void instantiateDefinition(BeanDefinition beanDefinition) {
        if(beanDefinition.isAnnotatedDefinition()) {
            initAnnotationBean(beanDefinition);
            return;
        }
        if(beanDefinition.isInstanceBeanDefinition()) {
            initInstanceBean(beanDefinition);
            return;
        }
        initDefaultBean(beanDefinition);
    }

    private void initAnnotationBean(BeanDefinition beanDefinition) {
        AnnotationBeanDefinition annotationBeanDefinition = (AnnotationBeanDefinition) beanDefinition;
        Method method = annotationBeanDefinition.getMethod();

        try {
            beans.put(method.getReturnType().getName(), createMethodInstance(method));
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Object createMethodInstance(Method method) throws InvocationTargetException, IllegalAccessException {
        if(method.getParameterCount() == 0) {
            return method.invoke(getBeanInstance(method.getDeclaringClass()));
        }
        return createParameterMethodInstance(method);
    }

    private Object createParameterMethodInstance(Method method) throws InvocationTargetException, IllegalAccessException {
        Class<?>[] parameterTypes = method.getParameterTypes();
        List<Object> params = new ArrayList<>();

        for (Class<?> parameterType : parameterTypes) {
            params.add(getBeanInstance(parameterType));
        }

        return method.invoke(params);
    }

    private Object getBeanInstance(Class<?> declaringClass) {
        if(beans.get(declaringClass.getName()) == null)  {
            instantiateBean(declaringClass);
        }
        return beans.get(declaringClass.getName());
    }

    private void instantiateBean(Class<?> targetClass) {
        BeanDefinition beanDefinition = getDefinitionByClass(targetClass);

        if(beans.get(beanDefinition.getBeanClass().getName()) != null) {
            return;
        }

        instantiateDefinition(beanDefinition);
    }

    private void initInstanceBean(BeanDefinition beanDefinition) {
        InstanceBeanDefinition instanceBeanDefinition = (InstanceBeanDefinition) beanDefinition;

        Class<?> instanceClass = instanceBeanDefinition.getBeanClass();
        Object instance = instanceBeanDefinition.getInstance();

        beans.put(instanceClass.getName(), instance);
    }

    private void initDefaultBean(BeanDefinition beanDefinition) {
        try {
            Object instance = beanDefinition.getBeanClass().getConstructor().newInstance();

            Field[] fields = beanDefinition.getBeanClass().getDeclaredFields();
            for (Field field : fields) {
                injectFields(instance, field);
            }

            beans.put(beanDefinition.getName(), instance);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void injectFields(Object instance, Field field) throws IllegalAccessException {
        if(field.isAnnotationPresent(Autowired.class)) {
            field.setAccessible(true);
            field.set(instance, getBeanInstance(field.getType()));
        }
    }

    private BeanDefinition getDefinitionByClass(Class<?> targetClass) {
        return beanDefinitions.stream()
                .filter(beanDefinition -> beanDefinition.getBeanClass().equals(targetClass))
                .findAny()
                .orElseThrow(() -> new RuntimeException("no bean named " + targetClass.getName() + " is defined"));
    }

    @Override
    public void registerDefinition(BeanDefinition beanDefinition) {
        if(beanDefinition.isInstanceBeanDefinition()
                || (beanDefinition.getBeanClass().isInterface() == false && Modifier.isAbstract(beanDefinition.getBeanClass().getModifiers()) == false)) {
            beanDefinitions.add(beanDefinition);
        }
    }

    @Override
    public Set<BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    @Override
    public Object getBean(String name) {
        return beans.get(name);
    }

    @Override
    public <T> T getBean(String name, Class<?> returnType) {
        return (T) beans.get(name);
    }

    @Override
    public Object[] getBeans() {
        return beans.values().toArray(new Object[] {});
    }
}
