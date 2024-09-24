package com.example.boundary;

import lombok.Getter;

@Getter
public class OperationResponse {
    public double result;

    public OperationResponse(double result) {
        this.result = result;
    }
}
