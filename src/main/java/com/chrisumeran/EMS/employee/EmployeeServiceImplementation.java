package com.chrisumeran.EMS.employee;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImplementation(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeEntity save(EmployeeEntity employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeEntity> findAll() {
        return StreamSupport.stream(employeeRepository
                .findAll()
                .spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeeEntity> findOne(Long empID) {
        return employeeRepository.findById(empID);
    }

    @Override
    public boolean ifExists(Long empID) {
        return employeeRepository.existsById(empID);
    }
}
