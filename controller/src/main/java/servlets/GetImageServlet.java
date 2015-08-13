package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.sql.*;

@WebServlet("/image")
public class GetImageServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        OutputStream os = response.getOutputStream();
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

        try {
            rset.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
