package MiniBank.Transaction_Service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long transactionId;
    private Long accNo;
    private Long userId;
    private String userName;
    private Double userBalance;
    private String type;
    private Double amount;
    private Date timestamp;
    private Double amountAfterTrans;

}