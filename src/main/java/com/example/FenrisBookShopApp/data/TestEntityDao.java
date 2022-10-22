package com.example.FenrisBookShopApp.data;

import org.springframework.stereotype.Repository;

@Repository
public class TestEntityDao extends AbstractHibernateDao<TestEntity> {
    public TestEntityDao() {
        super();
        setTClass(TestEntity.class);
    }
}
