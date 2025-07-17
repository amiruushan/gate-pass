package com.example.Gate_pass_system.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patrol_leader")
public class PatrolLeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patrolLeaderId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false )
    private String email;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "patrol_leader_location",
            joinColumns = @JoinColumn(name = "patrol_leader_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private Set<Location> branchesAssigned = new HashSet<>();

    // Default constructor
    public PatrolLeader() {}

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

    public Set<Location> getBranchesAssigned() {
        return branchesAssigned;
    }

    public void setBranchesAssigned(Set<Location> branchesAssigned) {
        this.branchesAssigned = branchesAssigned;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
