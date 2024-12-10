package Model;

public class SelectUserProject {
    private String projectTitle, userFirstName, userLastName;
    private int idProject, idUser, projectDuration;

    public SelectUserProject(String projectTitle, String userFirstName, String userLastName, int idProject, int idUser, int projectDuration) {
        this.projectTitle = projectTitle;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.idProject = idProject;
        this.idUser = idUser;
        this.projectDuration = projectDuration;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
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

    public int getProjectDuration() {
        return projectDuration;
    }

    public void setProjectDuration(int projectDuration) {
        this.projectDuration = projectDuration;
    }

    @Override
    public String toString() {
        return "SelectUserProject{" +
                "projectTitle='" + projectTitle + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", idProject=" + idProject +
                ", idUser=" + idUser +
                ", projectDuration=" + projectDuration +
                '}';
    }
}
