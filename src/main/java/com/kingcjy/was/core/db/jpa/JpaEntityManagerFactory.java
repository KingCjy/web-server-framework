package com.kingcjy.was.core.db.jpa;

import com.kingcjy.was.core.db.jpa.config.HibernatePersistenceUnitInfo;
import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

public class JpaEntityManagerFactory {

    private final String DB_URL = "jdbc:h2:mem:testdb";
    private final String DB_USER_NAME = "sa";
    private final String DB_PASSWORD = "";
    private final Class[] entityClasses;

    public JpaEntityManagerFactory(Class[] entityClasses) {
        this.entityClasses = entityClasses;
    }

    public EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    protected EntityManagerFactory getEntityManagerFactory() {
        PersistenceUnitInfo persistenceUnitInfo = getPersistenceUnitInfo(getClass().getSimpleName());
        Map<String, Object> configuration = new HashMap<>();
        return new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(persistenceUnitInfo), configuration)
                .build();
    }

    protected HibernatePersistenceUnitInfo getPersistenceUnitInfo(String name) {
        return new HibernatePersistenceUnitInfo(name, getEntityClassNames(), getProperties());
    }

    protected List<String> getEntityClassNames() {
        return Arrays.asList(getEntities())
                .stream()
                .map(Class::getName)
                .collect(Collectors.toList());
    }

    protected Properties getProperties() {
        Properties properties = new Properties();

        properties.put("javax.persistence.jdbc.driver", "org.h2.Driver");
        properties.put("javax.persistence.jdbc.user", "sa");
        properties.put("javax.persistence.jdbc.password", "");
        properties.put("javax.persistence.jdbc.url", "jdbc:h2:mem:testdb");


        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.id.new_generator_mappings", true);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.use_sql_comments", true);

        properties.put("hibernate.hbm2ddl.auto", "create");
        return properties;
    }

    protected Class[] getEntities() {
        return entityClasses;
    }

    protected DataSource getMysqlDataSource() {
        JdbcDataSource mysqlDataSource = new JdbcDataSource();
        mysqlDataSource.setURL(DB_URL);
        mysqlDataSource.setUser(DB_USER_NAME);
        mysqlDataSource.setPassword(DB_PASSWORD);
        return mysqlDataSource;
    }
}