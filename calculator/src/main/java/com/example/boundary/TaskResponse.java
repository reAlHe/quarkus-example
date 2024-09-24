package com.example.boundary;

import com.example.entity.Operation;
import com.example.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskResponse {

    private int number1;

    private int number2;

    private Operation operation;

    public static TaskResponse fromTask(Task task) {
        return new TaskResponse(task.getNumber1(), task.getNumber2(), task.getOperation());
    }

}
