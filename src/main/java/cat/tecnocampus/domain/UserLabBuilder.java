package cat.tecnocampus.domain;

public class UserLabBuilder {
    private String username;
    private String name;
    private String secondname;
    private String email;

    public UserLabBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserLabBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserLabBuilder setSecondname(String secondname) {
        this.secondname = secondname;
        return this;
    }

    public UserLabBuilder setEmail (String email) {
        this.email = email;
        return this;
    }

    public UserLab createUserLab() {
        return new UserLab(username, name, secondname, email);
    }
}