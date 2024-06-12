package org.example.DTO;

import jakarta.ws.rs.QueryParam;

public class JobsFilterDTO {

    private @QueryParam("min_salary") Double min_salary;


    public Double getMin_salary() {
        return min_salary;
    }

    public void setLocId(Double min_salary) {
        this.min_salary = min_salary;
    }


}

