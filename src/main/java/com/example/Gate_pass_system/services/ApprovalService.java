//package com.example.Gate_pass_system.services;
//
//import com.example.Gate_pass_system.DTO.ApprovalRequestDTO;
//import com.example.Gate_pass_system.DTO.ApprovalResponseDTO;
//import com.example.Gate_pass_system.entity.Approval;
//import com.example.Gate_pass_system.entity.ApprovalStatus;
//import com.example.Gate_pass_system.entity.GatePassRequest;
//import com.example.Gate_pass_system.entity.User;
//import com.example.Gate_pass_system.repo.ApprovalRepository;
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
//public class ApprovalService {
//
//    @Autowired
//    private ApprovalRepository approvalRepository;
//
//    @Autowired
//    private GatePassRequestRepository gatePassRequestRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private EmailService emailService;
//
//    public Approval approveOrRejectRequest(ApprovalRequestDTO approvalDTO) {
//        // Validate input
//        if (approvalDTO == null) {
//            throw new IllegalArgumentException("Approval DTO cannot be null");
//        }
//
//        // Find the gate pass request
//        GatePassRequest request = gatePassRequestRepository.findByRefNo(approvalDTO.getRequestId())
//                .orElseThrow(() -> new RuntimeException("Request not found with refNo: " + approvalDTO.getRequestId()));
//
//        // Find the approving user
//        User approvedBy = userRepository.findById(approvalDTO.getApprovedBy())
//                .orElseThrow(() -> new RuntimeException("User not found with serviceNumber: " + approvalDTO.getApprovedBy()));
//
//        // Create or update approval
//        Approval approval = approvalRepository.findByRequest_RefNo(request.getRefNo())
//                .orElse(new Approval());
//
//        approval.setRequest(request);
//        approval.setApprovedBy(approvedBy);
//        approval.setApprovalStatus(approvalDTO.getApprovalStatus());
//        approval.setComments(approvalDTO.getComments());
//        approval.setApprovalDate(LocalDateTime.now());
//
//        // Update gate pass request status
//        String newStatus = approvalDTO.getApprovalStatus() == ApprovalStatus.APPROVED ? "ExecutiveApproved" : "ExecutiveRejected";
//        request.setStatus(newStatus);
//
//        // Save changes
//        gatePassRequestRepository.save(request);
//        Approval savedApproval = approvalRepository.save(approval);
//
//        // Send email notifications
//        try {
//            String senderEmail = request.getSender().getEmail();
//            String requestId = String.valueOf(request.getRefNo());
//            String comments = approvalDTO.getComments();
//            String role = "Executive Officer";
//
//            if (approvalDTO.getApprovalStatus() == ApprovalStatus.REJECTED) {
//                // Send rejection email to sender
//                emailService.sendRejectionEmail(senderEmail, requestId, role, comments, "Rejected");
//            } else {
//                // Send approval email to sender
//                emailService.sendApprovalEmail(senderEmail, requestId, role, comments);
//            }
//        } catch (Exception e) {
//            // Log email sending error but don't fail the approval process
//            System.err.println("Failed to send email: " + e.getMessage());
//        }
//
//        return savedApproval;
//    }
//
//    public List<ApprovalResponseDTO> getApprovalsByExecutive(String executiveId) {
//        if (executiveId == null || executiveId.trim().isEmpty()) {
//            throw new IllegalArgumentException("Executive ID cannot be null or empty");
//        }
//
//        return approvalRepository.findByApprovedBy_ServiceNumber(executiveId)
//                .stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    private ApprovalResponseDTO convertToDto(Approval approval) {
//        ApprovalResponseDTO dto = new ApprovalResponseDTO();
//        dto.setApprovalId(approval.getApprovalId());
//        dto.setRequestId(approval.getRequest().getRefNo());
//
//        User approvedBy = approval.getApprovedBy();
//        String fullName = approvedBy.getFirstName() + " " + approvedBy.getLastName();
//        dto.setApprovedByName(fullName);
//
//        dto.setApprovalStatus(approval.getApprovalStatus());
//        dto.setApprovalDate(approval.getApprovalDate());
//        dto.setComments(approval.getComments());
//        return dto;
//    }
//}




package com.example.Gate_pass_system.services;


import com.example.Gate_pass_system.DTO.ApprovalRequestDTO;
import com.example.Gate_pass_system.DTO.ApprovalResponseDTO;
import com.example.Gate_pass_system.entity.Approval;
import com.example.Gate_pass_system.entity.ApprovalStatus;
import com.example.Gate_pass_system.entity.GatePassRequest;
import com.example.Gate_pass_system.entity.User;
import com.example.Gate_pass_system.entity.EmployeeRole;
import com.example.Gate_pass_system.repo.ApprovalRepository;
import com.example.Gate_pass_system.repo.GatePassRequestRepository;
import com.example.Gate_pass_system.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApprovalService {
    @Autowired
    private ApprovalRepository approvalRepository;
    @Autowired
    private GatePassRequestRepository gatePassRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    public Approval approveOrRejectRequest(ApprovalRequestDTO approvalDTO) {
        // Validate input
        if (approvalDTO == null) {
            throw new IllegalArgumentException("Approval DTO cannot be null");
        }
        // Find the gate pass request
        GatePassRequest request = gatePassRequestRepository.findByRefNo(approvalDTO.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found with refNo: " + approvalDTO.getRequestId()));
        // Find the approving user
        User approvedBy = userRepository.findById(approvalDTO.getApprovedBy())
                .orElseThrow(() -> new RuntimeException("User not found with serviceNumber: " + approvalDTO.getApprovedBy()));
        // Create or update approval
        Approval approval = approvalRepository.findByRequest_RefNo(request.getRefNo())
                .orElse(new Approval());
        approval.setRequest(request);
        approval.setApprovedBy(approvedBy);
        approval.setApprovalStatus(approvalDTO.getApprovalStatus());
        approval.setComments(approvalDTO.getComments());
        approval.setApprovalDate(LocalDateTime.now());
        // Update gate pass request status
        String newStatus = approvalDTO.getApprovalStatus() == ApprovalStatus.APPROVED ? "ExecutiveApproved" : "ExecutiveRejected";
        request.setStatus(newStatus);
        // Save changes
        gatePassRequestRepository.save(request);
        Approval savedApproval = approvalRepository.save(approval);

        // Send email notifications
        try {
            String senderEmail = request.getSender().getEmail();
            String requestId = String.valueOf(request.getRefNo());
            String comments = approvalDTO.getComments();
            String role = "Executive Officer";

            if (approvalDTO.getApprovalStatus() == ApprovalStatus.REJECTED) {
                // Send rejection email to sender
                emailService.sendRejectionEmail(senderEmail, requestId, role, comments, "Rejected", request);
            } else {
                // Send approval email to sender
                emailService.sendApprovalEmail(senderEmail, requestId, role, comments, request);

                // Send email to all duty officers if from another branch to Colombo Main
                String outLocationName = request.getOutLocation().getLocationName();
                String inLocationName = request.getInLocation().getLocationName();
                if (inLocationName != null && inLocationName.equalsIgnoreCase("Colombo Main") && !outLocationName.equalsIgnoreCase("Colombo Main")) {
                    List<User> dutyOfficers = userRepository.findByRole(EmployeeRole.DUTY_OFFICER);
                    for (User dutyOfficer : dutyOfficers) {
                        String dutyOfficerEmail = dutyOfficer.getEmail();
                        String dutySubject = "Gate Pass Request #" + requestId + " - Approved to Colombo Main";
                        String dutyBody = "<!DOCTYPE html>" +
                                "<html lang='en'>" +
                                "<head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><title>Gate Pass Notification</title></head>" +
                                "<body style='margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif; background-color: #f4f4f4;'>" +
                                "<table align='center' border='0' cellpadding='0' cellspacing='0' width='100%' style='max-width: 600px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); margin: 20px auto;'>" +
                                "<tr><td style='background: linear-gradient(to right, #87CEEB, #4682B4); padding: 20px; text-align: center; border-top-left-radius: 8px; border-top-right-radius: 8px;'>" +
                                "<img src='https://upload.wikimedia.org/wikipedia/commons/thumb/e/ed/SLTMobitel_Logo.svg/1280px-SLTMobitel_Logo.svg.png' alt='Gate Pass System Logo' style='max-width: 150px;'>" +
                                "<h1 style='color: #FFFFFF; font-size: 24px; margin: 10px 0;'>Gate Pass Notification</h1></td></tr>" +
                                "<tr><td style='padding: 20px;'><h2 style='color: #4682B4; font-size: 20px; margin: 0 0 10px;'>Request #" + requestId + " - Approved</h2>" +
                                "<p style='color: #1E3A8A; font-size: 16px; line-height: 1.5; margin: 0 0 20px;'>A gate pass request from " + outLocationName + " to Colombo Main has been approved.</p>" +
                                "<table border='0' cellpadding='0' cellspacing='0' width='100%' style='background-color: #E6F0FA; border-radius: 6px; padding: 15px; margin-bottom: 20px;'>" +
                                "<tr><td style='color: #1E3A8A; font-size: 16px;'><strong>Out Location:</strong> " + outLocationName + "</td></tr>" +
                                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>In Location:</strong> " + inLocationName + "</td></tr>" +
                                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Created Date:</strong> " + request.getCreatedDate().toString() + "</td></tr>" +
                                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Created By:</strong> " + (request.getSender().getFirstName() + " " + request.getSender().getLastName()) + "</td></tr>" +
                                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Executive Approved:</strong> Yes</td></tr>" +
                                "</table>" +
                                "<p style='color: #1E3A8A; font-size: 16px; line-height: 1.5; margin: 0 0 20px;'>Details: <a href='http://localhost:3000' style='color: #1E90FF; text-decoration: none; font-weight: bold;'>Gate Pass System</a>.</p></td></tr>" +
                                "<tr><td style='background-color: #f4f4f4; padding: 15px; text-align: center; border-bottom-left-radius: 8px; border-bottom-right-radius: 8px;'>" +
                                "<p style='color: #6B7280; font-size: 12px; margin: 0;'>This is an automated email from the Gate Pass System. Please do not reply directly to this email.</p>" +
                                "<p style='color: #6B7280; font-size: 12px; margin: 5px 0 0;'><a href='http://localhost:3000/support' style='color: #1E90FF; text-decoration: none;'>Contact Support</a></p></td></tr>" +
                                "</table></body></html>";
                        emailService.sendEmail(dutyOfficerEmail, dutySubject, dutyBody);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
        return savedApproval;
    }

    public List<ApprovalResponseDTO> getApprovalsByExecutive(String executiveId) {
        if (executiveId == null || executiveId.trim().isEmpty()) {
            throw new IllegalArgumentException("Executive ID cannot be null or empty");
        }
        return approvalRepository.findByApprovedBy_ServiceNumber(executiveId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ApprovalResponseDTO convertToDto(Approval approval) {
        ApprovalResponseDTO dto = new ApprovalResponseDTO();
        dto.setApprovalId(approval.getApprovalId());
        dto.setRequestId(approval.getRequest().getRefNo());
        User approvedBy = approval.getApprovedBy();
        String fullName = approvedBy.getFirstName() + " " + approvedBy.getLastName();
        dto.setApprovedByName(fullName);
        dto.setApprovalStatus(approval.getApprovalStatus());
        dto.setApprovalDate(approval.getApprovalDate());
        dto.setComments(approval.getComments());
        return dto;
    }
}