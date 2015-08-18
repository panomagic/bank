package servlets;

import beans.User;
import daos.PersistException;
import mysql.MySQLDAOFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

@WebServlet("/image")
public class GetImageServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GetImageServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection = null;
        OutputStream os = response.getOutputStream();
        MySQLDAOFactory factory = new MySQLDAOFactory();
        try {
            connection = factory.getContext();
        } catch (PersistException e) {
            e.printStackTrace();
        }
        Blob img ;
        byte[] imgData = null ;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet  = null;
        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
        try {
            preparedStatement = connection.prepareStatement("SELECT image FROM users Where ID = ?");
            preparedStatement.setInt(1, loggedUser.getid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (resultSet.next())
            {
                img = resultSet.getBlob(1);
                imgData = img.getBytes(1, (int)img.length());
            }
            response.setContentType("image/jpeg");
            os.write(imgData);
            os.flush();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.warn("Cannot close result set");
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.warn("Cannot close prepared statement");
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                    logger.info("DB connection is closed");
                } catch (SQLException e) {
                    logger.warn("Cannot close connection", e);
                }
            }
        }
        /*OutputStream os = response.getOutputStream();
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "970195");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RandomAccessFile image = null;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String req = "SELECT image FROM users Where ID = 1" ;
        ResultSet rset  = null;
        try {
            rset = stmt.executeQuery(req);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (rset.next ())
            {
                image = (RandomAccessFile) rset.getBlob("image");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        byte[] bytes = new byte[(int)image.length()]; //добавить буфер
        image.read(bytes);
        response.setContentType("image/jpeg");
        response.getOutputStream().write(bytes);
*/
        /*try {
            rset.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

    }
}
