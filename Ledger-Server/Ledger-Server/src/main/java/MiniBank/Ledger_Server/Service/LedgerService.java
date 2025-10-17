package MiniBank.Ledger_Server.Service;


import MiniBank.Ledger_Server.DTO.TransactionDTO;
import MiniBank.Ledger_Server.Entity.Ledger;
import MiniBank.Ledger_Server.Repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LedgerService {

    @Autowired
    private LedgerRepository ledgerRepository;

    // Saves transaction to ledger (used both by REST and RabbitMQ)
    public Ledger saveTransaction(TransactionDTO dto) {
        Ledger ledger = new Ledger();
        ledger.setTransactionId(dto.getTransactionId());
        ledger.setUserId(dto.getUserId());
        ledger.setAccNo(dto.getAccNo());
        ledger.setUserName(dto.getUserName());
        ledger.setUserBalance(dto.getUserBalance());
        ledger.setType(dto.getType());
        ledger.setAmount(dto.getAmount());
        ledger.setAmountAfterTrans(dto.getAmountAfterTrans());
        ledger.setTimestamp(dto.getTimestamp());
        return ledgerRepository.save(ledger);
    }

    public List<Ledger> getLedgerByUserId(Long userId) {
        return ledgerRepository.findByUserId(userId);
    }

    public List<Ledger> getAllLedgers() {
        return ledgerRepository.findAll();
    }
}