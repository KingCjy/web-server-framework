package me.kingcjy.was.data.core;

import me.kingcjy.was.core.beans.definition.BeanDefinitionRegistry;
import me.kingcjy.was.core.beans.definition.InstanceBeanDefinition;
import me.kingcjy.was.core.beans.provider.BeanDefinitionScannerProvider;
import me.kingcjy.was.core.utils.MyReflectionUtils;
import me.kingcjy.was.data.core.config.JpaEntityManagerFactory;
import me.kingcjy.was.data.core.repository.DynamicRepositoryInvocationHandler;
import me.kingcjy.was.data.core.repository.JpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Proxy;
import java.util.Set;
import java.util.stream.Collectors;

public class RepositoryComponentProvider implements BeanDefinitionScannerProvider {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryComponentProvider.class);

    private BeanDefinitionRegistry registry;

    @Override
    public void setBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.registry = beanDefinitionRegistry;
    }

    @Override
    public void scan(String basePackage) {
        Set<Class> entities = findEntities(basePackage);
        Set<Class> repositories = findRepositoryInterfaces(basePackage);

        JpaEntityManagerFactory jpaEntityManagerFactory = new JpaEntityManagerFactory(entities.toArray(new Class[]{}));
        EntityManagerFactory entityManagerFactory = jpaEntityManagerFactory.getEntityManagerFactory();
        EntityManager entityManager = new LocalEntityManagerProxy(entityManagerFactory);

        registry.registerDefinition(new InstanceBeanDefinition(EntityManagerFactory.class, jpaEntityManagerFactory.getEntityManagerFactory()));
        registry.registerDefinition(new InstanceBeanDefinition(EntityManager.class, entityManager));

        initializeRepositories(repositories, entityManager);
    }

    private void initializeRepositories(Set<Class> repositories, EntityManager entityManager) {
        repositories.forEach(repository -> {
            Object instance = Proxy.newProxyInstance(
                    getClass().getClassLoader(),
                    new Class[] { repository },
                    new DynamicRepositoryInvocationHandler(repository, entityManager));
            registry.registerDefinition(new InstanceBeanDefinition(repository, instance));
        });
    }

    private Set<Class> findEntities(String basePackage) {
        Set<Class> entities = MyReflectionUtils.findAnnotatedClasses(basePackage, Entity.class);
        return entities;
    }

    private Set<Class> findRepositoryInterfaces(String basePackage) {
        Set<Class> repositories = MyReflectionUtils.findAssignableClass(basePackage, JpaRepository.class);
        return repositories.stream().filter(repository -> repository.isInterface()).collect(Collectors.toSet());
    }
}
