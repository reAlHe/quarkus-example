package com.example.control;

import com.example.entity.Operation;
import com.example.entity.OperationRepository;
import com.example.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceTest {

    private CalculatorService underTest;

    @Mock
    private OperationRepository operationRepositoryMock;

    @BeforeEach
    void init(){
        underTest = new CalculatorService(operationRepositoryMock);
    }

    @ParameterizedTest(name = "{index} => a={0}, b={1}, operation={2}, result={3}")
    @MethodSource("provideParameters")
    void evaluateEquationSuccessfullyAndStoreOperation(int num1, int num2, Operation operation, double expected) throws DivisionByZeroException {
        var result = underTest.evaluateEquation(num1, num2, operation);

        assertEquals(expected, result);
        verify(operationRepositoryMock, times(1)).persist(new Task(num1, num2, operation));
    }

    @Test
    void evaluateEquationThrowsDivisionByZeroException() {
        assertThrowsExactly(DivisionByZeroException.class, () -> underTest.evaluateEquation(5, 0, Operation.DIV));
    }

    @Test
    void fetchAllOperationsReturnsCompleteList() {
        Task task1 = new Task(1, 2, Operation.ADD);
        Task task2 = new Task(4, 6, Operation.MUL);
        when(operationRepositoryMock.findAllTasks()).thenReturn(List.of(task1, task2));

        List<Task> result = underTest.fetchAllOperations();

        assertEquals(result.size(), 2);
        assertTrue(result.contains(task1));
        assertTrue(result.contains(task2));
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(1, 1, Operation.ADD, 2),
                Arguments.of(5, 3, Operation.SUB, 2),
                Arguments.of(2, 3, Operation.MUL, 6),
                Arguments.of(8, 2, Operation.DIV, 4)
        );
    }
}