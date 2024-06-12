package org.example.mappers;

import org.example.DTO.JobsDto;
import org.example.dao.JobsDAO;
import org.example.models.Jobs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface JobsMapper {

     JobsMapper INSTANCE = Mappers.getMapper(JobsMapper.class);


    JobsDto toDeptDto(Jobs j);

    Jobs toModel(JobsDto dto);

}

