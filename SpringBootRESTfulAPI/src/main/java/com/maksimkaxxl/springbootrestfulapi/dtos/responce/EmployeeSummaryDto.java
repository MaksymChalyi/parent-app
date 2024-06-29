package com.maksimkaxxl.springbootrestfulapi.dtos.responce;

public record EmployeeSummaryDto(
        String name,
        Integer age,
        String position
) {
}
