package com.maksimkaxxl.springbootrestfulapi.utils;

public class Constants {
    public static final class ErrorMessages {
        public static final String DUPLICATE_COMPANY_NAME = "Duplicate company name found: ";
        public static final String COMPANY_NOT_FOUND_ID = "Company with ID ";
        public static final String NOT_FOUND = " not found";
        public static final String COMPANY_ALREADY_EXISTS = "Company with this name already exists";
    }

    public static final class SuccessMessages {
        public static final String EMPLOYEE_ADDED = "New employee successfully added!";
    }

    public static final class FailureMessages {
        public static final String EMPLOYEE_NOT_ADDED = "New employee not added, we have a problem!";
    }
}
