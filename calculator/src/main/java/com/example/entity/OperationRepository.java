package com.example.entity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;


@ApplicationScoped
public class OperationRepository implements PanacheRepository<Task> {

    public List<Task> findByOperation(Operation operation) {
        return list("operation", operation);
    }

    public List<Task> findByNumber1BiggerAndNumber2Smaller(int number1, int number2) {
        return list("number1 > ?1 and number2 < ?2", number1, number2);
    }

    public List<Task> findAllTasks() {
        return findAll().stream().toList();
    }
}
