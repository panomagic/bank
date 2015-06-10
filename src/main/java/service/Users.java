package service;

public class Users {
    private String userName;

    private String password;

    public Users() {}

    public Users(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    Users admin = new Users("admin", "111");
    Users client = new Users("client", "222");

    public boolean isAdmin(Users user) {
        return (admin.getUserName().equals(user.getUserName()) && admin.getPassword().equals(user.getPassword()));
    }

    public boolean isClient(Users user) {
        return (client.getUserName().equals(user.getUserName()) && client.getPassword().equals(user.getPassword()));
    }
}
