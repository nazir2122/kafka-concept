package com.kafka.user.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserProof implements Serializable {
    public String aadharNo;
    public String panNo;
    public String passportNo;
    public String voterIdNo;
    public String drivingLicenseNo;
}
