

package com.example.Gate_pass_system.services;

import com.example.Gate_pass_system.DTO.PatrolLeaderDTO;
import com.example.Gate_pass_system.DTO.NotificationDTO;
import com.example.Gate_pass_system.entity.Location;
import com.example.Gate_pass_system.entity.PatrolLeader;
import com.example.Gate_pass_system.entity.Notification;
import com.example.Gate_pass_system.repo.LocationRepository;
import com.example.Gate_pass_system.repo.PatrolLeaderRepository;
import com.example.Gate_pass_system.repo.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PatrolLeaderService {

    @Autowired
    private PatrolLeaderRepository patrolLeaderRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    public PatrolLeaderDTO createPatrolLeader(PatrolLeaderDTO patrolLeaderDTO) {
        PatrolLeader patrolLeader = new PatrolLeader();
        patrolLeader.setFirstName(patrolLeaderDTO.getFirstName());
        patrolLeader.setLastName(patrolLeaderDTO.getLastName());
        patrolLeader.setEmail(patrolLeaderDTO.getEmail());

        if (patrolLeaderDTO.getBranchIds() != null) {
            Set<Location> locations = patrolLeaderDTO.getBranchIds().stream()
                    .map(id -> locationRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id)))
                    .collect(Collectors.toSet());
            patrolLeader.setBranchesAssigned(locations);
        }

        PatrolLeader savedPatrolLeader = patrolLeaderRepository.save(patrolLeader);
        return mapToDTO(savedPatrolLeader);
    }

    @Transactional(readOnly = true)
    public PatrolLeaderDTO getPatrolLeader(Long patrolLeaderId) {
        PatrolLeader patrolLeader = patrolLeaderRepository.findById(patrolLeaderId)
                .orElseThrow(() -> new RuntimeException("Patrol Leader not found with ID: " + patrolLeaderId));
        return mapToDTO(patrolLeader);
    }

    @Transactional
    public PatrolLeaderDTO updatePatrolLeader(Long patrolLeaderId, PatrolLeaderDTO patrolLeaderDTO) {
        PatrolLeader patrolLeader = patrolLeaderRepository.findById(patrolLeaderId)
                .orElseThrow(() -> new RuntimeException("Patrol Leader not found with ID: " + patrolLeaderId));

        patrolLeader.setFirstName(patrolLeaderDTO.getFirstName());
        patrolLeader.setLastName(patrolLeaderDTO.getLastName());
        patrolLeader.setEmail(patrolLeaderDTO.getEmail());

        if (patrolLeaderDTO.getBranchIds() != null) {
            Set<Location> locations = patrolLeaderDTO.getBranchIds().stream()
                    .map(id -> locationRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id)))
                    .collect(Collectors.toSet());
            patrolLeader.setBranchesAssigned(locations);
        }

        PatrolLeader updatedPatrolLeader = patrolLeaderRepository.save(patrolLeader);
        return mapToDTO(updatedPatrolLeader);
    }

    @Transactional
    public void deletePatrolLeader(Long patrolLeaderId) {
        PatrolLeader patrolLeader = patrolLeaderRepository.findById(patrolLeaderId)
                .orElseThrow(() -> new RuntimeException("Patrol Leader not found with ID: " + patrolLeaderId));
        patrolLeaderRepository.delete(patrolLeader);
    }

    @Transactional
    public PatrolLeaderDTO assignLocation(Long patrolLeaderId, Long locationId) {
        PatrolLeader patrolLeader = patrolLeaderRepository.findById(patrolLeaderId)
                .orElseThrow(() -> new RuntimeException("Patrol Leader not found with ID: " + patrolLeaderId));
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found with ID: " + locationId));

        // Unassign the location from all other patrol leaders
        patrolLeaderRepository.findAll().forEach(otherPatrolLeader -> {
            if (!otherPatrolLeader.getPatrolLeaderId().equals(patrolLeaderId)) {
                Set<Location> otherBranches = otherPatrolLeader.getBranchesAssigned();
                if (otherBranches != null && otherBranches.remove(location)) {
                    otherPatrolLeader.setBranchesAssigned(otherBranches);
                    patrolLeaderRepository.save(otherPatrolLeader);
                }
            }
        });

        Set<Location> branchesAssigned = patrolLeader.getBranchesAssigned();
        if (branchesAssigned == null) {
            branchesAssigned = new HashSet<>();
            patrolLeader.setBranchesAssigned(branchesAssigned);
        }
        branchesAssigned.add(location);
        patrolLeader.setBranchesAssigned(branchesAssigned);

        PatrolLeader updatedPatrolLeader = patrolLeaderRepository.save(patrolLeader);
        return mapToDTO(updatedPatrolLeader);
    }

    @Transactional
    public PatrolLeaderDTO unassignLocation(Long patrolLeaderId, Long locationId) {
        PatrolLeader patrolLeader = patrolLeaderRepository.findById(patrolLeaderId)
                .orElseThrow(() -> new RuntimeException("Patrol Leader not found with ID: " + patrolLeaderId));
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found with ID: " + locationId));

        Set<Location> branchesAssigned = patrolLeader.getBranchesAssigned();
        if (branchesAssigned == null || !branchesAssigned.contains(location)) {
            throw new RuntimeException("Location not assigned to this patrol leader");
        }
        branchesAssigned.remove(location);
        patrolLeader.setBranchesAssigned(branchesAssigned);

        PatrolLeader updatedPatrolLeader = patrolLeaderRepository.save(patrolLeader);
        return mapToDTO(updatedPatrolLeader);
    }

    @Transactional(readOnly = true)
    public PatrolLeaderDTO getPatrolLeaderByLocation(String locationName) {
        Location location = locationRepository.findByLocationName(locationName)
                .orElseThrow(() -> new RuntimeException("Location not found with name: " + locationName));

        PatrolLeader patrolLeader = patrolLeaderRepository.findAll().stream()
                .filter(pl -> pl.getBranchesAssigned() != null && pl.getBranchesAssigned().contains(location))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No patrol leader assigned to location: " + locationName));

        return mapToDTO(patrolLeader);
    }

    @Transactional(readOnly = true)
    public List<NotificationDTO> getNotificationsByPatrolLeaderEmail(String email) {
        List<Notification> notifications = notificationRepository.findByRecipientEmail(email);
        return notifications.stream().map(this::mapToNotificationDTO).collect(Collectors.toList());
    }

    private PatrolLeaderDTO mapToDTO(PatrolLeader patrolLeader) {
        PatrolLeaderDTO dto = new PatrolLeaderDTO();
        dto.setPatrolLeaderId(patrolLeader.getPatrolLeaderId());
        dto.setFirstName(patrolLeader.getFirstName());
        dto.setLastName(patrolLeader.getLastName());
        dto.setEmail(patrolLeader.getEmail());
        dto.setBranchIds(patrolLeader.getBranchesAssigned().stream()
                .map(Location::getLocationId)
                .collect(Collectors.toSet()));
        return dto;
    }

    private NotificationDTO mapToNotificationDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationId(notification.getNotificationId());
        dto.setRecipientEmail(notification.getRecipientEmail());
        dto.setSubject(notification.getSubject());
        dto.setBody(notification.getBody());
        dto.setSentDate(notification.getSentDate());
        if (notification.getRequest() != null) {
            dto.setRequestId(notification.getRequest().getRefNo());
            dto.setOutLocation(notification.getRequest().getOutLocation().getLocationName());
            dto.setInLocation(notification.getRequest().getInLocation().getLocationName());
            dto.setCreatorName(notification.getRequest().getSender().getFirstName() + " " +
                    notification.getRequest().getSender().getLastName());
        }
        return dto;
    }
}