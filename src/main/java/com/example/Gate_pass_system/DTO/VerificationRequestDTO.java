//package com.example.Gate_pass_system.DTO;
//
//import com.example.Gate_pass_system.entity.VerificationStatus;
//
//// Request DTO for submitting verification
//public class VerificationRequestDTO {
//    private Long requestId;
//    private String verifiedBy;
//    private VerificationStatus verificationStatus;
//    private String comments;
//
//    // Getters and Setters
//
//    public Long getRequestId() {
//        return requestId;
//    }
//
//    public void setRequestId(Long requestId) {
//        this.requestId = requestId;
//    }
//
//    public String getVerifiedBy() {
//        return verifiedBy;
//    }
//
//    public void setVerifiedBy(String verifiedBy) {
//        this.verifiedBy = verifiedBy;
//    }
//
//    public VerificationStatus getVerificationStatus() {
//        return verificationStatus;
//    }
//
//    public void setVerificationStatus(VerificationStatus verificationStatus) {
//        this.verificationStatus = verificationStatus;
//    }
//
//    public String getComments() {
//        return comments;
//    }
//
//    public void setComments(String comments) {
//        this.comments = comments;
//    }
//}



package com.example.Gate_pass_system.DTO;

import com.example.Gate_pass_system.entity.VerificationStatus;

public class VerificationRequestDTO {
    private Long requestId;
    private String verifiedBy;
    private String checkedBy;
    private VerificationStatus verificationStatus;
    private String comments;

    // Getters and Setters

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public String getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(String checkedBy) {
        this.checkedBy = checkedBy;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}


