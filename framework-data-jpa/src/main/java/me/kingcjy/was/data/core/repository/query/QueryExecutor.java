package me.kingcjy.was.data.core.repository.query;

import me.kingcjy.was.data.core.annotation.Query;

import javax.persistence.EntityManager;
import java.lang.reflect.Method;
import java.util.List;

public class QueryExecutor {

    private Method method;
    private String query;

    public QueryExecutor(Method method) {
        this.method = method;

        this.query = findQueryString();
    }

    private String findQueryString() {
        if(method.isAnnotationPresent(Query.class) == false) {
            throw new QueryNotFoundException(method.getName() + " query not found exception");
        }

        Query query = method.getAnnotation(Query.class);

        return query.value();
    }

    public Object execute(EntityManager entityManager, Object[] args) {
        if (List.class.equals(this.method.getReturnType())) {
            return entityManager.createQuery(query).getResultList();
        } else if(Void.TYPE.equals(this.method.getReturnType())) {
            entityManager.createQuery(query).executeUpdate();
            return null;
        } else {
            return entityManager.createQuery(query).getSingleResult();
        }
    }
}
