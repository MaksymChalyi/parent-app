package com.maksimkaxxl.springbootrestfulapi.repository;

import com.maksimkaxxl.springbootrestfulapi.dtos.responce.EmployeeSummaryDto;
import com.maksimkaxxl.springbootrestfulapi.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAll(Pageable pageable);

    Optional<Employee> findById(Long id);

    @Query(value = "SELECT new com.maksimkaxxl.springbootrestfulapi.dtos.responce.EmployeeSummaryDto(e.name, e.age, e.position) " +
            "FROM Employee e WHERE e.name LIKE %:name% OR e.position LIKE %:position% OR e.age = :age")
    Optional<Page<EmployeeSummaryDto>> findAllByNameOrPositionOrCompany(String name, Integer age, String position,
                                                                        Pageable pageable);

    @Query(value = "SELECT new com.maksimkaxxl.springbootrestfulapi.dtos.responce.EmployeeSummaryDto(e.name, e.age, e.position) FROM Employee e")
    Optional<Page<EmployeeSummaryDto>> findAllEmployeeNameOrPositionOrCompany(Pageable pageable);


}
