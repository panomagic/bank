package daos;

import beans.*;
import mysql.MySQLDAOFactory;
import mysql.MySQLUserDAOImpl;

import java.io.*;
import java.sql.*;

public class Management {

    /*private static final String URL = "jdbc:mysql://localhost:3306/bank";
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
            logger.error("Tomcat is unable to connect to DB", e);
        } catch (IllegalAccessException e) {
            logger.error("Tomcat is unable to connect to DB", e);
        } catch (ClassNotFoundException e) {
            logger.error("Tomcat is unable to connect to DB", e);
        }

        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);   //регистрируем драйвер
        } catch (Exception e) {
            logger.error("Cannot load MySQL driver class", e);
        }

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());
            if(!connection.isClosed())  //опционально
            {
                logger.info("DB connection is established");
            }
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }

        return connection;
    }
*/
    public static void main(String[] args) throws PersistException {

        /*//Тестим фабрику ДАО - получение счета/всех счетов - ОК
        MySQLDAOFactory factory = new MySQLDAOFactory();
        Connection connection = factory.getContext();
        GenericDAO dao = factory.getDAO(connection, Account.class);
        List<Account> accountList;
        accountList = dao.getAll();
        for (int i = 0; i < accountList.size(); i++) {
            System.out.println(accountList.get(i).getBalance());
        }*/

        /*//Тестим фабрику ДАО - получение клиента/всех клиентов - ОК
        MySQLDAOFactory factory = new MySQLDAOFactory();
        Connection connection = factory.getContext();
        GenericDAO dao = factory.getDAO(connection, Client.class);
        List<Client> clientList1 = dao.getAll();
        for (int i = 0; i < clientList1.size(); i++) {
            System.out.println(clientList1.get(i).getid());
        }
*/
        //тестим добавление/получение юзерпика (простейший вариант метода)
        /*MySQLDAOFactory factory = new MySQLDAOFactory();
        Connection connection = factory.getContext();
        MySQLUserDAOImpl mySQLUserDAO = new MySQLUserDAOImpl(connection);

        GenericDAO daoUser;
        try {
            daoUser = factory.getDAO(connection, User.class);
        } catch (PersistException e) {
            throw new PersistException(e);
        }
        User user1 = (User) daoUser.getByPK(2);
        String imgsrc = "d:\\catbig.jpg";
        try {
            mySQLUserDAO.addImageToDB(imgsrc, user1);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


       /* String sql = "SELECT image FROM users WHERE id=?";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, 1);

            ResultSet resultSet = null;
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                File image = new File("D:\\java.jpg");
                FileOutputStream fos = new FileOutputStream(image);

                byte[] buffer = new byte[1];
                InputStream is = resultSet.getBinaryStream(1);
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
                fos.close();
                is.close();
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        /*//Тестим фабрику ДАО - добавление счета - OK
        MySQLDAOFactory factory = new MySQLDAOFactory();
        Connection connection = factory.getContext();
        GenericDAO dao = factory.getDAO(connection, Account.class);

        Account account = new Account();
        account.setAccTypeID(1);
        account.setClientID(7);
        account.setCurrencyID(1);

        dao.persist(account);
        //dao.create(); - не нужен, вместо него - persist*/

        /*//Тестим фабрику ДАО - изменение счета - OK
        MySQLDAOFactory factory = new MySQLDAOFactory();
        Connection connection = factory.getContext();
        GenericDAO dao = factory.getDAO(connection, Account.class);

        Account account = new Account();
        account.setid(33);
        account.setAccTypeID(2);
        account.setClientID(7);
        account.setCurrencyID(2);

        dao.update(account);*/

        /*//Тестим фабрику ДАО - удаление счета - OK
        MySQLDAOFactory factory = new MySQLDAOFactory();
        Connection connection = factory.getContext();
        GenericDAO dao = factory.getDAO(connection, Account.class);

        Account account = new Account();
        account.setid(33);
        dao.delete(account);
*/
        /*//Тестим ввод нового клиента:
        bean.beans.Client client = new bean.beans.Client();
        client.setFullName("Nikolaev Nikolai"); //получили из формы сервлета
        client.setGender(beans.Gender.MALE);

        client.setDateOfBirth(new Date("01/08/1992"));  //MM-dd-yyyy заменить с использованием Calendar
        client.setDateOfReg(new Date("01/07/2013"));    //MM-dd-yyyy

        dao.daos.ClientDAO clientDAO = new dao.daos.ClientDAO();
        try {
            clientDAO.addClient(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // System.out.println(client.getGender().genderAsString());
*/

        /*тестим удаление клиента
        bean.beans.Client client = new bean.beans.Client();
        client.setid(8); //получили из формы сервлета ФИО, СОЗДАТЬ метод по определению clientID из фамилии и даты рождения
        dao.daos.ClientDAO clientDAO = new dao.daos.ClientDAO();
        try {
            clientDAO.deleteClient(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/

    /*тестим добавление счета
        beans.Account account = new beans.Account();
        account.setClientID(16);
        account.setAccTypeID(1);
        account.setCurrencyID(3);

        daos.AccountDAO accountDAO = new daos.AccountDAO();
        try {
            accountDAO.addAccount(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/
    /*тестим удаление счета
        bean.beans.Account account = new bean.beans.Account();
        account.setAccountID(9);
        dao.daos.AccountDAO accountDAO = new dao.daos.AccountDAO();
        try {
            accountDAO.deleteAccount(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/

        /*//тестим изменение счета
        bean.beans.Account account = new bean.beans.Account();
        account.setAccountID(14);
        account.setClientID(19);
        account.setAccTypeID(1);
        account.setCurrencyID(3);
        dao.daos.AccountDAO accountDAO = new dao.daos.AccountDAO();
        try {
            accountDAO.updateAccount(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

/*тестим получение счета
        bean.beans.Account account = null;
        try {
            account = new dao.daos.AccountDAO().getAccountByID(10);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(account.getClientID() + ", на счету " + account.getBalance());
        */

    /*тестим добавление транзакции (перевод)
        beans.Transaction transaction = new beans.Transaction();
        transaction.setCurrencyID(1);
        transaction.setPayerID(7);
        transaction.setPayerAccID(11);
        transaction.setRecipientID(7);
        transaction.setRecipientAccID(8);
        transaction.setTransTypeID(3);
        transaction.setSum(new BigDecimal(2.50));
        daos.TransactionDAO transactionDAO = new daos.TransactionDAO();
        try {
            transactionDAO.addTransaction(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */

    /*тестим историю транзакций
        List transactions = new ArrayList();
        try {
            transactions = new daos.TransactionDAO().getAllTransactions();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(transactions);

        for (int i = 0; i < transactions.size(); i++) {
            System.out.println(transactions.get(i).toString());
        }

        */

    //тестим получение пользователя
 /*       bean.beans.User user = null;
        try {
            user = new daos.UserDAO().getUserByUserName("admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        System.out.println(user.getRole().roleAsChar().equals("a"));*/

    }
}