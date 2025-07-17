

package com.example.Gate_pass_system.repo;

import com.example.Gate_pass_system.entity.User;
import com.example.Gate_pass_system.entity.EmployeeRole;
import com.example.Gate_pass_system.entity.EmploymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Find by service number
    User findByServiceNumber(String serviceNumber);

    // Check if service number exists
    boolean existsByServiceNumber(String serviceNumber);

    // Check if email exists
    boolean existsByEmail(String email);

    // Find all users by role
    List<User> findAllByRole(EmployeeRole role);

    // Get all executive role users (service number, first name, last name)
    @Query("SELECT u.serviceNumber, u.firstName, u.lastName FROM User u WHERE u.role = 'EXECUTIVE'")
    List<Object[]> findAllExecutiveRoleUsers();

    // Find user by service number and employment type
    User findByServiceNumberAndEmploymentType(String serviceNumber, EmploymentType employmentType);

    // Get all employee role users (service number, first name, last name, employment type)
    @Query("SELECT u.serviceNumber, u.firstName, u.lastName, u.employmentType FROM User u WHERE u.role = 'EMPLOYEE'")
    List<Object[]> findAllEmployeeRoleUsers();

    List<User> findByRole(EmployeeRole role);

}