package Model;

import DAO.SingletonConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class Project {
    private int id, idUser, rating, duration; // duration by months (e.g.: 5 months, 17 months...)
    private String title, description, feedback;

    public Project(int id, int idUser, int rating, int duration, String title, String description, String feedback) {
        this.id = id;
        this.idUser = idUser;
        this.rating = rating;
        this.duration = duration;
        this.title = title;
        this.description = description;
        this.feedback = feedback;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", rating=" + rating +
                ", duration=" + duration +
                ", titre='" + title + '\'' +
                ", description='" + description + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }

    // Selecting all the projects for admin
    public static LinkedList<Project> getProjects() {
        LinkedList<Project> projects = new LinkedList<>();
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "SELECT * FROM project";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idProject");
                String projectName = rs.getString("name");
                String projectDescription = rs.getString("description");
                int idUser = rs.getInt("idUser");
                int rating = rs.getInt("rating");
                String feedback = rs.getString("feedback");
                int duration = rs.getInt("duration");

                Project project = new Project(id, idUser, rating, duration, projectName, projectDescription, feedback);
                projects.add(project);
            }
            return projects;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Select project by id
    public static Project getProjectById(int idProject) {
        Project project = null;
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "SELECT * FROM project WHERE idProject=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, idProject);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idProject");
                String projectName = rs.getString("name");
                String projectDescription = rs.getString("description");
                int idUser = rs.getInt("idUser");
                int rating = rs.getInt("rating");
                String feedback = rs.getString("feedback");
                int duration = rs.getInt("duration");

                project = new Project(id, idUser, rating, duration, projectName, projectDescription, feedback);
            }

            return project;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Selecting all the projects assigned to a single developer (user)
    public static LinkedList<Project> getProjectsOfDev(int idUser) {
        LinkedList<Project> projects = new LinkedList<>();
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "SELECT * FROM project WHERE idUser = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, idUser);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idProject");
                String projectName = rs.getString("name");
                String projectDescription = rs.getString("description");
                int idUserDb = rs.getInt("idUser");
                int rating = rs.getInt("rating");
                String feedback = rs.getString("feedback");
                int duration = rs.getInt("duration");

                Project project = new Project(id, idUserDb, rating, duration, projectName, projectDescription, feedback);
                projects.add(project);
            }
            return projects;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Create a new project by admin
    public static boolean createProject(String title, int duratrion, String description) {
        boolean res = true;
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "INSERT INTO project (name, description, duration) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setInt(3, duratrion);

            int result = pstmt.executeUpdate();
            if (result != 1) res = false;

        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    // Delete a project by admin
    public static boolean deleteProject(int idProject) {
        boolean res = true;
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "DELETE FROM project WHERE idProject = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, idProject);

            int result = pstmt.executeUpdate();
            if (result != 1) res = false;

        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    // Assign a developer to a project by admin
    public static boolean assignDevToProject(int idProject, int idUser) {
        boolean res = true;
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "UPDATE project SET idUser=? WHERE idProject=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, idUser);
            pstmt.setInt(2, idProject);

            int result = pstmt.executeUpdate();
            if (result != 1) res = false;

        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    // Assign a feedback and a rating to a user in a project by admin
    public static boolean assignRatingAndFeedback(String feedback, int rating, int id) {
        boolean res = true;
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "UPDATE project SET feedback=?, rating=? WHERE idProject=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, feedback);
            pstmt.setInt(2, rating);
            pstmt.setInt(3, id);

            int result = pstmt.executeUpdate();
            if (result != 1) res = false;

        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }
}
