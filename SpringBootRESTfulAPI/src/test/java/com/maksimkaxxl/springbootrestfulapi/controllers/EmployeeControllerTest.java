package com.maksimkaxxl.springbootrestfulapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maksimkaxxl.springbootrestfulapi.dtos.CompanyDto;
import com.maksimkaxxl.springbootrestfulapi.dtos.EmployeeDto;
import com.maksimkaxxl.springbootrestfulapi.dtos.responce.EmployeeSummaryDto;
import com.maksimkaxxl.springbootrestfulapi.dtos.responce.UploadedEmployeeResponse;
import com.maksimkaxxl.springbootrestfulapi.entities.Company;
import com.maksimkaxxl.springbootrestfulapi.entities.Employee;
import com.maksimkaxxl.springbootrestfulapi.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(new EmployeeController(employeeService)).build();
    }


    @Test
    void createEmployee_ValidEmployee_ReturnsCreatedStatus() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto(
                "John Doe",
                30,
                "Developer",
                5,
                List.of("Programming", "Gaming"),
                new CompanyDto("ABC Inc.", "Technology")
        );
        Employee createdEmployee = new Employee(
                1L,
                "John Doe",
                30,
                "Developer",
                5,
                List.of("Programming", "Gaming"),
                new Company(1L, "ABC Inc.", "Technology")
        );
        when(employeeService.createEmployee(any(EmployeeDto.class))).thenReturn(createdEmployee);

        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void createEmployee_InvalidEmployee_ReturnsBadRequest() throws Exception {
        EmployeeDto invalidEmployeeDto = new EmployeeDto(
                "",
                25,
                "Invalid Position",
                0,
                List.of(),
                new CompanyDto("ABC Inc.", "Technology")
        );

        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmployeeDto)))
                .andExpect(status().isBadRequest());

    }


    @Test
    void findEmployeeById_ExistingId_ReturnsEmployee() throws Exception {
        Long id = 1L;
        Employee foundEmployee = new Employee(
                1L,
                "John Doe",
                30,
                "Developer",
                5,
                List.of("Programming", "Gaming"),
                new Company(1L, "ABC Inc.", "Technology")
        );
        when(employeeService.findById(id)).thenReturn(foundEmployee);

        mockMvc.perform(get("/api/employee/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(foundEmployee.getId()))
                .andExpect(jsonPath("$.name").value(foundEmployee.getName()))
                .andExpect(jsonPath("$.age").value(foundEmployee.getAge()))
                .andExpect(jsonPath("$.position").value(foundEmployee.getPosition()))
                .andExpect(jsonPath("$.experienceYears").value(foundEmployee.getExperienceYears()));
    }


    @Test
    void updateEmployee_ValidEmployee_ReturnsUpdatedEmployee() throws Exception {
        Long id = 1L;
        EmployeeDto updatedEmployeeDto = new EmployeeDto(
                "Updated Name",
                35,
                "Senior Developer",
                8,
                List.of("Programming", "Gaming", "Reading"),
                new CompanyDto("XYZ Corp.", "Technology")
        );
        Employee updatedEmployee = new Employee(
                id,
                "Updated Name",
                35,
                "Senior Developer",
                8,
                List.of("Programming", "Gaming", "Reading"),
                new Company(2L, "XYZ Corp.", "Technology")
        );
        when(employeeService.updateEmployee(id, updatedEmployeeDto)).thenReturn(updatedEmployee);

        mockMvc.perform(put("/api/employee/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployeeDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEmployeeById_ExistingId_ReturnsNoContent() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/employee/{id}", id))
                .andExpect(status().isNoContent());
    }


    @Test
    void generateEmployeeReport_ValidRequest_ReturnsOk() throws Exception {
        EmployeeSummaryDto summaryDto = new EmployeeSummaryDto("Olena", 22, "CEO");

        mockMvc.perform(post("/api/employee/_report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(summaryDto)))
                .andExpect(status().isOk());
    }

    @Test
    void updateEmployee_ValidEmployee_ReturnsOk() throws Exception {
        Long id = 1L;
        EmployeeDto employeeDto = new EmployeeDto(
                "John Doe",
                30,
                "Developer",
                5,
                List.of("Programming", "Gaming"),
                new CompanyDto("ABC Inc.", "Technology")
        );
        Employee updatedEmployee = new Employee(
                id,
                "John Doe",
                30,
                "Developer",
                5,
                List.of("Programming", "Gaming"),
                new Company(1L, "ABC Inc.", "Technology")
        );
        when(employeeService.updateEmployee(eq(id), any(EmployeeDto.class))).thenReturn(updatedEmployee);

        mockMvc.perform(put("/api/employee/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isOk());
    }

    @Test
    void uploadEmployees_ValidFile_ReturnsCreated() throws Exception {
        Resource resource = new ClassPathResource("employees.json");
        InputStream inputStream = resource.getInputStream();
        MockMultipartFile file = new MockMultipartFile("file", "employees.json", MediaType.APPLICATION_JSON_VALUE, inputStream);
        var response = new UploadedEmployeeResponse(1, 0, Collections.emptyList());
        when(employeeService.uploadEmployeesFromFile(file)).thenReturn(response);

        mockMvc.perform(multipart("/api/employee/upload")
                        .file(file))
                .andExpect(status().isCreated());
    }

    @Test
    void getEmployeesByPage_ValidData_ReturnsOk() throws Exception {

        EmployeeSummaryDto employeeSummaryDto = new EmployeeSummaryDto("John Doe", 30, "Developer");
        Map<String, Object> response = new HashMap<>();
        response.put("list", Collections.emptyList());
        response.put("totalPages", 1);
        when(employeeService.getEmployeesByPage(eq(employeeSummaryDto), anyInt(), anyInt())).thenReturn(response);

        mockMvc.perform(post("/api/employee/_list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeSummaryDto)))
                .andExpect(status().isOk());
    }

    @Test
    void generateEmployeeReport_ValidData_ReturnsOk() throws Exception {

        EmployeeSummaryDto employeeSummaryDto = new EmployeeSummaryDto("John Doe", 30, "Developer");

        mockMvc.perform(post("/api/employee/_report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeSummaryDto)))
                .andExpect(status().isOk());
    }

}
