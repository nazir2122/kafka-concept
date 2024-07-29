package com.kafka.user.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserBankDetails implements Serializable {
    public String accountHolderName;
    public String accountNo;
    public String ifscCode;
    public String branchCode;
}
