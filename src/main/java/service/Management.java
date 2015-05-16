package service;

import bean.*;
import dao.*;
import com.mysql.fabric.jdbc.FabricMySQLDriver;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class Management {

    private static final String URL = "jdbc:mysql://localhost:3306/bank";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "970195";

    private static String getURL() {
        return URL;
    }
    private static String getUSERNAME() {
        return USERNAME;
    }
    private static String getPASSWORD() {
        return PASSWORD;
    }

    public static Connection getDBConnection() {
        Connection connection = null;

        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);   //регистрируем драйвер
        } catch (Exception e) {
            System.err.println("Не удалось загрузить класс драйвера");
        }

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());
            if(!connection.isClosed())  //опционально
                System.out.println("Соединение с БД установлено");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {

        /*Тестим ввод нового клиента:
        bean.Client client = new bean.Client();
        client.setFullName("Pavlova Dasha"); //получили из формы сервлета
        client.setGender(Gender.FEMALE);

        client.setDateOfBirth(new Date("06/11/1985"));  //заменить с использованием Calendar
        client.setDateOfReg(new Date("19/03/2014"));

        dao.ClientDAO clientDAO = new dao.ClientDAO();
        try {
            clientDAO.addClient(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(client.getGender().genderAsString());

*/
        /*тестим удаление клиента
        bean.Client client = new bean.Client();
        client.setClientID(8); //получили из формы сервлета ФИО, СОЗДАТЬ метод по определению clientID из фамилии и даты рождения
        dao.ClientDAO clientDAO = new dao.ClientDAO();
        try {
            clientDAO.deleteClient(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/

    //тестим добавление счета
        Account account = new Account();
        account.setClientID(16);
        account.setAccTypeID(1);
        account.setCurrencyID(3);

        AccountDAO accountDAO = new AccountDAO();
        try {
            accountDAO.addAccount(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    /*тестим удаление счета
        bean.Account account = new bean.Account();
        account.setAccountID(9);
        dao.AccountDAO accountDAO = new dao.AccountDAO();
        try {
            accountDAO.deleteAccount(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/
/*тестим получение счета
        bean.Account account = null;
        try {
            account = new dao.AccountDAO().getAccountByID(10);
        } catch (SQLException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        System.out.println(account.getClientID() + ", на счету " + account.getBalance());
        */

    /*тестим добавление транзакции (перевод)
        Transaction transaction = new Transaction();
        transaction.setCurrencyID(1);
        transaction.setPayerID(7);
        transaction.setPayerAccID(11);
        transaction.setRecipientID(7);
        transaction.setRecipientAccID(8);
        transaction.setTransTypeID(3);
        transaction.setSum(new BigDecimal(2.50));
        TransactionDAO transactionDAO = new TransactionDAO();
        try {
            transactionDAO.addTransaction(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
    }
}