package com.example.FenrisBookShopApp.config;

import com.example.FenrisBookShopApp.data.BookRepository;
import com.example.FenrisBookShopApp.data.TestEntity;
import com.example.FenrisBookShopApp.data.TestEntityCrudRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final TestEntityCrudRepository testEntityCrudRepository;
    private final BookRepository bookRepository;

    @Autowired
    public CommandLineRunnerImpl(TestEntityCrudRepository testEntityCrudRepository, BookRepository bookRepository) {
        this.testEntityCrudRepository = testEntityCrudRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 5; i++) {
            createTestEntity(new TestEntity());
        }

        TestEntity readTestEntity = readTestEntityById(3L);
//        TestEntity readTestEntity = testEntityDao.findOne(3L);
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

        Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName())
                .info(bookRepository.findBookByAuthor_FirstName("Maurits").toString());

        Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName())
                .info(bookRepository.customFindAllBooks().toString());
    }

    private void createTestEntity(@NotNull TestEntity testEntity) {
        testEntity.setData(testEntity.getClass().getSimpleName() + testEntity.hashCode());
        testEntityCrudRepository.save(testEntity);
    }

    private TestEntity readTestEntityById(Long id) {
        return testEntityCrudRepository.findById(id).orElse(null);
    }

    private @NotNull TestEntity updateTestEntity(Long id) {
        TestEntity testEntity = readTestEntityById(id);
        testEntity.setData("NEW DATA");
        testEntityCrudRepository.save(testEntity);
        return testEntity;
    }

    private void deleteTestEntityById(Long id) {
        TestEntity testEntity = readTestEntityById(id);
        testEntityCrudRepository.delete(testEntity);
    }
}
