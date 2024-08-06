package com.user.decision.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserResponse implements Serializable {
    public String eventStatus;  // success, pending, fail
    public String userName;  // first + middle + last name
    public String uniqueEventId; //userid+lastName+pincode+aadhar+currentDate;
    public LocalDate eventTime;
}
