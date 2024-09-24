package com.example.boundary;

import com.example.entity.Operation;
import com.example.entity.OperationRepository;
import com.example.entity.Task;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
class CalculatorResourceTest {

    @Inject
    private OperationRepository operationRepository;

    @AfterEach
    @Transactional
    void cleanup() {
        operationRepository.deleteAll();
    }

    @Test
    void evaluateAdditionSuccessfully() {
        String jsonBody = "{ \"number1\": 2, \"number2\": 3, \"operation\": \"ADD\" }";

        given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when().post("/calculate")
                .then()
                .statusCode(200)
                .body("result", is(5.0F));
    }

    @Test
    public void evaluateDivisionByZeroReturnsException() {
        String jsonBody = "{ \"number1\": 2, \"number2\": 0, \"operation\": \"DIV\" }";

        given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when().post("/calculate")
                .then()
                .statusCode(400)
                .body("message", is("Division by 0 is not allowed!"));
    }

    @Test
    void getAllOperationsReturnsAllEntries() {
        Task task1 = new Task(1, 2, Operation.ADD);
        Task task2 = new Task(4, 6, Operation.MUL);

        prepareTestData(List.of(task1, task2));

        given()
                .when().get("/calculate")
                .then()
                .statusCode(200)
                .body("previousTasks", hasSize(2))
                .body("previousTasks[0].number1", is(1))
                .body("previousTasks[0].number2", is(2))
                .body("previousTasks[0].operation", is("ADD"))
                .body("previousTasks[1].number1", is(4))
                .body("previousTasks[1].number2", is(6))
                .body("previousTasks[1].operation", is("MUL"));
    }

    @Transactional
    void prepareTestData(List<Task> tasks) {
        operationRepository.persist(tasks);
    }
}