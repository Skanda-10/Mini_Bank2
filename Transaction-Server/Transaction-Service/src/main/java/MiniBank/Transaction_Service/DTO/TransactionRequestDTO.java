package MiniBank.Transaction_Service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO received from AccountService
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
