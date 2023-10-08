package com.chrisumeran.EMS.department;

import com.chrisumeran.EMS.mapper.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DepartmentController {

    private DepartmentService departmentService;

    private Mapper<DepartmentEntity, DepartmentDTO> departmentMapper;

    public DepartmentController(Mapper<DepartmentEntity, DepartmentDTO> departmentMapper, DepartmentService departmentService) {
        this.departmentMapper = departmentMapper;
        this.departmentService = departmentService;
    }

    // create endpoint
    @PostMapping(path = "/departments")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO department) {
        DepartmentEntity departmentEntity = departmentMapper.mapFrom(department);
        DepartmentEntity savedDepartmentEntity = departmentService.createDepartment(departmentEntity);
        return new ResponseEntity<>(departmentMapper.mapTo(savedDepartmentEntity), HttpStatus.CREATED);
    }

    // read many endpoint
    @GetMapping("/departments")
    public List<DepartmentDTO> findAllDepartments() {
        List<DepartmentEntity> departments =  departmentService.findAll();
        return departments
                .stream()
                .map(departmentMapper::mapTo)
                .collect(Collectors.toList());
    }
}
