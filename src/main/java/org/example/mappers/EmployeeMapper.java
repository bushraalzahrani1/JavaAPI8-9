package org.example.mappers;

import org.example.DTO.EmployeeDto;
import org.example.models.Employees;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {JobsMapper.class})
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);


    EmployeeDto toEmpDto(Employees employee);

    Employees toModel(EmployeeDto dto);

}