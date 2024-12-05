package Model;

import DAO.SingletonConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class Competence {
    private int id, idUser,yearsExperience;
    private String technology;

    public Competence(int id, int idUser, int yearsExperience, String technology) {
        this.id = id;
        this.idUser = idUser;
        this.yearsExperience = yearsExperience;
        this.technology = technology;
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

    public int getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(int yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    // In competence table, i retrieve the technologies and experience
    public static LinkedList<Competence> getCompetences(int idUser) {
        LinkedList<Competence> competences = new LinkedList<>();
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "SELECT * FROM competences JOIN users ON competences.idUser = users.id WHERE competences.idUser = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, idUser);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String technology = rs.getString("technology");
                int yearsExperience = rs.getInt("yearsExperience");
                int idUserDb = rs.getInt("idUser");
                Competence comp = new Competence(id, idUserDb, yearsExperience, technology);
                competences.add(comp);
            }
            return competences;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Inserting a new technology to the table competence
    public static boolean addNewTechnology(String technology, int yearsExperience, int idUser) {
        boolean res = true;
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "INSERT INTO competences (technology, yearsExperience, idUser) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, technology);
            pstmt.setInt(2, yearsExperience);
            pstmt.setInt(3, idUser);


            int result = pstmt.executeUpdate();
            if (result != 1) res = false;

        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    // Delete a technology
    public static boolean deleteTechnology(int idTechnology) {
        boolean res = true;
        Connection conn = SingletonConn.getConnection();
        String sqlQuery = "DELETE FROM competences WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, idTechnology);


            int result = pstmt.executeUpdate();
            if (result != 1) res = false;

        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }
}
