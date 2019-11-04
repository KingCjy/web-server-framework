package me.kingcjy.was.data.core.jpa;

import javax.persistence.EntityManager;

public interface EntityManagerProxy extends EntityManager {
    EntityManager getTargetEntityManager();
}
