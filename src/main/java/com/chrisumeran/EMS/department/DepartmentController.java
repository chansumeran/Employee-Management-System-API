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

    private final DepartmentService departmentService;

    private final Mapper<DepartmentEntity, DepartmentDTO> departmentMapper;

    public DepartmentController(Mapper<DepartmentEntity, DepartmentDTO> departmentMapper, DepartmentService departmentService) {
        this.departmentMapper = departmentMapper;
        this.departmentService = departmentService;
    }

    // create endpoint
    @PostMapping(path = "/departments")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO department) {
        DepartmentEntity departmentEntity = departmentMapper.mapFrom(department);
        DepartmentEntity savedDepartmentEntity = departmentService.save(departmentEntity);

        // return HttpStatus 201
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

    // read one endpoint
    @GetMapping("departments/{deptID}")
    public ResponseEntity<DepartmentDTO> findDepartment(@PathVariable("deptID") Long deptID) {
        Optional<DepartmentEntity> foundDepartment = departmentService.findOne(deptID);

        return foundDepartment.map(department -> {
            DepartmentDTO departmentDTO = departmentMapper.mapTo(department);

            // returns a department and HttpStatus 200
            return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // update all endpoint
    @PutMapping("departments/{deptID}")
    public ResponseEntity<DepartmentDTO> fullUpdateDepartment(@PathVariable("deptID") Long deptID,
                                                              @RequestBody DepartmentDTO departmentDTO) {
        // if a department doesn't exist, return HttpStatus 404
        if (!departmentService.ifExists(deptID)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        departmentDTO.setDeptID(deptID);
        DepartmentEntity departmentEntity = departmentMapper.mapFrom(departmentDTO);
        DepartmentEntity savedDepartmentEntity = departmentService.save(departmentEntity);

        // Updates the department and returns HttpStatus 200
        return new ResponseEntity<>(departmentMapper.mapTo(savedDepartmentEntity), HttpStatus.OK);
    }

    // partial update endpoint
    @PatchMapping("/departments/{deptID}")
    public ResponseEntity<DepartmentDTO> partialUpdateDepartment(@PathVariable("deptID") Long deptID,
                                                                 @RequestBody DepartmentDTO departmentDTO) {
        if (!departmentService.ifExists(deptID)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DepartmentEntity departmentEntity = departmentMapper.mapFrom(departmentDTO);
        DepartmentEntity updatedDepartment = departmentService.partialUpdate(deptID, departmentEntity);
        return new ResponseEntity<>(departmentMapper.mapTo(updatedDepartment), HttpStatus.OK);
    }
}
