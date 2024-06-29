package com.maksimkaxxl.springbootrestfulapi.services;

import com.maksimkaxxl.springbootrestfulapi.dtos.CompanyDto;
import com.maksimkaxxl.springbootrestfulapi.entities.Company;

import java.util.List;

public interface CompanyService {

    Company createCompany(CompanyDto companyDto);

    List<Company> getAllCompanies();

    void deleteCompanyById(Long id);

    Company updateCompany(Long id, CompanyDto companyDto);
}
