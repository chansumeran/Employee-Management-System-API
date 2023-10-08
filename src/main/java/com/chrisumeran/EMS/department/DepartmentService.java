package com.chrisumeran.EMS.department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    DepartmentEntity createDepartment(DepartmentEntity department);

    List<DepartmentEntity> findAll();

    Optional<DepartmentEntity> findOne(Long deptID);
}
