package com.chrisumeran.EMS.employee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long>, PagingAndSortingRepository<EmployeeEntity, Long> {
}
