package com.chrisumeran.EMS.employee;

import com.chrisumeran.EMS.mapper.Mapper;
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
        EmployeeEntity savedEmployeeEntity = employeeService.createEmployee(employeeEntity);
        return new ResponseEntity<>(employeeMapper.mapTo(savedEmployeeEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/employees")
    public List<EmployeeDTO> findAllEmployees() {
        List<EmployeeEntity> employees = employeeService.findAll();
        return employees
                .stream()
                .map(employeeMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/employees/{empID}")
    public ResponseEntity<EmployeeDTO> findOneEmployee(@PathVariable("empID") Long empID) {
        Optional<EmployeeEntity> foundEmployee = employeeService.findOne(empID);
        return foundEmployee.map(employee -> {
            EmployeeDTO employeeDTO = employeeMapper.mapTo(employee);
            return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
