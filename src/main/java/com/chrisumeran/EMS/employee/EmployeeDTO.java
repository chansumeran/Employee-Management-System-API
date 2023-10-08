package com.chrisumeran.EMS.employee;

import com.chrisumeran.EMS.department.DepartmentEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {

    private Long empID;

    private String firstName;

    private String lastName;

    private String email;

    private String salary;

    private String hireDate;

    private DepartmentEntity department;

}
