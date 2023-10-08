package com.chrisumeran.EMS.department;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<DepartmentEntity> findAll() {
        return StreamSupport.stream(departmentRepository
                .findAll()
                .spliterator(),
                false)
                .collect(Collectors.toList());
    }
}
