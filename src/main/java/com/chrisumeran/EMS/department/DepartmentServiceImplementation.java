package com.chrisumeran.EMS.department;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DepartmentServiceImplementation implements DepartmentService{

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImplementation(final DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentEntity save(DepartmentEntity department) {
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

    @Override
    public Optional<DepartmentEntity> findOne(Long deptID) {
        return departmentRepository.findById(deptID);
    }

    @Override
    public boolean ifExists(Long deptID) {
        return departmentRepository.existsById(deptID);
    }

    @Override
    public DepartmentEntity partialUpdate(Long deptID, DepartmentEntity departmentEntity) {
        departmentEntity.setDeptID(deptID);

        return departmentRepository.findById(deptID).map(existingDepartment -> {
            Optional.ofNullable(departmentEntity.getName()).ifPresent(existingDepartment::setName);
            return departmentRepository.save(existingDepartment);
        }).orElseThrow(() -> new RuntimeException("Department does not exist"));
    }
}
