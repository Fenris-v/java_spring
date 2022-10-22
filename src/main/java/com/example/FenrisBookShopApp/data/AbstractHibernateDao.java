package com.example.FenrisBookShopApp.data;

import lombok.Setter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;

@Repository
public abstract class AbstractHibernateDao<T> {
    @Autowired
    protected EntityManagerFactory entityManagerFactory;

    @Setter
    private Class<T> tClass;

    public T findOne(Long id) {
        return getSession().find(tClass, id);
    }

    public Session getSession() {
        return entityManagerFactory.createEntityManager().unwrap(Session.class);
    }
}
