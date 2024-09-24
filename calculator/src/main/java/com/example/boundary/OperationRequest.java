package com.example.boundary;

import com.example.entity.Operation;
import lombok.Getter;

@Getter
public class OperationRequest {
    public int number1;
    public int number2;
    public Operation operation;
}