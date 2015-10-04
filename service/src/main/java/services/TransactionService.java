package services;

import beans.Transaction;
import java.util.List;

public interface TransactionService {
    void addTransactionService(Transaction transaction);
    Transaction getTransactionByID(Integer id);
    boolean updateTransaction(Transaction transaction);
    boolean deleteTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
}