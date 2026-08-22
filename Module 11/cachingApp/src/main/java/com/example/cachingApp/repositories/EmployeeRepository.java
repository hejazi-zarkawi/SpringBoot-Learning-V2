package com.example.cachingApp.repositories;


import com.example.cachingApp.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
        List<Employee> findByEmail(String email);
    }

