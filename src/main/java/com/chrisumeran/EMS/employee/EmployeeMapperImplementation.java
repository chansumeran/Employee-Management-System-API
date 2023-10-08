package com.chrisumeran.EMS.employee;

import com.chrisumeran.EMS.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapperImplementation implements Mapper<EmployeeEntity, EmployeeDTO> {

    private ModelMapper modelMapper;

    public EmployeeMapperImplementation(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDTO mapTo(EmployeeEntity employeeEntity) {
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    @Override
    public EmployeeEntity mapFrom(EmployeeDTO employeeDTO) {
        return modelMapper.map(employeeDTO, EmployeeEntity.class);
    }
}
