package com.chrisumeran.EMS;

import com.chrisumeran.EMS.department.DepartmentDTO;
import com.chrisumeran.EMS.department.DepartmentEntity;
import com.chrisumeran.EMS.employee.EmployeeDTO;
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

    public static DepartmentDTO testCreateDepartmentDtoA() {
        return DepartmentDTO.builder()
                .deptID(1L)
                .name("School of Engineering, Architecture, and Information Technology Education")
                .build();
    }

    public static DepartmentEntity testCreateDepartmentB() {
        return DepartmentEntity.builder()
                .deptID(2L)
                .name("School of Accountancy, Business, and Hospitality Management")
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

    public static EmployeeDTO testCreateEmployeeDtoA() {
        return EmployeeDTO.builder()
                .empID(1L)
                .firstName("Christian")
                .lastName("Sumeran")
                .email("chrisumerandeveloper@gmail.com")
                .salary("150,000")
                .hireDate("August 11, 2023")
                .build();
    }

    public static EmployeeEntity testCreateEmployeeB() {
        return EmployeeEntity.builder()
                .empID(2L)
                .firstName("Julie")
                .lastName("Cabildo")
                .email("julieanncabildo@gmail.com")
                .salary("85,000")
                .hireDate("July 18, 2023")
                .build();
    }
}
