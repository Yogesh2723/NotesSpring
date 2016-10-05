package cat.tecnocampus.useCases;

import cat.tecnocampus.databaseRepositories.NoteLabRepository;
import cat.tecnocampus.databaseRepositories.UserLabRepository;
import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.NoteLabBuilder;
import cat.tecnocampus.domain.UserLab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Created by roure on 20/09/2016.
 *
 * All methods update the database
 */
@Component
public class UserUseCases {
    private NoteLabRepository noteLabRepository;
    private UserLabRepository userLabRepository;

    public UserUseCases(NoteLabRepository noteLabRepository, UserLabRepository userLabRepository) {
        this.noteLabRepository = noteLabRepository;
        this.userLabRepository = userLabRepository;
    }

    public UserLab createUser(String username, String name, String secondName, String email) {
        UserLab userLab = new UserLab(username, name, secondName, email);
        try {
            userLabRepository.save(userLab);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userLab;
    }

    //The @Transactiona annotation states that saveUser is a transaction. So ,if a unchecked exception is signaled
    // (and not cached) during the saveUser method the transaction is going to rollback
    @Transactional
    public void saveUser (UserLab user) {
          userLabRepository.save(user);
    }

    public NoteLab addUserNote(UserLab userLab, String title, String contents) {
        LocalDateTime now = LocalDateTime.now();
        NoteLab note = new NoteLabBuilder().setTitle(title).setContent(contents).
                setTime(now).setTimeEdit(now).createNoteLab();
        userLab.addNote(note);
        noteLabRepository.save(note, userLab);
        return note;
    }

    public NoteLab updateNote(NoteLab note, String title, String contents) {
        note.setTitle(title);
        note.setContent(contents);
        note.setDateEdit(LocalDateTime.now());
        noteLabRepository.updateNote(note);
        return note;
    }

    public boolean exitstTitle(String title, UserLab user) {
        return user.existNote(title);
    }
}
