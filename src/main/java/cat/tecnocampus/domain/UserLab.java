package cat.tecnocampus.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roure on 19/09/2016.
 */
public class UserLab {

    private String username;
    private String name;
    private String secondName;
    private String email;

    private List<NoteLab> noteLabs;

    public UserLab() {
        noteLabs = new ArrayList<NoteLab>();
    }

    public UserLab(String username, String name, String secondname, String email) {
        this.noteLabs = new ArrayList<NoteLab>();
        this.setUsername(username);
        this.setName(name);
        this.setSecondName(secondname);
        this.setEmail(email);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public List<NoteLab> getnotes() {
        return this.noteLabs;
    }

    public void setnotes(List<NoteLab> noteLabs) {
        this.noteLabs = noteLabs;
    }

    public NoteLab addNote(NoteLab noteLab) {
        getnotes().add(noteLab);

        return noteLab;
    }

    public NoteLab removeNote(NoteLab noteLab) {
        getnotes().remove(noteLab);

        return noteLab;
    }

    public void addNotes(List<NoteLab> notes) {
        this.noteLabs.addAll(notes);
    }

    public String toString() {
        return "Usuari: " + this.username + ", " + this.name + " " + this.secondName;
    }

}