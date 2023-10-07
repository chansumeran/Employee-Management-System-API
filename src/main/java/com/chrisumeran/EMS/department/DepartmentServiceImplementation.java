package com.chrisumeran.EMS.department;

import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImplementation implements DepartmentService{

    private DepartmentRepository departmentRepository;

    public DepartmentServiceImplementation(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentEntity createDepartment(DepartmentEntity department) {
        return departmentRepository.save(department);
    }
}
