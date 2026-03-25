package com.example.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "librarians")
public class Librarian extends User {

    private String employeeCode;

    public Librarian() {
        super();
    }

    public Librarian(String id, String name, String email, String employeeCode) {
        super(id, name, email);
        this.employeeCode = employeeCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    @Override
    public String getDetails() {
        return "Librarian [" + getId() + "] " + getName() + " - code: " + employeeCode;
    }
}
