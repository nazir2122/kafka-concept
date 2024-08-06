package com.kafka.user.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserResponse implements Serializable {
    private static final long serialVersionUID = 7290554912560684845L;
	public String eventStatus;  // success, pending, fail
    public String userName;  // first + middle + last name
    public String uniqueEventId; //userid+lastName+pincode+aadhar+currentDate;
    public LocalDate eventTime;
}
