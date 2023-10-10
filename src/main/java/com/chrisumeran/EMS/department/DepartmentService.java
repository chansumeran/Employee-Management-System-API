package com.chrisumeran.EMS.department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    DepartmentEntity save(DepartmentEntity department);

    List<DepartmentEntity> findAll();

    Optional<DepartmentEntity> findOne(Long deptID);

    boolean ifExists(Long deptID);

    DepartmentEntity partialUpdate(Long deptID, DepartmentEntity departmentEntity);
}
