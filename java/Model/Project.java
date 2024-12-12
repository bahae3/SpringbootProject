package Model;

import DAO.SingletonConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class Project {
    private int id, duration; // duration by months (e.g.: 5 months, 17 months...)
    private String title, description;

    public Project(int id, int duration, String title, String description) {
        this.id = id;
        this.duration = duration;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                int duration = rs.getInt("duration");

                Project project = new Project(id, duration, projectName, projectDescription);
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
                int duration = rs.getInt("duration");

                project = new Project(id, duration, projectName, projectDescription);
            }

            return project;

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
}
