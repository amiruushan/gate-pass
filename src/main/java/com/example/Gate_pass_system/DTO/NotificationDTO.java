package com.example.Gate_pass_system.DTO;

import java.time.LocalDateTime;

public class NotificationDTO {
    private Long notificationId;
    private String recipientEmail;
    private String subject;
    private String body;
    private LocalDateTime sentDate;
    private Long requestId;
    private String outLocation;
    private String inLocation;
    private String creatorName;

    // Constructors
    public NotificationDTO() {}

    public NotificationDTO(Long notificationId, String recipientEmail, String subject, String body, LocalDateTime sentDate,
                           Long requestId, String outLocation, String inLocation, String creatorName) {
        this.notificationId = notificationId;
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.body = body;
        this.sentDate = sentDate;
        this.requestId = requestId;
        this.outLocation = outLocation;
        this.inLocation = inLocation;
        this.creatorName = creatorName;
    }

    // Getters and Setters
    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getOutLocation() {
        return outLocation;
    }

    public void setOutLocation(String outLocation) {
        this.outLocation = outLocation;
    }

    public String getInLocation() {
        return inLocation;
    }

    public void setInLocation(String inLocation) {
        this.inLocation = inLocation;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}