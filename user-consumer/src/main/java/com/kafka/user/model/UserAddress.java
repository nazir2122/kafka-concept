package com.kafka.user.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddress implements Serializable {
    public String HouseNo;
    public String mohalla;
    public String post;
    public String district;
    public String state;
    public String country;
    public String pinCode;
}
