package services;

import beans.Account;
import beans.Transaction;
import daos.PersistException;
import daos.TransactionDAO;
import mysql.MySQLTransactionDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service("transactionService")
@Scope("prototype")
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private static final Logger logger = Logger.getLogger(TransactionServiceImpl.class);

    @Autowired
    TransactionDAO transactionDAO;

    @Autowired
    public TransactionServiceImpl(MySQLTransactionDAOImpl mySQLTransactionDAO) {
        this.transactionDAO = mySQLTransactionDAO;
    }

    @Autowired
    AccountService accountServicePayer;

    @Autowired
    AccountService accountServiceRecipient;

    public TransactionServiceImpl() {
    }

    @Override
    public int addTransactionService(int payerAccID, int recipientAccID, BigDecimal sum) {
        Transaction transaction = new Transaction();

        Account payerAccount = accountServicePayer.getAccountByID(payerAccID);

        Account recipientAccount = accountServiceRecipient.getAccountByID(recipientAccID);

        if (payerAccount.getCurrencyID() != recipientAccount.getCurrencyID()) {
            logger.info("Money transfer attempt from account with id " + payerAccount.getid() + " to account with id "
                    + recipientAccount.getid() + " was REJECTED due to currency mismatch");
            return 1;
        }

        if (payerAccount.getid() == recipientAccount.getid()) {
            logger.info("Money transfer attempt from account with id " + payerAccount.getid() + " to the same account " +
                    "was REJECTED");
            return 2;
        }

        transaction.setCurrencyID(payerAccount.getCurrencyID());
        transaction.setPayerID(payerAccount.getClientID());
        transaction.setPayerAccID(payerAccount.getid());

        transaction.setRecipientID(recipientAccount.getClientID());
        transaction.setRecipientAccID(recipientAccount.getid());
        transaction.setTransTypeID(3);
        transaction.setSum(sum);

        if (payerAccount.getAccTypeID() == 1 && transaction.getSum().compareTo(payerAccount.getBalance()) == 1) {
            logger.info("Money transfer attempt from account with id " + payerAccount.getid() + " to account with id "
                    + recipientAccount.getid() + " was REJECTED: not enough money on payer's account");
            return 3;
        }

        try {
            transactionDAO.addTransaction(transaction);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
        }
        return 0;
    }


    @Override
    public Transaction getTransactionByID(Integer id) {
        try {
            return transactionDAO.getByPK(id);
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }

    @Override
    public boolean updateTransaction(Transaction transaction) {     //todo maybe REMOVE because of unnecessary
        try {
            transactionDAO.update(transaction);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public boolean deleteTransaction(Transaction transaction) {     //todo maybe REMOVE because of unnecessary
        try {
            transactionDAO.delete(transaction);
            return true;
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return false;
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
        try {
            return transactionDAO.getAll();
        } catch (PersistException e) {
            logger.error("MySQL DB error", e);
            return null;
        }
    }
}