package com.example.boundary;

import com.example.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ExecutedTaskResponse {

    public List<TaskResponse> previousTasks;

    public static ExecutedTaskResponse fromTasks(List<Task> tasks) {
        List<TaskResponse> taskResponses = tasks.stream().map(TaskResponse::fromTask).toList();
        return new ExecutedTaskResponse(taskResponses);
    }
}
