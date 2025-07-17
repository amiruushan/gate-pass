




package com.example.Gate_pass_system.services;

import com.example.Gate_pass_system.DTO.UserDTO;
import com.example.Gate_pass_system.entity.EmployeeRole;
import com.example.Gate_pass_system.entity.EmploymentType;
import com.example.Gate_pass_system.entity.User;
import com.example.Gate_pass_system.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create or update a user
    public UserDTO saveUser(UserDTO userDTO) {
        if (userRepository.existsByServiceNumber(userDTO.getServiceNumber())) {
            throw new RuntimeException("User with service number " + userDTO.getServiceNumber() + " already exists");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email " + userDTO.getEmail() + " is already in use");
        }

        User user = mapToEntity(userDTO);
        user = userRepository.save(user);
        return mapToDTO(user);
    }

    // Get user by service number
    public UserDTO getUserByServiceNumber(String serviceNumber) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByServiceNumber(serviceNumber));
        return userOptional.map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("User with service number " + serviceNumber + " not found"));
    }

    // Delete user by service number
    public void deleteUser(String serviceNumber) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByServiceNumber(serviceNumber));
        User user = userOptional.orElseThrow(() -> new RuntimeException("User with service number " + serviceNumber + " not found"));
        userRepository.delete(user);
    }

    // Get all users by role
    public List<UserDTO> getUsersByRole(EmployeeRole role) {
        return userRepository.findAllByRole(role).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get all executive role users
    public List<String> getAllExecutiveRoleUsers() {
        return userRepository.findAllExecutiveRoleUsers().stream()
                .map(row -> (String) row[0] + " - " + row[1] + " " + row[2])
                .collect(Collectors.toList());
    }

    // Get all employee role users
    public List<String> getAllEmployeeRoleUsers() {
        return userRepository.findAllEmployeeRoleUsers().stream()
                .map(row -> (String) row[0] + " - " + row[1] + " " + row[2] + " (" + row[3] + ")")
                .collect(Collectors.toList());
    }

    // Helper method to map User to UserDTO
    private UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setServiceNumber(user.getServiceNumber());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole().name());
        userDTO.setEmploymentType(user.getEmploymentType() != null ? user.getEmploymentType().name() : null);
        userDTO.setBranch(user.getBranch());
        userDTO.setDesignation(user.getDesignation());
        userDTO.setSection(user.getSection());
        userDTO.setGroup(user.getGroup());
        userDTO.setContactNumber(user.getContactNumber());
        return userDTO;
    }

    // Helper method to map UserDTO to User
    private User mapToEntity(UserDTO userDTO) {
        User user = new User();
        user.setServiceNumber(userDTO.getServiceNumber());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole() != null ? EmployeeRole.valueOf(userDTO.getRole()) : null);
        user.setEmploymentType(userDTO.getEmploymentType() != null ? EmploymentType.valueOf(userDTO.getEmploymentType()) : null);
        user.setBranch(userDTO.getBranch());
        user.setDesignation(userDTO.getDesignation());
        user.setSection(userDTO.getSection());
        user.setGroup(userDTO.getGroup());
        user.setContactNumber(userDTO.getContactNumber());
        return user;
    }
}



