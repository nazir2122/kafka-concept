package com.kafka.user.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MotherInfo implements Serializable {
    public String motherName;
    public String motherAge;
    public String motherMobile;
    public String motherAddress;
}
