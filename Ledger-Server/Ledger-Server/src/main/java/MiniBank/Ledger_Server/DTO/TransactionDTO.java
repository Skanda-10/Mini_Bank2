package MiniBank.Ledger_Server.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO implements Serializable {
    private Long transactionId;
    private Long userId;
    private Long accNo;
    private String userName;
    private Double userBalance;
    private String type;           // DEPOSIT, WITHDRAW, TRANSFER
    private double amount;
    private double amountAfterTrans;
    private Date timestamp;       // now using java.util.Date
}