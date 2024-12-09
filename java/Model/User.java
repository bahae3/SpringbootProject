package Model;

import DAO.SingletonConn;

import java.sql.*;
import java.util.LinkedList;

public class User {
    private int id, isAdmin;  // 1: is admin. 0: is not admin
    private String firstName, lastName, email, password, role;

    public User(int id, String firstName, String lastName, String email, String password, int isAdmin, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", isAdmin=" + isAdmin +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    // Authenticating the user (log in)
    public static User userLoginAuth(String email, String password) {
        User user;
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "SELECT * FROM users WHERE email=? and password=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                int id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");
                int isAdmin = rs.getInt("isAdmin");
                String role = rs.getString("role");
                user = new User(id, firstName, lastName, userEmail, userPassword, isAdmin, role);
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Select all users
    public static LinkedList<User> getAllUsers() {
        LinkedList<User> users = new LinkedList<>();
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "SELECT * FROM users";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");
                int isAdmin = rs.getInt("isAdmin");
                String role = rs.getString("role");
                User user = new User(id, firstName, lastName, userEmail, userPassword, isAdmin, role);
                users.add(user);
            }
            return users;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Select users by skill
    public static LinkedList<User> getUsersBySkill(String skill) {
        LinkedList<User> users = new LinkedList<>();
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "SELECT * FROM users JOIN competences ON users.id = competences.idUser WHERE technology=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, skill);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");
                int isAdmin = rs.getInt("isAdmin");
                String role = rs.getString("role");
                User user = new User(id, firstName, lastName, userEmail, userPassword, isAdmin, role);
                users.add(user);
            }
            return users;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Add a new user to the db, sign up
    public static boolean addNewUser(String firstName, String lastName, String email, String password) {
        boolean res = true;
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "INSERT INTO users (firstName, lastName, email, password, isAdmin) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setInt(5, 0);

            int result = pstmt.executeUpdate();
            if (result != 1) res = false;

        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    // Update user's information (edit his profile)
    public static boolean updateUser(int idUser, String firstName, String lastName, String email, String password) {
        boolean res = true;
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "UPDATE users SET firstName=?, lastName=?, email=?, password=? WHERE id=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setInt(5, idUser);

            int result = pstmt.executeUpdate();
            if (result != 1) res = false;

        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    // When the admin assigns a role to the user
    public static boolean assignRoleToDev(int idUser, String role) {
        boolean res = true;
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "UPDATE users SET role=? WHERE id=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, role);
            pstmt.setInt(2, idUser);

            int result = pstmt.executeUpdate();
            if (result != 1) res = false;

        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }
}
