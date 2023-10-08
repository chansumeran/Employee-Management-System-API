package com.chrisumeran.EMS.employee;

import com.chrisumeran.EMS.department.DepartmentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id_seq")
    private Long empID;

    private String firstName;

    private String lastName;

    private String email;

    private String salary;

    private String hireDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deptID")
    private DepartmentEntity department;
}
