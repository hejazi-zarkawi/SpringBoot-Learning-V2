package com.example.cachingApp.services.impl;

import com.example.cachingApp.entities.Employee;
import com.example.cachingApp.entities.SalaryAccount;

public interface SalaryAccountService {

    void createAccount(Employee employee);

    SalaryAccount incrementBalance(Long accountId);
}
