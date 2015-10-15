package services;

import beans.Transaction;
import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    int addTransactionService(int payerAccID, int recipientAccID, BigDecimal sum);
    Transaction getTransactionByID(Integer id);
    boolean updateTransaction(Transaction transaction);
    boolean deleteTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
}