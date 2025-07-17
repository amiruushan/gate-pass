package com.example.Gate_pass_system.controller;

import com.example.Gate_pass_system.DTO.NotificationDTO;
import com.example.Gate_pass_system.DTO.PatrolLeaderDTO;
import com.example.Gate_pass_system.services.PatrolLeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrol-leaders")
public class PatrolLeaderController {

    @Autowired
    private PatrolLeaderService patrolLeaderService;

    @PostMapping("/create")
    public ResponseEntity<PatrolLeaderDTO> createPatrolLeader(@RequestBody PatrolLeaderDTO patrolLeaderDTO) {
        PatrolLeaderDTO createdPatrolLeader = patrolLeaderService.createPatrolLeader(patrolLeaderDTO);
        return ResponseEntity.ok(createdPatrolLeader);
    }

    @GetMapping("/{patrolLeaderId}")
    public ResponseEntity<PatrolLeaderDTO> getPatrolLeader(@PathVariable Long patrolLeaderId) {
        PatrolLeaderDTO patrolLeaderDTO = patrolLeaderService.getPatrolLeader(patrolLeaderId);
        return ResponseEntity.ok(patrolLeaderDTO);
    }

    @PutMapping("/{patrolLeaderId}")
    public ResponseEntity<PatrolLeaderDTO> updatePatrolLeader(@PathVariable Long patrolLeaderId, @RequestBody PatrolLeaderDTO patrolLeaderDTO) {
        PatrolLeaderDTO updatedPatrolLeader = patrolLeaderService.updatePatrolLeader(patrolLeaderId, patrolLeaderDTO);
        return ResponseEntity.ok(updatedPatrolLeader);
    }

    @DeleteMapping("/{patrolLeaderId}")
    public ResponseEntity<Void> deletePatrolLeader(@PathVariable Long patrolLeaderId) {
        patrolLeaderService.deletePatrolLeader(patrolLeaderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{patrolLeaderId}/assign-location")
    public ResponseEntity<PatrolLeaderDTO> assignLocation(@PathVariable Long patrolLeaderId, @RequestBody Long locationId) {
        System.out.println("ok");
        PatrolLeaderDTO updatedPatrolLeader = patrolLeaderService.assignLocation(patrolLeaderId, locationId);
        return ResponseEntity.ok(updatedPatrolLeader);
    }

    @PostMapping("/{patrolLeaderId}/unassign-location")
    public ResponseEntity<PatrolLeaderDTO> unassignLocation(@PathVariable Long patrolLeaderId, @RequestBody Long locationId) {
        PatrolLeaderDTO updatedPatrolLeader = patrolLeaderService.unassignLocation(patrolLeaderId, locationId);
        return ResponseEntity.ok(updatedPatrolLeader);
    }

    @GetMapping("/by-location/{locationName}")
    public ResponseEntity<PatrolLeaderDTO> getPatrolLeaderByLocation(@PathVariable String locationName) {
        PatrolLeaderDTO patrolLeaderDTO = patrolLeaderService.getPatrolLeaderByLocation(locationName);
        return patrolLeaderDTO != null ? ResponseEntity.ok(patrolLeaderDTO) : ResponseEntity.notFound().build();
    }



    @GetMapping("/notifications/{email}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByPatrolLeaderEmail(@PathVariable String email) {
        List<NotificationDTO> notifications = patrolLeaderService.getNotificationsByPatrolLeaderEmail(email);
        return ResponseEntity.ok(notifications);
    }
}