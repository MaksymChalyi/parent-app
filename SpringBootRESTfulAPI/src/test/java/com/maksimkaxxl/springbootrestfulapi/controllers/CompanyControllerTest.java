package com.maksimkaxxl.springbootrestfulapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maksimkaxxl.springbootrestfulapi.dtos.CompanyDto;
import com.maksimkaxxl.springbootrestfulapi.entities.Company;
import com.maksimkaxxl.springbootrestfulapi.services.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CompanyController.class)
@AutoConfigureMockMvc
class CompanyControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(new CompanyController(companyService)).build();
    }

    @Test
    void createCompany_ValidCompany_Returns201() throws Exception {

        CompanyDto companyDto = new CompanyDto("Test Company", "IT");
        Company createdCompany = new Company();
        createdCompany.setId(1L);
        createdCompany.setName("Test Company");
        createdCompany.setIndustry("IT");
        when(companyService.createCompany(any(CompanyDto.class))).thenReturn(createdCompany);

        ResultActions result = mockMvc.perform(post("/api/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyDto)));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Company"))
                .andExpect(jsonPath("$.industry").value("IT"));
    }

    @Test
    void getAllCompanies_ReturnsListOfCompanies() throws Exception {
        Company company1 = new Company(1L, "Company 1", "IT");
        Company company2 = new Company(2L, "Company 2", "Finance");
        List<Company> companies = Arrays.asList(company1, company2);
        when(companyService.getAllCompanies()).thenReturn(companies);

        ResultActions result = mockMvc.perform(get("/api/company"));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Company 1"))
                .andExpect(jsonPath("$[0].industry").value("IT"))
                .andExpect(jsonPath("$[1].name").value("Company 2"))
                .andExpect(jsonPath("$[1].industry").value("Finance"));
    }

    @Test
    void deleteCompanyById_ExistingId_Returns204() throws Exception {
        Long companyId = 1L;
        Mockito.doNothing().when(companyService).deleteCompanyById(companyId);

        ResultActions result = mockMvc.perform(delete("/api/company/{id}", companyId));

        result.andExpect(status().isNoContent());
    }

    @Test
    void updateCompany_ExistingIdAndValidCompany_Returns200() throws Exception {
        Long companyId = 1L;
        CompanyDto companyDto = new CompanyDto("Updated Company", "Updated Industry");
        Company updatedCompany = new Company();
        updatedCompany.setId(companyId);
        updatedCompany.setName("Updated Company");
        updatedCompany.setIndustry("Updated Industry");
        when(companyService.updateCompany(companyId, companyDto)).thenReturn(updatedCompany);

        ResultActions result = mockMvc.perform(put("/api/company/{id}", companyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyDto)));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Company"))
                .andExpect(jsonPath("$.industry").value("Updated Industry"));
    }

}