package com.chrisumeran.EMS;

import com.chrisumeran.EMS.department.DepartmentEntity;

public class TestDataUtil {

    public TestDataUtil() {
    }

    public static DepartmentEntity testCreateDepartmentA() {
        return DepartmentEntity.builder()
                .deptID(1L)
                .name("School of Engineering, Architecture, and Information Technology Education")
                .build();
    }
}
