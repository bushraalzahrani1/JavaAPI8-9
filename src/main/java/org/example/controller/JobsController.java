package org.example.controller;

import jakarta.ws.rs.core.*;
import org.example.DTO.JobsFilterDTO;
import org.example.dao.JobsDAO;
import jakarta.ws.rs.*;

import org.example.DTO.JobsDto;
import org.example.mappers.JobsMapper;
import org.example.models.Jobs;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import org.example.exceptions.DataNotFoundException;


@Path("/jobs")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class JobsController {

    JobsDAO dao = new JobsDAO();
    Jobs jobs = new Jobs();
    @Context UriInfo uriInfo;
    @Context HttpHeaders headers;

    @GET
    public Response SELECT_ALL_JOBS(
            @BeanParam JobsFilterDTO filter



    ) {

        try {
            GenericEntity<ArrayList<Jobs>> jobs = new GenericEntity<ArrayList<Jobs>>(dao.SELECT_ALL_JOBS(filter.getMin_salary())) {};
            if(headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
                return Response
                        .ok(jobs)
                        .type(MediaType.APPLICATION_XML)
                        .build();
            } return Response
                    .ok(jobs, MediaType.APPLICATION_JSON)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @GET
//    @Path("{job_id}")
 /*   public Jobs SELECT_ONE_JOBS(@PathParam("job_id") int job_id) {

        try {
            return dao.selectJob(job_id);
        } catch (org.example.Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    @GET
    @Path("{job_id}")
    public Response SELECT_ONE_JOBS(@PathParam("job_id") int job_id)throws SQLException {

        try {
            Jobs job = dao.selectJob(job_id);
            if(job == null ){

                throw new DataNotFoundException("jobs " + job_id + "Not found");
            }
            //headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML) {

            JobsDto dto = JobsMapper.INSTANCE.toDeptDto(job);
            addLinks(dto);
            return Response.ok(dto).build();


        } catch (ClassNotFoundException  e) {
            throw new RuntimeException(e);
        }
    }

    private void addLinks(JobsDto dto) {
        URI selfUri = uriInfo.getAbsolutePath();
        URI empUri = uriInfo.getAbsolutePathBuilder()
                .path(JobsController.class).build();

        dto.addLink(selfUri.toString(), "self");
        dto.addLink(empUri.toString(), "employees");

    }

    @DELETE
    @Path("{job_id}")
    public void DELETE_JOB(@PathParam("job_id") int job_id) {

        try {
            dao.deleteJob(job_id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @POST
    public Response INSERT_JOB(Jobs job) {

        try {
            dao.insertJob(job);
            return Response
                    .ok(job)
                    .status(Response.Status.CREATED)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PUT
    @Path("{job_id}")
    public void UPDATE_JOB(@PathParam("job_id") int job_id, Jobs job) {

        try {
            job.setJob_id(job_id);
            dao.updateJob(job);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}