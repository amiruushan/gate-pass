package com.example.Gate_pass_system.DTO;


import java.util.Set;

public class PatrolLeaderDTO {

    private Long patrolLeaderId;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Long> branchIds;

    // Default constructor
    public PatrolLeaderDTO() {}

    // Getters and Setters
    public Long getPatrolLeaderId() {
        return patrolLeaderId;
    }

    public void setPatrolLeaderId(Long patrolLeaderId) {
        this.patrolLeaderId = patrolLeaderId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Long> getBranchIds() {
        return branchIds;
    }

    public void setBranchIds(Set<Long> branchIds) {
        this.branchIds = branchIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}