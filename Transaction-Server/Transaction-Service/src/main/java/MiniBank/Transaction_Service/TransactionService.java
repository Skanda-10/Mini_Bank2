package MiniBank.Transaction_Service;

import MiniBank.Transaction_Service.DTO.TransactionDTO;
import MiniBank.Transaction_Service.DTO.TransactionRequestDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.exchange.transaction}")
    private String exchange;

    @Value("${rabbitmq.routingkey.transaction}")
    private String routingKey;

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(TransactionRequestDTO request) {
        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive.");
        }
        if (request.getType().equalsIgnoreCase("withdraw") && request.getAmount() > 10000) {
            throw new RuntimeException("Daily withdrawal limit exceeded.");
        }

        Transaction transaction = new Transaction();
        transaction.setUserId(request.getUserId());
        transaction.setType(request.getType());
        transaction.setAmount(request.getAmount());
        transaction.setTimestamp(new Date());

        Transaction savedTransaction = transactionRepository.save(transaction);

        processTransaction(request, savedTransaction);

        return savedTransaction;
    }

    private void processTransaction(TransactionRequestDTO request, Transaction savedTransaction) {

        TransactionDTO dto = new TransactionDTO();

        dto.setTransactionId(savedTransaction.getId());
        dto.setAccNo(request.getAccNo());
        dto.setUserId(request.getUserId());
        dto.setUserName(request.getUserName());
        dto.setUserBalance(request.getUserBalance());
        dto.setType(request.getType());
        dto.setAmount(request.getAmount());
        dto.setTimestamp(savedTransaction.getTimestamp());

        Double amountAfterTrans = request.getUserBalance();
        if (request.getType().equalsIgnoreCase("deposit")) {
            amountAfterTrans += request.getAmount();
        } else if (request.getType().equalsIgnoreCase("withdraw")) {
            amountAfterTrans -= request.getAmount();
        }
        dto.setAmountAfterTrans(amountAfterTrans);

        amqpTemplate.convertAndSend(exchange, routingKey, dto);
        System.out.println("Transaction event published to LedgerService Queue: " + dto);
    }

    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
}
