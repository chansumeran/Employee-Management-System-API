package com.chrisumeran.EMS.department;

import com.chrisumeran.EMS.mapper.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
