package com.example.control;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DivisionByZeroException extends Exception {

    private final String message = "Division by 0 is not allowed";


}
