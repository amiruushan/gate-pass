//
//
//package com.example.Gate_pass_system.services;
//
//import com.example.Gate_pass_system.entity.Approval;
//import com.example.Gate_pass_system.entity.GatePassRequest;
//import com.example.Gate_pass_system.entity.User;
//import com.example.Gate_pass_system.repo.ApprovalRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//    @Autowired
//    private ApprovalRepository approvalRepository;
//
//    @Async
//    public void sendEmail(String to, String subject, String body) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(body, true); // true for HTML content
//
//        mailSender.send(message);
//    }
//
//    @Async
//    public void sendRejectionEmail(String to, String requestId, String role, String comments, String status, GatePassRequest request) throws MessagingException {
//        String subject = "Gate Pass Request #" + requestId + " - " + status + " by " + role;
//        String outLocation = request.getOutLocation().getLocationName();
//        String inLocation = request.getInLocation().getLocationName();
//        String createdDate = request.getCreatedDate().toString();
//        String creatorName = request.getSender().getFirstName() + " " + request.getSender().getLastName();
//        String executiveApprovedBy = getExecutiveApproverName(request.getRefNo());
//
//        String body = "<!DOCTYPE html>" +
//                "<html lang='en'>" +
//                "<head>" +
//                "<meta charset='UTF-8'>" +
//                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
//                "<title>Gate Pass Request Status</title>" +
//                "</head>" +
//                "<body style='margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif; background-color: #f4f4f4;'>" +
//                "<table align='center' border='0' cellpadding='0' cellspacing='0' width='100%' style='max-width: 600px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); margin: 20px auto;'>" +
//                "<tr>" +
//                "<td style='background: linear-gradient(to right, #87CEEB, #4682B4); padding: 20px; text-align: center; border-top-left-radius: 8px; border-top-right-radius: 8px;'>" +
//                "<img src='https://upload.wikimedia.org/wikipedia/commons/thumb/e/ed/SLTMobitel_Logo.svg/1280px-SLTMobitel_Logo.svg.png' alt='Gate Pass System Logo' style='max-width: 150px;'>" +
//                "<h1 style='color: #FFFFFF; font-size: 24px; margin: 10px 0;'>Gate Pass Request Status</h1>" +
//                "</td>" +
//                "</tr>" +
//                "<tr>" +
//                "<td style='padding: 20px;'>" +
//                "<h2 style='color: #4682B4; font-size: 20px; margin: 0 0 10px;'>Request #" + requestId + " - " + status + "</h2>" +
//                "<p style='color: #1E3A8A; font-size: 16px; line-height: 1.5; margin: 0 0 20px;'>Your gate pass request has been " + status.toLowerCase() + " by the " + role + ".</p>" +
//                "<table border='0' cellpadding='0' cellspacing='0' width='100%' style='background-color: #E6F0FA; border-radius: 6px; padding: 15px; margin-bottom: 20px;'>" +
//                "<tr><td style='color: #1E3A8A; font-size: 16px;'><strong>Out Location:</strong> " + outLocation + "</td></tr>" +
//                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>In Location:</strong> " + inLocation + "</td></tr>" +
//                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Created Date:</strong> " + createdDate + "</td></tr>" +
//                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Created By:</strong> " + creatorName + "</td></tr>" +
//                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Executive Approved:</strong> " + (executiveApprovedBy != null ? "Yes (" + executiveApprovedBy + ")" : "No") + "</td></tr>" +
//                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Duty Approved:</strong> " + (request.getStatus().equals("DutyOfficerApproved") ? "Yes" : "No") + "</td></tr>" +
//                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Comments:</strong> " + (comments != null ? comments : "No comments provided") + "</td></tr>" +
//                "</table>" +
//                "<p style='color: #1E3A8A; font-size: 16px; line-height: 1.5; margin: 0 0 20px;'>Please review the details in the <a href='http://localhost:3000' style='color: #1E90FF; text-decoration: none; font-weight: bold;'>Gate Pass System</a>.</p>" +
//                "</td>" +
//                "</tr>" +
//                "<tr>" +
//                "<td style='background-color: #f4f4f4; padding: 15px; text-align: center; border-bottom-left-radius: 8px; border-bottom-right-radius: 8px;'>" +
//                "<p style='color: #6B7280; font-size: 12px; margin: 0;'>This is an automated email from the Gate Pass System. Please do not reply directly to this email.</p>" +
//                "<p style='color: #6B7280; font-size: 12px; margin: 5px 0 0;'><a href='http://localhost:3000/support' style='color: #1E90FF; text-decoration: none;'>Contact Support</a> | <a href='http://localhost:3000/privacy' style='color: #1E90FF; text-decoration: none;'>Privacy Policy</a></p>" +
//                "</td>" +
//                "</tr>" +
//                "</table>" +
//                "</body>" +
//                "</html>";
//
//        sendEmail(to, subject, body);
//    }
//
//    @Async
//    public void sendApprovalEmail(String to, String requestId, String role, String comments, GatePassRequest request) throws MessagingException {
//        String subject = "Gate Pass Request #" + requestId + " - Approved by " + role;
//        String outLocation = request.getOutLocation().getLocationName();
//        String inLocation = request.getInLocation().getLocationName();
//        String createdDate = request.getCreatedDate().toString();
//        String creatorName = request.getSender().getFirstName() + " " + request.getSender().getLastName();
//        String executiveApprovedBy = getExecutiveApproverName(request.getRefNo());
//
//        String body = "<!DOCTYPE html>" +
//                "<html lang='en'>" +
//                "<head>" +
//                "<meta charset='UTF-8'>" +
//                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
//                "<title>Gate Pass Request Status</title>" +
//                "</head>" +
//                "<body style='margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif; background-color: #f4f4f4;'>" +
//                "<table align='center' border='0' cellpadding='0' cellspacing='0' width='100%' style='max-width: 600px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); margin: 20px auto;'>" +
//                "<tr>" +
//                "<td style='background: linear-gradient(to right, #87CEEB, #4682B4); padding: 20px; text-align: center; border-top-left-radius: 8px; border-top-right-radius: 8px;'>" +
//                "<img src='https://upload.wikimedia.org/wikipedia/commons/thumb/e/ed/SLTMobitel_Logo.svg/1280px-SLTMobitel_Logo.svg.png' alt='Gate Pass System Logo' style='max-width: 150px;'>" +
//                "<h1 style='color: #FFFFFF; font-size: 24px; margin: 10px 0;'>Gate Pass Request Status</h1>" +
//                "</td>" +
//                "</tr>" +
//                "<tr>" +
//                "<td style='padding: 20px;'>" +
//                "<h2 style='color: #4682B4; font-size: 20px; margin: 0 0 10px;'>Request #" + requestId + " - Approved</h2>" +
//                "<p style='color: #1E3A8A; font-size: 16px; line-height: 1.5; margin: 0 0 20px;'>Your gate pass request has been approved by the " + role + ".</p>" +
//                "<table border='0' cellpadding='0' cellspacing='0' width='100%' style='background-color: #E6F0FA; border-radius: 6px; padding: 15px; margin-bottom: 20px;'>" +
//                "<tr><td style='color: #1E3A8A; font-size: 16px;'><strong>Out Location:</strong> " + outLocation + "</td></tr>" +
//                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>In Location:</strong> " + inLocation + "</td></tr>" +
//                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Created Date:</strong> " + createdDate + "</td></tr>" +
//                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Created By:</strong> " + creatorName + "</td></tr>" +
//                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Executive Approved:</strong> " + (executiveApprovedBy != null ? "Yes (" + executiveApprovedBy + ")" : "No") + "</td></tr>" +
//                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Duty Approved:</strong> " + (request.getStatus().equals("DutyOfficerApproved") ? "Yes" : "No") + "</td></tr>" +
//                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Comments:</strong> " + (comments != null ? comments : "No comments provided") + "</td></tr>" +
//                "</table>" +
//                "<p style='color: #1E3A8A; font-size: 16px; line-height: 1.5; margin: 0 0 20px;'>Please review the details in the <a href='http://localhost:3000' style='color: #1E90FF; text-decoration: none; font-weight: bold;'>Gate Pass System</a>.</p>" +
//                "</td>" +
//                "</tr>" +
//                "<tr>" +
//                "<td style='background-color: #f4f4f4; padding: 15px; text-align: center; border-bottom-left-radius: 8px; border-bottom-right-radius: 8px;'>" +
//                "<p style='color: #6B7280; font-size: 12px; margin: 0;'>This is an automated email from the Gate Pass System. Please do not reply directly to this email.</p>" +
//                "<p style='color: #6B7280; font-size: 12px; margin: 5px 0 0;'><a href='http://localhost:3000/support' style='color: #1E90FF; text-decoration: none;'>Contact Support</a> | <a href='http://localhost:3000/privacy' style='color: #1E90FF; text-decoration: none;'>Privacy Policy</a></p>" +
//                "</td>" +
//                "</tr>" +
//                "</table>" +
//                "</body>" +
//                "</html>";
//
//        sendEmail(to, subject, body);
//    }
//
//    private String getExecutiveApproverName(Long requestId) {
//        return approvalRepository.findByRequest_RefNo(requestId)
//                .map(approval -> approval.getApprovedBy() != null ?
//                        (approval.getApprovedBy().getFirstName() + " " + approval.getApprovedBy().getLastName()).trim() : null)
//                .orElse(null);
//    }
//}




package com.example.Gate_pass_system.services;

import com.example.Gate_pass_system.entity.Approval;
import com.example.Gate_pass_system.entity.GatePassRequest;
import com.example.Gate_pass_system.entity.Notification;
import com.example.Gate_pass_system.repo.ApprovalRepository;
import com.example.Gate_pass_system.repo.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ApprovalRepository approvalRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Async
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // true for HTML content

        mailSender.send(message);

        // Save notification for patrol leader emails
        if (subject.contains("Approved and Heading to Your Region")) {
            Notification notification = new Notification(to, subject, body, LocalDateTime.now(), null);
            notificationRepository.save(notification);
        }
    }

    @Async
    public void sendEmailWithRequest(String to, String subject, String body, GatePassRequest request) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // true for HTML content

        mailSender.send(message);

        // Save notification for patrol leader emails
        if (subject.contains("Approved and Heading to Your Region")) {
            Notification notification = new Notification(to, subject, body, LocalDateTime.now(), request);
            notificationRepository.save(notification);
        }
    }

    @Async
    public void sendRejectionEmail(String to, String requestId, String role, String comments, String status, GatePassRequest request) throws MessagingException {
        String subject = "Gate Pass Request #" + requestId + " - " + status + " by " + role;
        String outLocation = request.getOutLocation().getLocationName();
        String inLocation = request.getInLocation().getLocationName();
        String createdDate = request.getCreatedDate().toString();
        String creatorName = request.getSender().getFirstName() + " " + request.getSender().getLastName();
        String executiveApprovedBy = getExecutiveApproverName(request.getRefNo());

        String body = "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<title>Gate Pass Request Status</title>" +
                "</head>" +
                "<body style='margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif; background-color: #f4f4f4;'>" +
                "<table align='center' border='0' cellpadding='0' cellspacing='0' width='100%' style='max-width: 600px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); margin: 20px auto;'>" +
                "<tr>" +
                "<td style='background: linear-gradient(to right, #87CEEB, #4682B4); padding: 20px; text-align: center; border-top-left-radius: 8px; border-top-right-radius: 8px;'>" +
                "<img src='https://upload.wikimedia.org/wikipedia/commons/thumb/e/ed/SLTMobitel_Logo.svg/1280px-SLTMobitel_Logo.svg.png' alt='Gate Pass System Logo' style='max-width: 150px;'>" +
                "<h1 style='color: #FFFFFF; font-size: 24px; margin: 10px 0;'>Gate Pass Request Status</h1>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 20px;'>" +
                "<h2 style='color: #4682B4; font-size: 20px; margin: 0 0 10px;'>Request #" + requestId + " - " + status + "</h2>" +
                "<p style='color: #1E3A8A; font-size: 16px; line-height: 1.5; margin: 0 0 20px;'>Your gate pass request has been " + status.toLowerCase() + " by the " + role + ".</p>" +
                "<table border='0' cellpadding='0' cellspacing='0' width='100%' style='background-color: #E6F0FA; border-radius: 6px; padding: 15px; margin-bottom: 20px;'>" +
                "<tr><td style='color: #1E3A8A; font-size: 16px;'><strong>Out Location:</strong> " + outLocation + "</td></tr>" +
                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>In Location:</strong> " + inLocation + "</td></tr>" +
                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Created Date:</strong> " + createdDate + "</td></tr>" +
                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Created By:</strong> " + creatorName + "</td></tr>" +
                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Executive Approved:</strong> " + (executiveApprovedBy != null ? "Yes (" + executiveApprovedBy + ")" : "No") + "</td></tr>" +
                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Duty Approved:</strong> " + (request.getStatus().equals("DutyOfficerApproved") ? "Yes" : "No") + "</td></tr>" +
                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Comments:</strong> " + (comments != null ? comments : "No comments provided") + "</td></tr>" +
                "</table>" +
                "<p style='color: #1E3A8A; font-size: 16px; line-height: 1.5; margin: 0 0 20px;'>Please review the details in the <a href='http://localhost:3000' style='color: #1E90FF; text-decoration: none; font-weight: bold;'>Gate Pass System</a>.</p>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='background-color: #f4f4f4; padding: 15px; text-align: center; border-bottom-left-radius: 8px; border-bottom-right-radius: 8px;'>" +
                "<p style='color: #6B7280; font-size: 12px; margin: 0;'>This is an automated email from the Gate Pass System. Please do not reply directly to this email.</p>" +
                "<p style='color: #6B7280; font-size: 12px; margin: 5px 0 0;'><a href='http://localhost:3000/support' style='color: #1E90FF; text-decoration: none;'>Contact Support</a> | <a href='http://localhost:3000/privacy' style='color: #1E90FF; text-decoration: none;'>Privacy Policy</a></p>" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</body>" +
                "</html>";

        sendEmail(to, subject, body);
    }

    @Async
    public void sendApprovalEmail(String to, String requestId, String role, String comments, GatePassRequest request) throws MessagingException {
        String subject = "Gate Pass Request #" + requestId + " - Approved by " + role;
        String outLocation = request.getOutLocation().getLocationName();
        String inLocation = request.getInLocation().getLocationName();
        String createdDate = request.getCreatedDate().toString();
        String creatorName = request.getSender().getFirstName() + " " + request.getSender().getLastName();
        String executiveApprovedBy = getExecutiveApproverName(request.getRefNo());

        String body = "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<title>Gate Pass Request Status</title>" +
                "</head>" +
                "<body style='margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif; background-color: #f4f4f4;'>" +
                "<table align='center' border='0' cellpadding='0' cellspacing='0' width='100%' style='max-width: 600px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); margin: 20px auto;'>" +
                "<tr>" +
                "<td style='background: linear-gradient(to right, #87CEEB, #4682B4); padding: 20px; text-align: center; border-top-left-radius: 8px; border-top-right-radius: 8px;'>" +
                "<img src='https://upload.wikimedia.org/wikipedia/commons/thumb/e/ed/SLTMobitel_Logo.svg/1280px-SLTMobitel_Logo.svg.png' alt='Gate Pass System Logo' style='max-width: 150px;'>" +
                "<h1 style='color: #FFFFFF; font-size: 24px; margin: 10px 0;'>Gate Pass Request Status</h1>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 20px;'>" +
                "<h2 style='color: #4682B4; font-size: 20px; margin: 0 0 10px;'>Request #" + requestId + " - Approved</h2>" +
                "<p style='color: #1E3A8A; font-size: 16px; line-height: 1.5; margin: 0 0 20px;'>Your gate pass request has been approved by the " + role + ".</p>" +
                "<table border='0' cellpadding='0' cellspacing='0' width='100%' style='background-color: #E6F0FA; border-radius: 6px; padding: 15px; margin-bottom: 20px;'>" +
                "<tr><td style='color: #1E3A8A; font-size: 16px;'><strong>Out Location:</strong> " + outLocation + "</td></tr>" +
                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>In Location:</strong> " + inLocation + "</td></tr>" +
                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Created Date:</strong> " + createdDate + "</td></tr>" +
                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Created By:</strong> " + creatorName + "</td></tr>" +
                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Executive Approved:</strong> " + (executiveApprovedBy != null ? "Yes (" + executiveApprovedBy + ")" : "No") + "</td></tr>" +
                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Duty Approved:</strong> " + (request.getStatus().equals("DutyOfficerApproved") ? "Yes" : "No") + "</td></tr>" +
                "<tr><td style='color: #1E3A8A; font-size: 16px; padding-top: 10px;'><strong>Comments:</strong> " + (comments != null ? comments : "No comments provided") + "</td></tr>" +
                "</table>" +
                "<p style='color: #1E3A8A; font-size: 16px; line-height: 1.5; margin: 0 0 20px;'>Please review the details in the <a href='http://localhost:3000' style='color: #1E90FF; text-decoration: none; font-weight: bold;'>Gate Pass System</a>.</p>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='background-color: #f4f4f4; padding: 15px; text-align: center; border-bottom-left-radius: 8px; border-bottom-right-radius: 8px;'>" +
                "<p style='color: #6B7280; font-size: 12px; margin: 0;'>This is an automated email from the Gate Pass System. Please do not reply directly to this email.</p>" +
                "<p style='color: #6B7280; font-size: 12px; margin: 5px 0 0;'><a href='http://localhost:3000/support' style='color: #1E90FF; text-decoration: none;'>Contact Support</a> | <a href='http://localhost:3000/privacy' style='color: #1E90FF; text-decoration: none;'>Privacy Policy</a></p>" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</body>" +
                "</html>";

        sendEmailWithRequest(to, subject, body, request);
    }

    private String getExecutiveApproverName(Long requestId) {
        return approvalRepository.findByRequest_RefNo(requestId)
                .map(approval -> approval.getApprovedBy() != null ?
                        (approval.getApprovedBy().getFirstName() + " " + approval.getApprovedBy().getLastName()).trim() : null)
                .orElse(null);
    }
}