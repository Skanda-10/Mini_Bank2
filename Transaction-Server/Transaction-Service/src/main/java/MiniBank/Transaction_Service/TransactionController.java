package MiniBank.Transaction_Service;

import MiniBank.Transaction_Service.DTO.TransactionRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // This is the API endpoint that the AccountService will call.
    // It receives the full TransactionRequestDTO object in the request body.
    @PostMapping("/process")
    public Transaction processTransaction(@RequestBody TransactionRequestDTO request) {
        System.out.println("⚡ Received transaction request in instance: " + System.getenv("SERVER_PORT"));
        return transactionService.createTransaction(request);
    }

    // This is the endpoint for retrieving a user's transactions.
    @GetMapping("/user/{userId}")
    public List<Transaction> getUserTransactions(@PathVariable Long userId) {
        return transactionService.getTransactionsByUserId(userId);
    }
}