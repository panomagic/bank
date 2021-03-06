package servlets;

import beans.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.UserService;
import javax.servlet.ServletConfig;
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
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/image")
@Controller
public class GetImageServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GetImageServlet.class);

    @Autowired
    UserService userService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    private static void showImageFromFileSystem(User user, HttpServletResponse response) throws IOException {
        InputStream is = new FileInputStream(user.getImagepath());
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("image/jpeg");
        while (is.available() > 0) {
            int data = is.read();
            outputStream.write(data);
        }
        is.close();
        outputStream.close();
        logger.debug("Showing image more than 100 KB from the filesystem");
    }

    private static Map<Integer, Blob> retrieveCache(ServletContext context) {
        Map<Integer, Blob> cacheFromContext = new HashMap<>();
        if (context.getAttribute("cache") != null)
            cacheFromContext = (HashMap<Integer, Blob>) context.getAttribute("cache");
        return cacheFromContext;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OutputStream os = response.getOutputStream();
        Blob img;
        byte[] imgData = null;

        User loggedUser = null;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<User> userList = userService.getAllUsers();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserName().equals(userDetails.getUsername()))
                loggedUser = userList.get(i);
        }

        Map<Integer, Blob> cache;

        ServletContext context = request.getServletContext();

        cache = retrieveCache(context);     //retrieving cache

        if (cache.containsKey(loggedUser.getid())) {    //if cache has needed image
            img = cache.get(loggedUser.getid());  //showing image from the cache
            try {
                imgData = img.getBytes(1, (int) img.length());
            } catch (SQLException e) {
                logger.error("MySQL DB error", e);
            }
            response.setContentType("image/jpeg");
            os.write(imgData);
            os.close();
            logger.debug("Showing image from the cache");
            for (Map.Entry<Integer, Blob> e : cache.entrySet()) {
                logger.debug("Cache while showing an image: " + e.getKey() + " - " + e.getValue());
            }
        }

        User user = new User();

        if (!cache.containsKey(loggedUser.getid())) {   //retrieving user from DB if cache does not have needed image
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getid() == loggedUser.getid())
                    user = loggedUser;
            }
        }

        if (!cache.containsKey(loggedUser.getid()) && user.getImagepath() == null) {
            img = user.getImage();
            try {
                imgData = img.getBytes(1, (int) img.length());
            } catch (SQLException e) {
                logger.error("MySQL DB error", e);
            }
            response.setContentType("image/jpeg");
            os.write(imgData);
            os.close();
            logger.debug("Showing image less then 100 KB from DB");
            cache.put(user.getid(), img);
            context.setAttribute("cache", cache);   //renew cache
        } else if (!cache.containsKey(loggedUser.getid())) {    //image is in the filesystem
            showImageFromFileSystem(loggedUser, response);
            context.setAttribute("cache", cache);
        }
    }
}