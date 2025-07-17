//package com.example.Gate_pass_system.services;
//
//import com.example.Gate_pass_system.DTO.VerificationDetailsDTO;
//import com.example.Gate_pass_system.DTO.VerificationRequestDTO;
//import com.example.Gate_pass_system.DTO.VerificationResponseDTO;
//import com.example.Gate_pass_system.entity.Verification;
//import com.example.Gate_pass_system.entity.VerificationStatus;
//import com.example.Gate_pass_system.entity.GatePassRequest;
//import com.example.Gate_pass_system.entity.User;
//import com.example.Gate_pass_system.repo.VerificationRepository;
//import com.example.Gate_pass_system.repo.GatePassRequestRepository;
//import com.example.Gate_pass_system.repo.UserRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//public class VerificationService {
//    @Autowired
//    private VerificationRepository verificationRepository;
//    @Autowired
//    private GatePassRequestRepository gatePassRequestRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private EmailService emailService;
//
//    public Verification verifyOrRejectRequest(VerificationRequestDTO verificationDTO) {
//        if (verificationDTO == null) {
//            throw new IllegalArgumentException("Verification DTO cannot be null");
//        }
//        GatePassRequest request = gatePassRequestRepository.findByRefNo(verificationDTO.getRequestId())
//                .orElseThrow(() -> new RuntimeException("Request not found with refNo: " + verificationDTO.getRequestId()));
//        User verifiedBy = userRepository.findById(verificationDTO.getVerifiedBy())
//                .orElseThrow(() -> new RuntimeException("User not found with serviceNumber: " + verificationDTO.getVerifiedBy()));
//        if (!request.getStatus().equals("ExecutiveApproved")) {
//            throw new RuntimeException("Request must be approved by executive officer before verification");
//        }
//        Verification verification = verificationRepository.findByRequest_RefNo(request.getRefNo())
//                .orElse(new Verification());
//        verification.setRequest(request);
//        verification.setVerifiedBy(verifiedBy);
//        verification.setVerificationStatus(verificationDTO.getVerificationStatus());
//        verification.setComments(verificationDTO.getComments());
//        verification.setVerificationDate(LocalDateTime.now());
//        String newStatus = verificationDTO.getVerificationStatus() == VerificationStatus.VERIFIED ? "DutyOfficerApproved" : "DutyOfficerRejected";
//        request.setStatus(newStatus);
//        gatePassRequestRepository.save(request);
//        Verification savedVerification = verificationRepository.save(verification);
//        try {
//            String senderEmail = request.getSender().getEmail();
//            String executiveEmail = request.getExecutiveOfficer().getEmail();
//            String requestId = String.valueOf(request.getRefNo());
//            String comments = verificationDTO.getComments();
//            String role = "Duty Officer";
//            if (verificationDTO.getVerificationStatus() == VerificationStatus.REJECTED) {
//                emailService.sendRejectionEmail(senderEmail, requestId, role, comments, "Rejected");
//                emailService.sendRejectionEmail(executiveEmail, requestId, role, comments, "Rejected");
//            } else {
//                emailService.sendApprovalEmail(senderEmail, requestId, role, comments);
//                emailService.sendApprovalEmail(executiveEmail, requestId, role, comments);
//            }
//        } catch (Exception e) {
//            System.err.println("Failed to send email: " + e.getMessage());
//        }
//        return savedVerification;
//    }
//
//    public List<VerificationResponseDTO> getVerificationsByDutyOfficer(String dutyOfficerId) {
//        if (dutyOfficerId == null || dutyOfficerId.trim().isEmpty()) {
//            throw new IllegalArgumentException("Duty Officer ID cannot be null or empty");
//        }
//        return verificationRepository.findByVerifiedBy_ServiceNumber(dutyOfficerId)
//                .stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    public List<GatePassRequest> getPendingVerifications() {
//        return gatePassRequestRepository.findByStatus("ExecutiveApproved");
//    }
//
//    public VerificationDetailsDTO getVerificationDetailsByRequestId(String requestId) {
//        return new VerificationDetailsDTO(); // Implement as needed
//    }
//
//    private VerificationResponseDTO convertToDto(Verification verification) {
//        if (verification == null) {
//            return null;
//        }
//        VerificationResponseDTO dto = new VerificationResponseDTO();
//        dto.setVerificationId(verification.getVerificationId());
//        dto.setRequestId(verification.getRequest().getRefNo());
//        User verifiedBy = verification.getVerifiedBy();
//        if (verifiedBy != null) {
//            String fullName = (verifiedBy.getFirstName() != null ? verifiedBy.getFirstName() : "") + " " +
//                    (verifiedBy.getLastName() != null ? verifiedBy.getLastName() : "");
//            dto.setVerifiedByName(fullName.trim());
//        }
//        dto.setVerificationStatus(verification.getVerificationStatus());
//        dto.setVerificationDate(verification.getVerificationDate());
//        dto.setComments(verification.getComments());
//        return dto;
//    }
//}

//dem chnage kare ude
//
//package com.example.Gate_pass_system.services;
//import com.example.Gate_pass_system.DTO.*;
//import com.example.Gate_pass_system.entity.GatePassRequest;
//import com.example.Gate_pass_system.entity.User;
//import com.example.Gate_pass_system.entity.Verification;
//import com.example.Gate_pass_system.entity.VerificationStatus;
//import com.example.Gate_pass_system.repo.GatePassRequestRepository;
//import com.example.Gate_pass_system.repo.UserRepository;
//import com.example.Gate_pass_system.repo.VerificationRepository;
//import com.example.Gate_pass_system.services.EmailService;
//import com.example.Gate_pass_system.services.PatrolLeaderService;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//public class VerificationService {
//    @Autowired
//    private VerificationRepository verificationRepository;
//    @Autowired
//    private GatePassRequestRepository gatePassRequestRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private EmailService emailService;
//    @Autowired
//    private PatrolLeaderService patrolLeaderService;
//
//    public Verification verifyOrRejectRequest(VerificationRequestDTO verificationDTO) {
//        if (verificationDTO == null) {
//            throw new IllegalArgumentException("Verification DTO cannot be null");
//        }
//        GatePassRequest request = gatePassRequestRepository.findByRefNo(verificationDTO.getRequestId())
//                .orElseThrow(() -> new RuntimeException("Request not found with refNo: " + verificationDTO.getRequestId()));
//        User verifiedBy = userRepository.findById(verificationDTO.getVerifiedBy())
//                .orElseThrow(() -> new RuntimeException("User not found with serviceNumber: " + verificationDTO.getVerifiedBy()));
//        User checkedBy = verificationDTO.getCheckedBy() != null ? userRepository.findById(verificationDTO.getCheckedBy())
//                .orElseThrow(() -> new RuntimeException("Checker not found with serviceNumber: " + verificationDTO.getCheckedBy())) : null;
//
//        if (!request.getStatus().equals("ExecutiveApproved")) {
//            throw new RuntimeException("Request must be approved by executive officer before verification");
//        }
//        Verification verification = verificationRepository.findByRequest_RefNo(request.getRefNo())
//                .orElse(new Verification());
//        verification.setRequest(request);
//        verification.setVerifiedBy(verifiedBy);
//        verification.setCheckedBy(checkedBy);
//        verification.setVerificationStatus(verificationDTO.getVerificationStatus());
//        verification.setComments(verificationDTO.getComments());
//        verification.setVerificationDate(LocalDateTime.now());
//        String newStatus = verificationDTO.getVerificationStatus() == VerificationStatus.VERIFIED ? "DutyOfficerApproved" : "DutyOfficerRejected";
//        request.setStatus(newStatus);
//        gatePassRequestRepository.save(request);
//        Verification savedVerification = verificationRepository.save(verification);
//
//        try {
//            String senderEmail = request.getSender().getEmail();
//            String executiveEmail = request.getExecutiveOfficer().getEmail();
//            String requestId = String.valueOf(request.getRefNo());
//            String comments = verificationDTO.getComments();
//            String role = "Duty Officer";
//
//            // Send emails to sender and executive
//            if (verificationDTO.getVerificationStatus() == VerificationStatus.REJECTED) {
//                emailService.sendRejectionEmail(senderEmail, requestId, role, comments, "Rejected", request);
//                emailService.sendRejectionEmail(executiveEmail, requestId, role, comments, "Rejected", request);
//            } else {
//                emailService.sendApprovalEmail(senderEmail, requestId, role, comments, request);
//                emailService.sendApprovalEmail(executiveEmail, requestId, role, comments, request);
//            }
//
//            // Send email to patrol leader if approved and from Colombo Main to another location
//            if (verificationDTO.getVerificationStatus() == VerificationStatus.VERIFIED) {
//                String outLocationName = request.getOutLocation().getLocationName();
//                String inLocationName = request.getInLocation().getLocationName();
//                String creatorName = request.getSender().getFirstName() + " " + request.getSender().getLastName();
//                if (outLocationName != null && outLocationName.equalsIgnoreCase("Colombo Main") && !inLocationName.equalsIgnoreCase("Colombo Main")) {
//                    PatrolLeaderDTO patrolLeader = patrolLeaderService.getPatrolLeaderByLocation(inLocationName);
//                    if (patrolLeader != null) {
//                        String patrolLeaderEmail = patrolLeader.getEmail();
//                        String patrolSubject = "Gate Pass Request #" + requestId + " - Approved and Heading to Your Region";
//                        boolean dutyApproved = request.getStatus().equals("DutyOfficerApproved");
//                        String patrolBody = "<!DOCTYPE html>" +
//                                "<html lang='en'>" +
//                                "<head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><title>Gate Pass Notification</title></head>" +
//                                "<body style='margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif; background-color: #f4f4f4;'>" +
//                                "<table align='center' border='0' cellpadding='0' cellspacing='0' width='100%' style='max-width: 600px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); margin: 20px auto;'>" +
//                                "<tr><td style='background: linear-gradient(to right, #87CEEB, #4682B4); padding: 20px; text-align: center; border-top-left-radius: 8px; border-top-right-radius: 8px;'>" +
//                                "<img src='https://upload.wikimedia.org/wikipedia/commons/thumb/e/ed/SLTMobitel_Logo.svg/1280px-SLTMobitel_Logo.svg.png' alt='Gate Pass System Logo' style='max-width: 150px;'>" +
//                                "<h1 style='color: #FFFFFF; font-size: 24px; margin: 10px 0;'>Gate Pass Notification</h1></td></tr>" +
//                                "<tr><td style='padding: 20px;'><h2 style='color: #4682B4; font-size: 20px; margin: 0 0 10px;'>Request #" + requestId + " - Approved</h2>" +
//                                "<p style='color: #1E3A8A; font-size: 16px; line-height: 1.5; margin: 0 0 20px;'>A gate pass request has been approved and is heading to your region (" + inLocationName + ").</p>" +
//                                "<table border='0' cellpadding='0' cellspacing='0' width='100%' style='background-color: #E6F0FA; border-radius: 6px; padding: 15px; margin-bottom: 20px;'>" +
//                                "<tr><td style='color: #1E3A8A; font-size: 16px;'><strong>Out Location:</strong> " + outLocationName + "</td></tr>" +
//                                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>In Location:</strong> " + inLocationName + "</td></tr>" +
//                                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Created Date:</strong> " + request.getCreatedDate().toString() + "</td></tr>" +
//                                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Created By:</strong> " + creatorName + "</td></tr>" +
//                                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Executive Approved:</strong> Yes</td></tr>" +
//                                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Duty Approved:</strong> " + (dutyApproved ? "Yes" : "No") + "</td></tr>" +
//                                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Checked By:</strong> " + (checkedBy != null ? (checkedBy.getFirstName() + " " + checkedBy.getLastName()) : "N/A") + "</td></tr>" +
//                                "</table>" +
//                                "<p style='color: #1E3A8A; font-size: 16px; line-height: 1.5; margin: 0 0 20px;'>Details: <a href='http://localhost:3000' style='color: #1E90FF; text-decoration: none; font-weight: bold;'>Gate Pass System</a>.</p></td></tr>" +
//                                "<tr><td style='background-color: #f4f4f4; padding: 15px; text-align: center; border-bottom-left-radius: 8px; border-bottom-right-radius: 8px;'>" +
//                                "<p style='color: #6B7280; font-size: 12px; margin: 0;'>This is an automated email from the Gate Pass System. Please do not reply directly to this email.</p>" +
//                                "<p style='color: #6B7280; font-size: 12px; margin: 5px 0 0;'><a href='http://localhost:3000/support' style='color: #1E90FF; text-decoration: none;'>Contact Support</a></p></td></tr>" +
//                                "</table></body></html>";
//                        emailService.sendEmail(patrolLeaderEmail, patrolSubject, patrolBody);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("Failed to send email: " + e.getMessage());
//        }
//        return savedVerification;
//    }
//
//    public List<VerificationResponseDTO> getVerificationsByDutyOfficer(String dutyOfficerId) {
//        if (dutyOfficerId == null || dutyOfficerId.trim().isEmpty()) {
//            throw new IllegalArgumentException("Duty Officer ID cannot be null or empty");
//        }
//        return verificationRepository.findByVerifiedBy_ServiceNumber(dutyOfficerId)
//                .stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    public List<GatePassRequest> getPendingVerifications() {
//        return gatePassRequestRepository.findByStatus("ExecutiveApproved");
//    }
//
//    public VerificationDetailsDTO getVerificationDetailsByRequestId(String requestId) {
//        VerificationDetailsDTO dto = new VerificationDetailsDTO();
//        // Implement logic to populate dto based on requestId
//        Verification verification = verificationRepository.findByRequest_RefNo(Long.parseLong(requestId))
//                .orElse(null);
//        if (verification != null) {
//            // Executive details (from Approval entity if exists, simplified here)
//            dto.setExecutiveOfficer(new VerificationUserDTO(verification.getRequest().getExecutiveOfficer().getServiceNumber(),
//                    verification.getRequest().getExecutiveOfficer().getFirstName() + " " + verification.getRequest().getExecutiveOfficer().getLastName()));
//            dto.setExecutiveStatus(verification.getRequest().getStatus().contains("Executive") ? "Approved" : "Pending");
//            dto.setExecutiveComments(""); // Add logic if needed
//            dto.setExecutiveDate(verification.getRequest().getCreatedDate()); // Adjust if executive approval date is needed
//
//            // Duty officer details
//            dto.setDutyOfficer(new VerificationUserDTO(verification.getVerifiedBy().getServiceNumber(),
//                    verification.getVerifiedBy().getFirstName() + " " + verification.getVerifiedBy().getLastName()));
//            dto.setDutyStatus(verification.getVerificationStatus().name());
//            dto.setDutyComments(verification.getComments());
//            dto.setDutyDate(verification.getVerificationDate());
//
//            // Checker details
//            if (verification.getCheckedBy() != null) {
//                dto.setCheckedBy(new VerificationUserDTO(verification.getCheckedBy().getServiceNumber(),
//                        verification.getCheckedBy().getFirstName() + " " + verification.getCheckedBy().getLastName()));
//                dto.setCheckComments(verification.getComments()); // Reuse comments or add separate field if needed
//            }
//        }
//        return dto;
//    }
//
//    private VerificationResponseDTO convertToDto(Verification verification) {
//        if (verification == null) {
//            return null;
//        }
//        VerificationResponseDTO dto = new VerificationResponseDTO();
//        dto.setVerificationId(verification.getVerificationId());
//        dto.setRequestId(verification.getRequest().getRefNo());
//        User verifiedBy = verification.getVerifiedBy();
//        if (verifiedBy != null) {
//            String fullName = (verifiedBy.getFirstName() != null ? verifiedBy.getFirstName() : "") + " " + (verifiedBy.getLastName() != null ? verifiedBy.getLastName() : "");
//            dto.setVerifiedByName(fullName.trim());
//        }
//        User checkedBy = verification.getCheckedBy();
//        if (checkedBy != null) {
//            String checkedByName = (checkedBy.getFirstName() != null ? checkedBy.getFirstName() : "") + " " + (checkedBy.getLastName() != null ? checkedBy.getLastName() : "");
//            dto.setCheckedByName(checkedByName.trim());
//        }
//        dto.setVerificationStatus(verification.getVerificationStatus());
//        dto.setVerificationDate(verification.getVerificationDate());
//        dto.setComments(verification.getComments());
//        return dto;
//    }
//}



package com.example.Gate_pass_system.services;

import com.example.Gate_pass_system.DTO.*;
import com.example.Gate_pass_system.entity.GatePassRequest;
import com.example.Gate_pass_system.entity.User;
import com.example.Gate_pass_system.entity.Verification;
import com.example.Gate_pass_system.entity.VerificationStatus;
import com.example.Gate_pass_system.repo.GatePassRequestRepository;
import com.example.Gate_pass_system.repo.UserRepository;
import com.example.Gate_pass_system.repo.VerificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VerificationService {
    @Autowired
    private VerificationRepository verificationRepository;
    @Autowired
    private GatePassRequestRepository gatePassRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PatrolLeaderService patrolLeaderService;

    public Verification verifyOrRejectRequest(VerificationRequestDTO verificationDTO) {
        if (verificationDTO == null) {
            throw new IllegalArgumentException("Verification DTO cannot be null");
        }
        GatePassRequest request = gatePassRequestRepository.findByRefNo(verificationDTO.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found with refNo: " + verificationDTO.getRequestId()));
        User verifiedBy = userRepository.findById(verificationDTO.getVerifiedBy())
                .orElseThrow(() -> new RuntimeException("User not found with serviceNumber: " + verificationDTO.getVerifiedBy()));
        User checkedBy = verificationDTO.getCheckedBy() != null ? userRepository.findById(verificationDTO.getCheckedBy())
                .orElseThrow(() -> new RuntimeException("Checker not found with serviceNumber: " + verificationDTO.getCheckedBy())) : null;

        if (!request.getStatus().equals("ExecutiveApproved")) {
            throw new RuntimeException("Request must be approved by executive officer before verification");
        }
        Verification verification = verificationRepository.findByRequest_RefNo(request.getRefNo())
                .orElse(new Verification());
        verification.setRequest(request);
        verification.setVerifiedBy(verifiedBy);
        verification.setCheckedBy(checkedBy);
        verification.setVerificationStatus(verificationDTO.getVerificationStatus());
        verification.setComments(verificationDTO.getComments());
        verification.setVerificationDate(LocalDateTime.now());
        String newStatus = verificationDTO.getVerificationStatus() == VerificationStatus.VERIFIED ? "DutyOfficerApproved" : "DutyOfficerRejected";
        request.setStatus(newStatus);
        gatePassRequestRepository.save(request);
        Verification savedVerification = verificationRepository.save(verification);

        try {
            String senderEmail = request.getSender().getEmail();
            String executiveEmail = request.getExecutiveOfficer().getEmail();
            String requestId = String.valueOf(request.getRefNo());
            String comments = verificationDTO.getComments();
            String role = "Duty Officer";

            // Send emails to sender and executive
            if (verificationDTO.getVerificationStatus() == VerificationStatus.REJECTED) {
                emailService.sendRejectionEmail(senderEmail, requestId, role, comments, "Rejected", request);
                emailService.sendRejectionEmail(executiveEmail, requestId, role, comments, "Rejected", request);
            } else {
                emailService.sendApprovalEmail(senderEmail, requestId, role, comments, request);
                emailService.sendApprovalEmail(executiveEmail, requestId, role, comments, request);
            }

            // Send email to patrol leader if approved and from Colombo Main to another location
            if (verificationDTO.getVerificationStatus() == VerificationStatus.VERIFIED) {
                String outLocationName = request.getOutLocation().getLocationName();
                String inLocationName = request.getInLocation().getLocationName();
                String creatorName = request.getSender().getFirstName() + " " + request.getSender().getLastName();
                if (outLocationName != null && outLocationName.equalsIgnoreCase("Colombo Main") && !inLocationName.equalsIgnoreCase("Colombo Main")) {
                    PatrolLeaderDTO patrolLeader = patrolLeaderService.getPatrolLeaderByLocation(inLocationName);
                    if (patrolLeader != null) {
                        String patrolLeaderEmail = patrolLeader.getEmail();
                        String patrolSubject = "Gate Pass Request #" + requestId + " - Approved and Heading to Your Region";
                        boolean dutyApproved = request.getStatus().equals("DutyOfficerApproved");
                        String patrolBody = "<!DOCTYPE html>" +
                                "<html lang='en'>" +
                                "<head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><title>Gate Pass Notification</title></head>" +
                                "<body style='margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif; background-color: #f4f4f4;'>" +
                                "<table align='center' border='0' cellpadding='0' cellspacing='0' width='100%' style='max-width: 600px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); margin: 20px auto;'>" +
                                "<tr><td style='background: linear-gradient(to right, #87CEEB, #4682B4); padding: 20px; text-align: center; border-top-left-radius: 8px; border-top-right-radius: 8px;'>" +
                                "<img src='https://upload.wikimedia.org/wikipedia/commons/thumb/e/ed/SLTMobitel_Logo.svg/1280px-SLTMobitel_Logo.svg.png' alt='Gate Pass System Logo' style='max-width: 150px;'>" +
                                "<h1 style='color: #FFFFFF; font-size: 24px; margin: 10px 0;'>Gate Pass Notification</h1></td></tr>" +
                                "<tr><td style='padding: 20px;'><h2 style='color: #4682B4; font-size: 20px; margin: 0 0 10px;'>Request #" + requestId + " - Approved</h2>" +
                                "<p style='color: #1E3A8A; font-size: 16px; line-height: 1.5; margin: 0 0 20px;'>A gate pass request has been approved and is heading to your region (" + inLocationName + ").</p>" +
                                "<table border='0' cellpadding='0' cellspacing='0' width='100%' style='background-color: #E6F0FA; border-radius: 6px; padding: 15px; margin-bottom: 20px;'>" +
                                "<tr><td style='color: #1E3A8A; font-size: 16px;'><strong>Out Location:</strong> " + outLocationName + "</td></tr>" +
                                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>In Location:</strong> " + inLocationName + "</td></tr>" +
                                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Created Date:</strong> " + request.getCreatedDate().toString() + "</td></tr>" +
                                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Created By:</strong> " + creatorName + "</td></tr>" +
                                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Executive Approved:</strong> Yes</td></tr>" +
                                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Duty Approved:</strong> " + (dutyApproved ? "Yes" : "No") + "</td></tr>" +
                                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Checked By:</strong> " + (checkedBy != null ? (checkedBy.getFirstName() + " " + checkedBy.getLastName()) : "N/A") + "</td></tr>" +
                                "</table>" +
                                "<p style='color: #1E3A8A; font-size: 16px; line-height: 1.5; margin: 0 0 20px;'>Details: <a href='http://localhost:3000' style='color: #1E90FF; text-decoration: none; font-weight: bold;'>Gate Pass System</a>.</p></td></tr>" +
                                "<tr><td style='background-color: #f4f4f4; padding: 15px; text-align: center; border-bottom-left-radius: 8px; border-bottom-right-radius: 8px;'>" +
                                "<p style='color: #6B7280; font-size: 12px; margin: 0;'>This is an automated email from the Gate Pass System. Please do not reply directly to this email.</p>" +
                                "<p style='color: #6B7280; font-size: 12px; margin: 5px 0 0;'><a href='http://localhost:3000/support' style='color: #1E90FF; text-decoration: none;'>Contact Support</a></p></td></tr>" +
                                "</table></body></html>";
                        emailService.sendEmailWithRequest(patrolLeaderEmail, patrolSubject, patrolBody, request);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
        return savedVerification;
    }

    public List<VerificationResponseDTO> getVerificationsByDutyOfficer(String dutyOfficerId) {
        if (dutyOfficerId == null || dutyOfficerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Duty Officer ID cannot be null or empty");
        }
        return verificationRepository.findByVerifiedBy_ServiceNumber(dutyOfficerId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<GatePassRequest> getPendingVerifications() {
        return gatePassRequestRepository.findByStatus("ExecutiveApproved");
    }

    public VerificationDetailsDTO getVerificationDetailsByRequestId(String requestId) {
        VerificationDetailsDTO dto = new VerificationDetailsDTO();
        // Implement logic to populate dto based on requestId
        Verification verification = verificationRepository.findByRequest_RefNo(Long.parseLong(requestId))
                .orElse(null);
        if (verification != null) {
            // Executive details (from Approval entity if exists, simplified here)
            dto.setExecutiveOfficer(new VerificationUserDTO(verification.getRequest().getExecutiveOfficer().getServiceNumber(),
                    verification.getRequest().getExecutiveOfficer().getFirstName() + " " + verification.getRequest().getExecutiveOfficer().getLastName()));
            dto.setExecutiveStatus(verification.getRequest().getStatus().contains("Executive") ? "Approved" : "Pending");
            dto.setExecutiveComments(""); // Add logic if needed
            dto.setExecutiveDate(verification.getRequest().getCreatedDate()); // Adjust if executive approval date is needed

            // Duty officer details
            dto.setDutyOfficer(new VerificationUserDTO(verification.getVerifiedBy().getServiceNumber(),
                    verification.getVerifiedBy().getFirstName() + " " + verification.getVerifiedBy().getLastName()));
            dto.setDutyStatus(verification.getVerificationStatus().name());
            dto.setDutyComments(verification.getComments());
            dto.setDutyDate(verification.getVerificationDate());

            // Checker details
            if (verification.getCheckedBy() != null) {
                dto.setCheckedBy(new VerificationUserDTO(verification.getCheckedBy().getServiceNumber(),
                        verification.getCheckedBy().getFirstName() + " " + verification.getCheckedBy().getLastName()));
                dto.setCheckComments(verification.getComments()); // Reuse comments or add separate field if needed
            }
        }
        return dto;
    }

    private VerificationResponseDTO convertToDto(Verification verification) {
        if (verification == null) {
            return null;
        }
        VerificationResponseDTO dto = new VerificationResponseDTO();
        dto.setVerificationId(verification.getVerificationId());
        dto.setRequestId(verification.getRequest().getRefNo());
        User verifiedBy = verification.getVerifiedBy();
        if (verifiedBy != null) {
            String fullName = (verifiedBy.getFirstName() != null ? verifiedBy.getFirstName() : "") + " " + (verifiedBy.getLastName() != null ? verifiedBy.getLastName() : "");
            dto.setVerifiedByName(fullName.trim());
        }
        User checkedBy = verification.getCheckedBy();
        if (checkedBy != null) {
            String checkedByName = (checkedBy.getFirstName() != null ? checkedBy.getFirstName() : "") + " " + (checkedBy.getLastName() != null ? checkedBy.getLastName() : "");
            dto.setCheckedByName(checkedByName.trim());
        }
        dto.setVerificationStatus(verification.getVerificationStatus());
        dto.setVerificationDate(verification.getVerificationDate());
        dto.setComments(verification.getComments());
        return dto;
    }
}