package com.chrisumeran.EMS.employee;

import java.util.List;

public interface EmployeeService {

    EmployeeEntity createEmployee(EmployeeEntity employee);

    List<EmployeeEntity> findAll();
}
