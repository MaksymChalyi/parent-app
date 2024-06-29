package com.maksimkaxxl.springbootrestfulapi.services;

import com.maksimkaxxl.springbootrestfulapi.dtos.EmployeeDto;
import com.maksimkaxxl.springbootrestfulapi.dtos.responce.EmployeeSummaryDto;
import com.maksimkaxxl.springbootrestfulapi.dtos.responce.UploadedEmployeeResponse;
import com.maksimkaxxl.springbootrestfulapi.entities.Employee;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface EmployeeService {
    Employee createEmployee(EmployeeDto employeeDto);

    Employee findById(Long id);

    Employee updateEmployee(Long id, EmployeeDto employeeDto);

    void deleteEmployee(Long id);

    UploadedEmployeeResponse uploadEmployeesFromFile(MultipartFile file);

    Page<EmployeeSummaryDto> getEmployeesFromList(EmployeeSummaryDto filmNameAndGenreDto, Pageable pageable);

    Map<String, Object> getEmployeesByPage(EmployeeSummaryDto employeeSummaryDto, int page, int size);

    void generateEmployeeReport(HttpServletResponse response, EmployeeSummaryDto employeeSummaryDto);

    Page<Employee> findAllEmployees(int page, int size);
}
