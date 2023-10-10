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
    public boolean isExists(Long empID) {
        return employeeRepository.existsById(empID);
    }

    @Override
    public EmployeeEntity partialUpdate(Long empID, EmployeeEntity employeeEntity) {
        employeeEntity.setEmpID(empID);

        return employeeRepository.findById(empID).map(existingEmployee -> {
            Optional.ofNullable(employeeEntity.getFirstName()).ifPresent(existingEmployee::setFirstName);
            Optional.ofNullable(employeeEntity.getLastName()).ifPresent(existingEmployee::setLastName);
            Optional.ofNullable(employeeEntity.getEmail()).ifPresent(existingEmployee::setEmail);
            Optional.ofNullable(employeeEntity.getSalary()).ifPresent(existingEmployee::setSalary);
            return employeeRepository.save(existingEmployee);
        }).orElseThrow(() -> new RuntimeException("Employee does not exist."));
    }

    @Override
    public void delete(Long empID) {
        employeeRepository.deleteById(empID);
    }
}
