package com.chrisumeran.EMS.department;

import java.util.List;

public interface DepartmentService {
    DepartmentEntity createDepartment(DepartmentEntity department);

    List<DepartmentEntity> findAll();
}
