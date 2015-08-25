package servlets;

import beans.Role;
import beans.User;
import daos.PersistException;
import mysql.MySQLDAOFactory;
import mysql.MySQLUserDAOImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
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
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UploadServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
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

    private String getFileExtension(String fileName) {
        String extension = null;
        int i = fileName.lastIndexOf('.');
        if (i > 0)
            extension = fileName.substring(i + 1);
        return extension;
    }

    private static Map<Integer, Blob> cache = new HashMap<>(); //creating cache map


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">

        String uploadFolder = "D:\\Java\\bank-images";  //defines path to user images folder
        String fileName = getSubmittedFileName(filePart);

        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");

        //setting path as 'folder/userid.ext'
        String uploadPath = uploadFolder + "\\" + loggedUser.getid().toString() + "." + getFileExtension(fileName);

        Path path = Paths.get(uploadPath);
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, path, StandardCopyOption.REPLACE_EXISTING);
        }

        File uploadedFile = new File(uploadPath);

        MySQLDAOFactory factory = new MySQLDAOFactory();
        Connection connection = null;
        try {
            connection = factory.getContext();
        } catch (PersistException e) {
            logger.error("Error while creating connection");
        }
        MySQLUserDAOImpl mySQLUserDAO = new MySQLUserDAOImpl(connection);

        //retrieving cache if it exists
        ServletContext context = request.getSession().getServletContext();
        if (context.getAttribute("cache") != null) {
            cache = (HashMap<Integer, Blob>) context.getAttribute("cache");
        }

        try {
            mySQLUserDAO.addImageToDB(uploadedFile, loggedUser);
            User user = mySQLUserDAO.getByPK(loggedUser.getid());   //retrieving new user with updated image
            request.getSession().setAttribute("LOGGED_USER", user); //saving new user in session instead of older one

            if (user.getImagepath() == null)    //if an image is saved in DB (<100 kb)
                cache.put(loggedUser.getid(), mySQLUserDAO.getImageFromDB(loggedUser)); //saving image from DB to the cache
            else {                              //otherwise remove old image from the cache to avoid conflicts
                cache.remove(loggedUser.getid());
            }
            for (Map.Entry<Integer, Blob> e : cache.entrySet()) {
                logger.debug("Cache after image uploading: " + e.getKey() + " - " + e.getValue());
            }
        } catch (PersistException | SQLException e) {
            logger.error("MySQL DB error", e);
        } finally {
            if (connection != null)
                try {
                    connection.close();
                    logger.info("DB connection is closed");
                } catch (SQLException e) {
                    logger.warn("Cannot close connection", e);
                }
        }

        context.setAttribute("cache", cache);    //saving cache Map in order to have access to it from other servlets by all users

        if (Role.ADMINISTRATOR == loggedUser.getRole())
            response.sendRedirect("/admin");
        else if (Role.CLIENT == loggedUser.getRole())
            response.sendRedirect("/clientinfo");
    }
}