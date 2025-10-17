package com.ash.Account_Service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Data Transfer Object for the request
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {
    private Long accNo;
    private Long userId;
    private String userName;
    private Double userBalance;
    private String type;
    private Double amount;
}
