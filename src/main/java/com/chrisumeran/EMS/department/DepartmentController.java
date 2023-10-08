package com.chrisumeran.EMS.department;

import com.chrisumeran.EMS.mapper.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @GetMapping("departments/{deptID}")
    public ResponseEntity<DepartmentDTO> findDepartment(@PathVariable("deptID") Long deptID) {
        Optional<DepartmentEntity> foundDepartment = departmentService.findOne(deptID);
        return foundDepartment.map(department -> {
            DepartmentDTO departmentDTO = departmentMapper.mapTo(department);
            return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
