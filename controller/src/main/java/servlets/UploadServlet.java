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

    private static String getFileExtension(String fileName) {
        String extension = null;
        int i = fileName.lastIndexOf('.');
        if (i > 0)
            extension = fileName.substring(i + 1);
        return extension;
    }

    private static final String UPLOADFOLDER = "D:\\Java\\bank-images"; //defines path to user images folder on server

    private static Map<Integer, Blob> cache = new HashMap<>(); //creating cache map

    /**
     * Method receives user image as filepart and copies it to:
     * - defined folder in the server's filesystem {@code UPLOADFOLDER} for images over than 100KB
     * (also renames output file to userid.ext)
     * - database via {@code addImageToDB} method and {@code cache} for images less than 100KB
     * {@code cache}
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">

        String fileName = getSubmittedFileName(filePart);

        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");

        //setting path as 'folder/userid.ext'
        String uploadPath = UPLOADFOLDER + "\\" + loggedUser.getid().toString() + "." + getFileExtension(fileName);

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
            logger.error("Error while creating connection", e);
        }
        MySQLUserDAOImpl mySQLUserDAO = new MySQLUserDAOImpl(connection);

        ServletContext context = request.getSession().getServletContext();
        if (context.getAttribute("cache") != null) {
            cache = (HashMap<Integer, Blob>) context.getAttribute("cache"); //retrieving cache if it exists
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