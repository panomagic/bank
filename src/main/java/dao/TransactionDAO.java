package dao;

import bean.*;
import service.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDAO {
    public void addTransaction(Transaction transaction) throws SQLException {
        Connection connection = Management.getDBConnection();

        PreparedStatement preparedStatementTransAct = null;
        ResultSet resultSetPayer = null;
        PreparedStatement psGetPayerAcc = null;
        PreparedStatement psUpdatePayerBalance = null;
        ResultSet resultSetRec = null;
        PreparedStatement psGetRecipientAcc = null;
        PreparedStatement psUpdateRecipientBalance = null;

        Account payerAcc = new AccountDAO().getAccountByID(transaction.getPayerAccID());
        Account recipientAcc = new AccountDAO().getAccountByID(transaction.getRecipientAccID());

        //проверка для дебитного счета плательщика (сумма транзакции не больше баланса):
        if (payerAcc.getAccTypeID() == 1 && transaction.getSum().compareTo(payerAcc.getBalance()) == 1) {
            System.out.println("Недостаточно средств на счету плательщика!");
            return;
        }

        try {
            preparedStatementTransAct = connection.prepareStatement("INSERT INTO transactions (currencies_currencyID, " +
                    "clients_payerID, accounts_payerAccID, clients_recipientID, accounts_recipientAccID, " +
                    "transactionTypes_transTypeID, sum) VALUES(?,?,?,?,?,?,?)");
            preparedStatementTransAct.setInt(1, transaction.getCurrencyID());
            preparedStatementTransAct.setInt(2, transaction.getPayerID());
            preparedStatementTransAct.setInt(3, transaction.getPayerAccID());
            preparedStatementTransAct.setInt(4, transaction.getRecipientID());
            preparedStatementTransAct.setInt(5, transaction.getRecipientAccID());
            preparedStatementTransAct.setInt(6, transaction.getTransTypeID());
            preparedStatementTransAct.setBigDecimal(7, transaction.getSum());


            //обновляем баланс счета плательщика (сам счет уже получен):
            psUpdatePayerBalance = connection.prepareStatement("UPDATE accounts SET balance = ? " +
                    "WHERE accountID = ?");
            psUpdatePayerBalance.setBigDecimal(1, payerAcc.getBalance().subtract(transaction.getSum()));
            psUpdatePayerBalance.setInt(2, transaction.getPayerAccID());

            //обновляем баланс счета получателя (сам счет уже получен):
            psUpdateRecipientBalance = connection.prepareStatement("UPDATE accounts SET balance = ? " +
                    "WHERE accountID = ?");
            psUpdateRecipientBalance.setBigDecimal(1, recipientAcc.getBalance().add(transaction.getSum()));
            psUpdateRecipientBalance.setInt(2, transaction.getRecipientAccID());

            connection.setAutoCommit(false);    //отключаем режим автоматической фиксации изменений

            preparedStatementTransAct.execute();
            psUpdatePayerBalance.execute();
            psUpdateRecipientBalance.execute();

            connection.commit();                //завершаем транзакцию, одновременно проводя перевод и изменяя баланс на счетах

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    System.err.print("Ошибка при проведении! Транзакция отменена");
                    connection.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        }
        finally {
            //проверяем корректность выполненной транзакции - ВРЕМЕННО ОСТАВЛЕНО, хотя с отключением autoCommit стало лишним
            Account payerAccNew = new AccountDAO().getAccountByID(transaction.getPayerAccID());
            BigDecimal payerBalanceNew = (payerAcc.getBalance().subtract(transaction.getSum())).setScale(2, RoundingMode.HALF_UP);
            Account recipientAccNew = new AccountDAO().getAccountByID(transaction.getRecipientAccID());
            BigDecimal recipientBalanceNew = (recipientAcc.getBalance().add(transaction.getSum())).setScale(2, RoundingMode.HALF_UP);
            if((payerBalanceNew.compareTo(payerAccNew.getBalance()) != 0) ||
                    (recipientBalanceNew.compareTo(recipientAccNew.getBalance())) != 0)
                throw new TransactionFailedException("Ошибка при выполнении транзакции! Проверьте баланс счетов");

            if(resultSetPayer != null)
                resultSetPayer.close();

            if(resultSetRec != null)
                resultSetRec.close();

            if(preparedStatementTransAct != null)
                preparedStatementTransAct.close();

            if(psGetPayerAcc != null)
                psGetPayerAcc.close();

            if(psUpdatePayerBalance != null)
                psUpdatePayerBalance.close();

            if(psGetRecipientAcc != null)
                psGetRecipientAcc.close();

            if(psUpdateRecipientBalance != null)
                psUpdateRecipientBalance.close();

            connection.setAutoCommit(true);

            if (connection != null) {
                connection.close();
                System.out.println("Соединение с БД закрыто");
            }
        }
    }
}