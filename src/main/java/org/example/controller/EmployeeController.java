package org.example.controller;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.dao.EmployeeDAO;
import org.example.dao.JobsDAO;
import org.example.DTO.EmployeeDto;
import org.example.DTO.EmployeeFilterDto;
import org.example.mappers.EmployeeMapper;
import org.example.models.Employees;

import java.net.URI;
import java.util.ArrayList;


@Path("/employees")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class EmployeeController {

    JobsDAO jobDao = new JobsDAO();
    EmployeeDAO dao = new EmployeeDAO();
    @Context UriInfo uriInfo;
    @Context HttpHeaders headers;



    @GET
    public Response getAllEmployees(
            @BeanParam EmployeeFilterDto filter
    ) {

        try {
            GenericEntity<ArrayList<Employees>> employees = new GenericEntity<ArrayList<Employees>>(dao.SELECT_ALL_EMPLOYEES()) {};
            GenericEntity<ArrayList<EmployeeDto>> employeesDtos = new GenericEntity<ArrayList<EmployeeDto>>(new ArrayList<EmployeeDto>()) {};
            for (Employees e : employees.getEntity()) {
                EmployeeDto dto = EmployeeMapper.INSTANCE.toEmpDto(e);
//                System.out.println(dto);
                addLinks(dto);
//                System.out.println(dto);
                employeesDtos.getEntity().add(dto);
            }

            if(headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
                return Response
                        .ok(employeesDtos)
                        .type(MediaType.APPLICATION_XML)
                        .build();
            }
            else if(headers.getAcceptableMediaTypes().contains(MediaType.valueOf("text/csv"))) {
                return Response
                        .ok(employeesDtos)
                        .type("text/csv")
                        .build();
            }
            return Response
                    .ok(employeesDtos, MediaType.APPLICATION_JSON)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @POST
    public Response insertEmployee(@PathParam("job_id") Integer job_id, Employees emp) {
        try {
            dao.INSERT_EMPLOYEE(emp);
            return Response
                    .ok(emp)
                    .status(Response.Status.CREATED)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addLinks(EmployeeDto dto) {
        URI selfUri = uriInfo.getAbsolutePath();
        URI empUri = uriInfo.getAbsolutePathBuilder()
                .path(EmployeeController.class).build();

        dto.addLink(selfUri.toString(), "self");
        dto.addLink(empUri.toString(), "employees");

    }
}