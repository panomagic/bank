package servlets;

import beans.User;
import daos.PersistException;
import mysql.MySQLDAOFactory;
import mysql.MySQLUserDAOImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/image")
public class GetImageServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GetImageServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OutputStream os = response.getOutputStream();
        Blob img;
        byte[] imgData = null;

        User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");

        Map<Integer, Blob> cache = new HashMap<>();
        ServletContext context = request.getSession().getServletContext();
        if (context.getAttribute("cache") != null) {    //retrieving cache
            cache = (HashMap<Integer, Blob>) context.getAttribute("cache");
        }
        System.out.println("Зашли в гетимедж, " + cache.get(loggedUser.getid()));

        if (cache.containsKey(loggedUser.getid())) {    //if cache has needed image
            System.out.println("Зашли в ИФ получения из кеша");
            img = cache.get(loggedUser.getid());  //showing image from the cache
            try {
                imgData = img.getBytes(1, (int) img.length());
            } catch (SQLException e) {
                logger.warn("MySQL DB error", e);
            }
            response.setContentType("image/jpeg");
            os.write(imgData);
            os.close();
            for (Map.Entry<Integer, Blob> e : cache.entrySet()) {
                System.out.println("Кеш во время показа картинки: " + e.getKey() + " - " + e.getValue());
            }
        }

        User user = null;
        if (!cache.containsKey(loggedUser.getid())) {   //retrieving user form DB if cache does not have needed image
            MySQLDAOFactory factory = new MySQLDAOFactory();
            Connection connection = null;
            try {
                connection = factory.getContext();
            } catch (PersistException e) {
                logger.error("Error while creating connection");
            }

            MySQLUserDAOImpl mySQLUserDAO = new MySQLUserDAOImpl(connection);
            try {
                user = mySQLUserDAO.getByPK(loggedUser.getid());
            } catch (PersistException e) {
                e.printStackTrace();
            }
        }

        //if an image is not cached and is saved in DB
        if (!cache.containsKey(loggedUser.getid()) && user.getImagepath() == null) {
            System.out.println("Зашли в ИФ, когда в кеше нет, а базе картинка до 100 кб");

            img = user.getImage();
            try {
                imgData = img.getBytes(1, (int) img.length());
            } catch (SQLException e) {
                logger.warn("MySQL DB error", e);
            }
            response.setContentType("image/jpeg");
            os.write(imgData);
            os.close();
            cache.put(user.getid(), img);
            context.setAttribute("cache", cache);   //renew cache
        } else if (!cache.containsKey(loggedUser.getid())) {    //image is in the filesystem
            System.out.println("Зашли в ЕЛС когда картинка на диске больше 100 Кб");

            InputStream is = new FileInputStream(user.getImagepath());
            System.out.println(user.getImagepath());
            os = response.getOutputStream();
            response.setContentType("image/jpeg");
            while (is.available() > 0) {
                int data = is.read();
                os.write(data);
            }
            is.close();
            os.close();
            context.setAttribute("cache", cache);
        }
        System.out.println("Вышли из гетимедж");
    }
}