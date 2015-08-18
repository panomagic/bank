package servlets;

import beans.User;
import daos.PersistException;
import mysql.MySQLDAOFactory;
import mysql.MySQLUserDAOImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UploadServlet.class);

    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("addimage.jsp").forward(request, response);
    }

    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">

        String uploadFolder = "D:\\Java\\bank-images";
        String fileName = getSubmittedFileName(filePart);
        String uploadPath = uploadFolder + "\\" + fileName;

        Path path = Paths.get(uploadPath);
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, path, StandardCopyOption.REPLACE_EXISTING);
        }

        File uploadedFile = new File(uploadPath);

        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");


        MySQLDAOFactory factory = new MySQLDAOFactory();

        Connection connection = null;   //ЗАКРЫТЬ
        try {
            connection = factory.getContext();
        } catch (PersistException e) {
            logger.error("Error while creating connection");
        }
        MySQLUserDAOImpl mySQLUserDAO = new MySQLUserDAOImpl(connection);

        try {
            mySQLUserDAO.addImageToDB(uploadedFile, loggedUser);
        } catch (SQLException e) {
            logger.error("MySQL DB error", e);
        }

        //uploadedFile.length();
        //Files.delete(Paths.get(uploadPath));
        response.sendRedirect("admin.jsp");
    }
}
