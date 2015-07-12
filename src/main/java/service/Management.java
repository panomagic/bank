package service;

import bean.*;
import dao.*;
import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Management {

    private static final String URL = "jdbc:mysql://localhost:3306/bank";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "970195";

    private static final Logger logger = Logger.getLogger(Management.class);

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
        //создаем инстанс драйвера jdbc для подключения Tomcat к MySQL
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            logger.error("Tomcat не удалось подключиться к БД", e); //e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.error("Tomcat не удалось подключиться к БД", e); //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.error("Tomcat не удалось подключиться к БД", e); //e.printStackTrace();
        }

        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);   //регистрируем драйвер
        } catch (Exception e) {
            logger.error("Не удалось загрузить класс драйвера", e); //System.err.println("Не удалось загрузить класс драйвера");
        }

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());
            if(!connection.isClosed())  //опционально
            {
                logger.info("Соединение с БД установлено"); //System.out.println("Соединение с БД установлено");
            }
        } catch (SQLException e) {
            logger.warn("Ошибка при соединении с БД MySQL", e); //e.printStackTrace();
        }

        return connection;
    }

    public static void main(String[] args) {

        /*//Тестим ввод нового клиента:
        bean.Client client = new bean.Client();
        client.setFullName("Nikolaev Nikolai"); //получили из формы сервлета
        client.setGender(Gender.MALE);

        client.setDateOfBirth(new Date("01/08/1992"));  //MM-dd-yyyy заменить с использованием Calendar
        client.setDateOfReg(new Date("01/07/2013"));    //MM-dd-yyyy

        dao.ClientDAO clientDAO = new dao.ClientDAO();
        try {
            clientDAO.addClient(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // System.out.println(client.getGender().genderAsString());
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

    /*тестим добавление счета
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
*/
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

        /*//тестим изменение счета
        bean.Account account = new bean.Account();
        account.setAccountID(14);
        account.setClientID(19);
        account.setAccTypeID(1);
        account.setCurrencyID(3);
        dao.AccountDAO accountDAO = new dao.AccountDAO();
        try {
            accountDAO.updateAccount(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

/*тестим получение счета
        bean.Account account = null;
        try {
            account = new dao.AccountDAO().getAccountByID(10);
        } catch (SQLException e) {
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

    //тестим историю транзакций
        List transactions = new ArrayList();
        try {
            transactions = new TransactionDAO().getAllTransactions();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(transactions);

        for (int i = 0; i < transactions.size(); i++) {
            System.out.println(transactions.get(i).toString());
        }

    //тестим получение пользователя
 /*       bean.User user = null;
        try {
            user = new UserDAO().getUserByUserName("admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        System.out.println(user.getRole().roleAsChar().equals("a"));*/

    }
}