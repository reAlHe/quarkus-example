package com.example.boundary;

import com.example.control.CalculatorService;
import com.example.control.DivisionByZeroException;
import com.example.entity.Task;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/calculate")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CalculatorResource {

    private final CalculatorService calculatorService;

    @Inject
    public CalculatorResource(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @POST
    public Response calculate(OperationRequest request) {
        try {
            double result = calculatorService.evaluateEquation(request.getNumber1(), request.getNumber2(), request.getOperation());
            return Response.ok(new OperationResponse(result)).build();
        }
        catch (DivisionByZeroException e) {
            throw new BadRequestException("Division by 0 is not allowed!");
        }
    }

    @GET
    public Response fetchTasks() {
        List<Task> result = calculatorService.fetchAllOperations();
        return Response.ok(ExecutedTaskResponse.fromTasks(result)).build();
    }
}