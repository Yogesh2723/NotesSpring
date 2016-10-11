package cat.tecnocampus.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by roure on 19/09/2016.
 */
public class UserLab {

    @NotNull
    @Size(min=5, max=15)
    private String username;

    @Size(min=5, max=15)
    private String name;

    @Size(min=5, max=15)
    private String secondName;

    @Size(min=5, max=15)
    private String email;

    private Map<String,NoteLab> noteLabs;

    public UserLab() {
        noteLabs = new HashMap<>();
    }

    public UserLab(String username, String name, String secondname, String email) {
        this.noteLabs = new HashMap<>();
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

    public Map<String, NoteLab> getnotes() {
        return this.noteLabs;
    }

    public List<NoteLab> getNotesAsList() {
        Collection coll = noteLabs.values();
        if (coll instanceof List) {
            return (List) coll;
        }
        else {
            return new ArrayList<>(coll);
        }
    }

    public NoteLab getNote(String title) {
        return noteLabs.get(title);
    }

    public void setnotes(Map<String, NoteLab> noteLabs) {
        this.noteLabs = noteLabs;
    }

    public NoteLab addNote(NoteLab noteLab) {
        if (!noteLabs.containsKey(noteLab.getTitle())) {
            noteLabs.put(noteLab.getTitle(),noteLab);
        } else {
            throw new RuntimeException("Note's title is repeated");
        }

        return noteLab;
    }

    public NoteLab removeNote(NoteLab noteLab) {
        getnotes().remove(noteLab);

        return noteLab;
    }

    public NoteLab removeNote(String title) {
        return noteLabs.remove(title);
    }

    public void addNotes(List<NoteLab> notes) {
        notes.forEach(n -> noteLabs.put(n.getTitle(),n));
    }

    public String toString() {
        return "Usuari: " + this.username + ", " + this.name + " " + this.secondName;
    }

    public boolean existsNote(String title) {
        return noteLabs.containsKey(title);
    }
}