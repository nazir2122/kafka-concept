package com.kafka.user.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class FatherInfo implements Serializable {

    public String fatherName;
    public String fatherAge;
    public String fatherMobile;
    public String fatherAddress;
}
