package com.chrisumeran.EMS.employee;

import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImplementation(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeEntity createEmployee(EmployeeEntity employee) {
        return employeeRepository.save(employee);
    }
}
