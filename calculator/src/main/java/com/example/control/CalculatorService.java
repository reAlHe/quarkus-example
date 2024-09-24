package com.example.control;

import com.example.entity.Operation;
import com.example.entity.OperationRepository;
import com.example.entity.Task;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class CalculatorService {

    private final OperationRepository operationRepository;

    @Inject
    public CalculatorService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public Double evaluateEquation(int num1, int num2, Operation operation) throws DivisionByZeroException {
        operationRepository.persist(new Task(num1, num2, operation));
        return switch (operation) {
            case Operation.ADD -> (double)sum(num1, num2);
            case Operation.SUB -> (double)sub(num1, num2);
            case Operation.MUL -> (double)mul(num1, num2);
            case Operation.DIV -> div(num1, num2);
        };
    }

    public List<Task> fetchAllOperations() {
        return operationRepository.findAllTasks();
    }

    private int sum(int num1, int num2) {
        return num1 + num2;
    }

    private int sub(int num1, int num2) {
        return num1 - num2;
    }

    private int mul(int num1, int num2) {
        return num1 * num2;
    }

    private double div(int num1, int num2) throws DivisionByZeroException {
        if (num2 == 0) {
            throw new DivisionByZeroException();
        }
        return ((double)num1) / num2;
    }

}
