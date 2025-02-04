package com.kafka.user.model;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserInfo implements Serializable {
    public String userId;
    public UserName userName;
    public UserParentInfo userParentInfo;
    public int userAge;
    public List<String> userMobile;
    public Date userDOB;
    public UserProof userProof;
    public UserBankDetails userBankDetails;
    public UserAddress userAddress;
    public String routingIndicator;
}
