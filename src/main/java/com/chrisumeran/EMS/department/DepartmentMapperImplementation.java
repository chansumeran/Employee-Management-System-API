package com.chrisumeran.EMS.department;

import com.chrisumeran.EMS.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapperImplementation implements Mapper<DepartmentEntity, DepartmentDTO> {

    private ModelMapper modelMapper;

    public DepartmentMapperImplementation(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DepartmentDTO mapTo(DepartmentEntity departmentEntity) {
        return modelMapper.map(departmentEntity, DepartmentDTO.class);
    }

    @Override
    public DepartmentEntity mapFrom(DepartmentDTO departmentDTO) {
        return modelMapper.map(departmentDTO, DepartmentEntity.class);
    }
}
