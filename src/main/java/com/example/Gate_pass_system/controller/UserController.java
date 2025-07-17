


package com.example.Gate_pass_system.controller;

import com.example.Gate_pass_system.DTO.UserDTO;
import com.example.Gate_pass_system.entity.EmployeeRole;
import com.example.Gate_pass_system.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create or update a user
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO savedUser = userService.saveUser(userDTO);
        return ResponseEntity.ok(savedUser);
    }

    // Get user by service number
    @GetMapping("/{serviceNumber}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String serviceNumber) {
        UserDTO userDTO = userService.getUserByServiceNumber(serviceNumber);
        return ResponseEntity.ok(userDTO);
    }

    // Delete user by service number
    @DeleteMapping("/{serviceNumber}")
    public ResponseEntity<Void> deleteUser(@PathVariable String serviceNumber) {
        userService.deleteUser(serviceNumber);
        return ResponseEntity.noContent().build();
    }

    // Get all users by role
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@PathVariable String role) {
        EmployeeRole employeeRole = EmployeeRole.valueOf(role.toUpperCase());
        List<UserDTO> users = userService.getUsersByRole(employeeRole);
        return ResponseEntity.ok(users);
    }

    // Get all executive role users
    @GetMapping("/executives")
    public ResponseEntity<List<String>> getAllExecutiveRoleUsers() {
        List<String> executives = userService.getAllExecutiveRoleUsers();
        return ResponseEntity.ok(executives);
    }

    // Get all employee role users
    @GetMapping("/employees")
    public ResponseEntity<List<String>> getAllEmployeeRoleUsers() {
        List<String> employees = userService.getAllEmployeeRoleUsers();
        return ResponseEntity.ok(employees);
    }
}