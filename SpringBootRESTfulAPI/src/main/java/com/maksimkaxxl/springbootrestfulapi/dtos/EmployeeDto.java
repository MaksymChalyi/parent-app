package com.maksimkaxxl.springbootrestfulapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maksimkaxxl.springbootrestfulapi.annotations.AgeConstraint;
import com.maksimkaxxl.springbootrestfulapi.annotations.NotNumericPosition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record EmployeeDto(
        @JsonProperty("name")
        @NotBlank(message = "Name is required")
        String name,

        @JsonProperty("age")
        @NotNull(message = "Age is required")
        @AgeConstraint
        Integer age,

        @JsonProperty("position")
        @NotBlank(message = "Position is required")
        @NotNumericPosition
        String position,

        @JsonProperty("experienceYears")
        @NotNull(message = "Experience years are required")
        @Positive(message = "Experience years must be a positive number")
        Integer experienceYears,

        @JsonProperty("interests")
        List<String> interests,

        @JsonProperty("company")
        @NotNull(message = "Company is required")
        CompanyDto company) {
}
