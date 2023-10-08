package com.chrisumeran.EMS;

import com.chrisumeran.EMS.department.DepartmentEntity;
import com.chrisumeran.EMS.employee.EmployeeEntity;

public class TestDataUtil {

    public TestDataUtil() {
    }

    public static DepartmentEntity testCreateDepartmentA() {
        return DepartmentEntity.builder()
                .deptID(1L)
                .name("School of Engineering, Architecture, and Information Technology Education")
                .build();
    }

    public static EmployeeEntity testCreateEmployeeA() {
        return EmployeeEntity.builder()
                .empID(1L)
                .firstName("Christian")
                .lastName("Sumeran")
                .email("chrisumerandeveloper@gmail.com")
                .salary("150,000")
                .hireDate("August 11, 2023")
                .build();
    }
}
