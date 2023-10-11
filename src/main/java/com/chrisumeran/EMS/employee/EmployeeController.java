package com.chrisumeran.EMS.employee;

import com.chrisumeran.EMS.mapper.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

    private EmployeeService employeeService;
    private Mapper<EmployeeEntity, EmployeeDTO> employeeMapper;

    public EmployeeController(EmployeeService employeeService, Mapper<EmployeeEntity, EmployeeDTO> employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @PostMapping(path = "/employees")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = employeeMapper.mapFrom(employeeDTO);
        EmployeeEntity savedEmployeeEntity = employeeService.save(employeeEntity);
        return new ResponseEntity<>(employeeMapper.mapTo(savedEmployeeEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/employees")
    public Page<EmployeeDTO> findAllEmployees(Pageable pageable) {
        Page<EmployeeEntity> employees = employeeService.findAll(pageable);
        return employees.map(employeeMapper::mapTo);

    }

    @GetMapping(path = "/employees/{empID}")
    public ResponseEntity<EmployeeDTO> findOneEmployee(@PathVariable("empID") Long empID) {
        Optional<EmployeeEntity> foundEmployee = employeeService.findOne(empID);
        return foundEmployee.map(employee -> {
            EmployeeDTO employeeDTO = employeeMapper.mapTo(employee);
            return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/employees/{empID}")
    public ResponseEntity<EmployeeDTO> fullUpdateEmployee(@PathVariable("empID") Long empID,
                                                          @RequestBody EmployeeDTO employeeDTO) {

        if (!employeeService.isExists(empID)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        employeeDTO.setEmpID(empID);
        EmployeeEntity employeeEntity = employeeMapper.mapFrom(employeeDTO);
        EmployeeEntity savedEmployeeEntity = employeeService.save(employeeEntity);

        return new ResponseEntity<>(employeeMapper.mapTo(savedEmployeeEntity), HttpStatus.OK);
    }
    
    @PatchMapping(path = "/employees/{empID}")
    public ResponseEntity<EmployeeDTO> partialUpdateEmployee(@PathVariable("empID") Long empID,
                                                             @RequestBody EmployeeDTO employeeDTO) {

        if (!employeeService.isExists(empID)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        employeeDTO.setEmpID(empID);
        EmployeeEntity employeeEntity = employeeMapper.mapFrom(employeeDTO);
        EmployeeEntity savedEmployeeEntity = employeeService.partialUpdate(empID, employeeEntity);

        return new ResponseEntity<>(employeeMapper.mapTo(savedEmployeeEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/employees/{empID}")
    public ResponseEntity deleteEmployee(@PathVariable("empID") Long empID) {
        employeeService.delete(empID);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}