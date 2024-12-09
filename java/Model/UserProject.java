package Model;

import DAO.SingletonConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class UserProject {
    private User user;
    private Project project;

    // Constructor
    public UserProject(User user, Project project) {
        this.user = user;
        this.project = project;
    }

    // Getters and setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "UserProject{" +
                "user=" + user +
                ", project=" + project +
                '}';
    }

    // Selecting users with projects assigned to them
    public static LinkedList<UserProject> getUsersAndProjects() {
        LinkedList<UserProject> usersAndProjects = new LinkedList<>();
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "SELECT * FROM users JOIN project ON project.idUser = users.id";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // User data
                int id_user = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");
                int isAdmin = rs.getInt("isAdmin");
                String role = rs.getString("role");
                User user = new User(id_user, firstName, lastName, userEmail, userPassword, isAdmin, role);

                // Project data
                int id_project = rs.getInt("idProject");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int idUser = rs.getInt("idUser");
                int rating = rs.getInt("rating");
                String feedback = rs.getString("feedback");
                int duration = rs.getInt("duration");
                Project project = new Project(id_project, idUser, rating, duration, name, description, feedback);

                // Assign them to an object
                UserProject userProject = new UserProject(user, project);
                usersAndProjects.add(userProject);
            }
            return usersAndProjects;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
