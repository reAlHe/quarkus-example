package com.example.entity;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class OperationRepositoryTest {

    @Inject
    private OperationRepository underTest;

    @AfterEach
    @Transactional
    void cleanup() {
        underTest.deleteAll();
    }

    @Test
    @Transactional
    void findByOperationFindsAllQueriedOperations() {
        Task task1 = new Task(1,3,Operation.ADD);
        Task task2 = new Task(6,7,Operation.MUL);
        Task task3 = new Task(6,4,Operation.DIV);
        Task task4 = new Task(7,1,Operation.SUB);
        Task task5 = new Task(7,1,Operation.ADD);

        underTest.persist(task1, task2, task3, task4, task5);

        List<Task> result = underTest.findByOperation(Operation.ADD);

        assertThat(result.size(), is(equalTo(2)));
        assertThat(result, containsInAnyOrder(task1, task5));
    }

    @Test
    @Transactional
    void findByNumber1BiggerAndNumber2SmallerFindsAllMatchingEntries() {
        Task task1 = new Task(1,3,Operation.ADD);
        Task task2 = new Task(6,7,Operation.MUL);
        Task task3 = new Task(6,4,Operation.DIV);
        Task task4 = new Task(7,1,Operation.SUB);
        Task task5 = new Task(2,2,Operation.DIV);

        underTest.persist(task1, task2, task3, task4, task5);

        List<Task> result = underTest.findByNumber1BiggerAndNumber2Smaller(1, 3);

        assertThat(result.size(), is(equalTo(2)));
        assertThat(result, containsInAnyOrder(task4, task5));
    }
}