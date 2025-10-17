package MiniBank.Ledger_Server.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ledger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ledger_Id;

    private Long transactionId;
    private Long userId;

    @Column(name = "accNo")
    private Long accNo;
    private String userName;
    private Double userBalance;
    private String type;
    private double amount;
    private double amountAfterTrans;
    private Date timestamp;   // now matching DTO
}