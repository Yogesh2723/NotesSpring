package cat.tecnocampus.domain;

public class UserLabBuilder {
    private String username;
    private String name;
    private String secondname;
    private String email;
    private String password;

    public UserLabBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserLabBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserLabBuilder secondname(String secondname) {
        this.secondname = secondname;
        return this;
    }

    public UserLabBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserLabBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserLab createUserLab() {
        UserLab u = new UserLab(username, name, secondname, email);
        u.setPassword(password);
        return u;
    }
}