package org.example.DTO;

import java.util.Date;

public class EmployeeDto {


    private String deptCode;
    private int IdSequence;
    private int hireYear;



    public EmployeeDto(String deptCode, int idSequence, int hireYear) {
        this.deptCode = deptCode;
        IdSequence = idSequence;
        this.hireYear = hireYear;
    }

    public EmployeeDto() {

    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public int getIdSequence() {
        return IdSequence;
    }

    public void setIdSequence(int idSequence) {
        IdSequence = idSequence;
    }

    public int getHireYear() {
        return hireYear;
    }

    public void setHireYear(int hireYear) {
        this.hireYear = hireYear;
    }

    @Override
    public String toString() {
        return deptCode + IdSequence + hireYear;
    }

    public void addLink(String string, String employees) {

    }
}