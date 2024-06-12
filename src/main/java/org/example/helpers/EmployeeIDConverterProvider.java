package org.example.helpers;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;
import org.example.DTO.EmployeeDto;


import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class EmployeeIDConverterProvider implements ParamConverterProvider {


    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if(rawType.getName().equals(EmployeeDto.class.getName())) {
            return new EmployeeIdParamConverter();
        }
        return null;
    }


    public class EmployeeIdParamConverter implements ParamConverter {

        @Override
        public Object fromString(String value) {

            EmployeeDto employee_id = new EmployeeDto();
            employee_id.setDeptCode(value.substring(0, 2));
            employee_id.setIdSequence(Integer.parseInt(value.substring(2, 6)));
            employee_id.setHireYear(Integer.parseInt(value.substring(6)));

            return employee_id;
        }

        @Override
        public String toString(Object value) {
            return value.toString();
        }
    }

}