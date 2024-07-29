package com.kafka.user.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserName implements Serializable {
    public String firstName;
    public String middleName;
    public String lastName;
}
