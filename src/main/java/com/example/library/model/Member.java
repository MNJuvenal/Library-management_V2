package com.example.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "members")
public class Member extends User {

    private int maxBooksAllowed = 3;

    public Member() {
        super();
    }

    public Member(String id, String name, String email) {
        super(id, name, email);
    }

    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }

    public void setMaxBooksAllowed(int maxBooksAllowed) {
        this.maxBooksAllowed = maxBooksAllowed;
    }

    @Override
    public String getDetails() {
        return "Member [" + getId() + "] " + getName() + " - " + getEmail();
    }
}
