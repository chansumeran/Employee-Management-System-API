package com.chrisumeran.EMS.employee;

import com.chrisumeran.EMS.mapper.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        EmployeeEntity savedEmployeeEntity = employeeService.createEmployee(employeeEntity);
        return new ResponseEntity<>(employeeMapper.mapTo(savedEmployeeEntity), HttpStatus.CREATED);
    }
}
