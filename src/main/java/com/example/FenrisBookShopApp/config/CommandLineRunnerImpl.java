package com.example.FenrisBookShopApp.config;

import com.example.FenrisBookShopApp.data.TestEntity;
import com.example.FenrisBookShopApp.data.TestEntityDao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateJdbcException;

import javax.persistence.EntityManagerFactory;
import java.util.logging.Logger;

@Configuration
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final EntityManagerFactory entityManagerFactory;
    private final TestEntityDao testEntityDao;

    @Autowired
    public CommandLineRunnerImpl(EntityManagerFactory entityManagerFactory, TestEntityDao testEntityDao) {
        this.entityManagerFactory = entityManagerFactory;
        this.testEntityDao = testEntityDao;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 5; i++) {
            createTestEntity(new TestEntity());
        }

//        TestEntity readTestEntity = readTestEntityById(3L);
        TestEntity readTestEntity = testEntityDao.findOne(3L);
        if (readTestEntity != null) {
            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("read " + readTestEntity);
        } else {
            throw new NullPointerException();
        }

        TestEntity updatedTestEntity = updateTestEntity(5L);
        if (readTestEntity != null) {
            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("update " + updatedTestEntity);
        } else {
            throw new NullPointerException();
        }

        deleteTestEntityById(4L);
    }

    private void createTestEntity(@NotNull TestEntity testEntity) {
        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            testEntity.setData(testEntity.getClass().getSimpleName() + testEntity.hashCode());
            session.save(testEntity);
            transaction.commit();
        } catch (HibernateJdbcException e) {
            if (transaction != null) {
                transaction.rollback();
            } else {
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
    }

    private TestEntity readTestEntityById(Long id) {
        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction transaction = null;
        TestEntity result = null;

        try {
            transaction = session.beginTransaction();
            result = session.find(TestEntity.class, id);
            transaction.commit();
        } catch (HibernateJdbcException e) {
            if (transaction != null) {
                transaction.rollback();
            } else {
                e.printStackTrace();
            }
        } finally {
            session.close();
        }

        return result;
    }

    private TestEntity updateTestEntity(Long id) {
        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction transaction = null;
        TestEntity result = null;

        try {
            transaction = session.beginTransaction();
            TestEntity testEntity = readTestEntityById(id);
            testEntity.setData("NEW DATA UPDATE");
            result = (TestEntity) session.merge(testEntity);
            transaction.commit();
        } catch (HibernateJdbcException e) {
            if (transaction != null) {
                transaction.rollback();
            } else {
                e.printStackTrace();
            }
        } finally {
            session.close();
        }

        return result;
    }

    private void deleteTestEntityById(Long id) {
        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            TestEntity testEntity = readTestEntityById(id);
            testEntity = (TestEntity) session.merge(testEntity);
            session.delete(testEntity);
            transaction.commit();
        } catch (HibernateJdbcException e) {
            if (transaction != null) {
                transaction.rollback();
            } else {
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
    }
}
