package com.chrisumeran.EMS.employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    EmployeeEntity save(EmployeeEntity employee);

    List<EmployeeEntity> findAll();

    Optional<EmployeeEntity> findOne(Long empID);

    boolean ifExists(Long empID);
}
