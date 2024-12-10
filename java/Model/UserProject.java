package Model;

import DAO.SingletonConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class UserProject {
    private int id, idProject, idUser, rating;
    private String feedback;

    public UserProject(int id, int idProject, int idUser, int rating, String feedback) {
        this.id = id;
        this.idProject = idProject;
        this.idUser = idUser;
        this.rating = rating;
        this.feedback = feedback;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "UserProject{" +
                "id=" + id +
                ", idProject=" + idProject +
                ", idUser=" + idUser +
                ", rating=" + rating +
                ", feedback='" + feedback + '\'' +
                '}';
    }

    // Selecting users with projects assigned to them
    public static LinkedList<SelectUserProject> getUsersAndProjects() {
        LinkedList<SelectUserProject> usersAndProjects = new LinkedList<>();
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "SELECT * FROM projectbyuser JOIN project ON project.idProject=projectbyuser.idProject JOIN users ON users.id=projectbyuser.idProject";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int idProject = rs.getInt("idProject");
                int idUser = rs.getInt("idUser");
                int duration = rs.getInt("duration");
                String title = rs.getString("name");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");

                SelectUserProject userProject = new SelectUserProject(title, firstName, lastName, idProject, idUser, duration);
                usersAndProjects.add(userProject);
            }
            return usersAndProjects;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Selecting users with projects assigned to them by idUser and idProject
    public static SelectUserProject getUserAndProjectById(int projectId, int userId) {
        SelectUserProject userProject = null;
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "SELECT project.idProject, project.name AS projectName, project.duration, " +
                "users.firstName, users.lastName, projectbyuser.idUser " +
                "FROM projectbyuser " +
                "JOIN project ON project.idProject = projectbyuser.idProject " +
                "JOIN users ON users.id = projectbyuser.idUser " +
                "WHERE projectbyuser.idProject = ? AND projectbyuser.idUser = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, projectId);
            pstmt.setInt(2, userId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int idProject = rs.getInt("idProject");
                int idUser = rs.getInt("idUser");
                int duration = rs.getInt("duration");
                String title = rs.getString("projectName");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");

                userProject = new SelectUserProject(title, firstName, lastName, duration, idProject, idUser);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return userProject;
    }

    // Assign a developer to a project by admin
    public static boolean assignDevToProject(int idProject, int idUser) {
        boolean res = true;
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "INSERT INTO projectbyuser (idProject, idUser) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, idProject);
            pstmt.setInt(2, idUser);

            int result = pstmt.executeUpdate();
            if (result != 1) res = false;

        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    // Assign a feedback and a rating to a user in a project by admin
    public static boolean assignRatingAndFeedback(String feedback, int rating, int idProject, int idUser) {
        boolean res = true;
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "UPDATE projectbyuser SET feedback=?, rating=? WHERE idProject=? AND idUser=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, feedback);
            pstmt.setInt(2, rating);
            pstmt.setInt(3, idProject);
            pstmt.setInt(4, idUser);

            int result = pstmt.executeUpdate();
            if (result != 1) res = false;

        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }
}
