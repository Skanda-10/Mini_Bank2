package MiniBank.Ledger_Server.Controller;


import MiniBank.Ledger_Server.DTO.TransactionDTO;
import MiniBank.Ledger_Server.Entity.Ledger;
import MiniBank.Ledger_Server.Service.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ledger")
public class LedgerController {

    @Autowired
    private LedgerService ledgerService;

    // Optional manual addition endpoint
    @PostMapping("/add")
    public Ledger addLedgerEntry(@RequestBody TransactionDTO dto) {
        return ledgerService.saveTransaction(dto);
    }

    @GetMapping("/user/{userId}")
    public List<Ledger> getUserLedger(@PathVariable Long userId) {
        return ledgerService.getLedgerByUserId(userId);
    }

    @GetMapping("/all")
    public List<Ledger> getAllLedgers() {
        return ledgerService.getAllLedgers();
    }
}
