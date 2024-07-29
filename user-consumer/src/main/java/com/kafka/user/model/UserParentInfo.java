package com.kafka.user.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserParentInfo implements Serializable {
    public FatherInfo fatherInfo;
    public MotherInfo motherInfo;
}
