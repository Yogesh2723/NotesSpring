package cat.tecnocampus.useCases;

import cat.tecnocampus.databaseRepositories.NoteLabRepository;
import cat.tecnocampus.databaseRepositories.UserLabRepository;
import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.NoteLabBuilder;
import cat.tecnocampus.domain.UserLab;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by roure on 20/09/2016.
 *
 * All methods update the database
 */
@Service
public class UserUseCases {
	
    private final NoteLabRepository noteLabRepository;
    
    private final UserLabRepository userLabRepository;

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
    public void saveUser(UserLab user) {
          userLabRepository.save(user);
    }

    public NoteLab addUserNote(UserLab userLab, String title, String contents) {
        LocalDateTime now = LocalDateTime.now();
        NoteLab note = new NoteLabBuilder().title(title).content(contents).
                time(now).timeEdit(now).createNoteLab();
        userLab.addNote(note);
        noteLabRepository.save(note, userLab);
        return note;
    }

    public NoteLab updateUserNote(UserLab userLab, NoteLab note, String title, String contents) {
        if (!title.equals(note.getTitle())) {
            userLab.removeNote(note.getTitle());
        }
        note.setTitle(title);
        note.setContent(contents);
        note.setDateEdit(LocalDateTime.now());
        userLab.addNote(note);
        noteLabRepository.updateNote(note);
        return note;
    }

    public List<NoteLab> getUserNotes(String userName) {
        return noteLabRepository.findAllFromUser(userName);
    }

    //Note that users don't have their notes with them
    public List<UserLab> getUsers() {
        return userLabRepository.findAllLazy();
    }

    public UserLab getUser(String userName) {
        return userLabRepository.findOne(userName);
    }

    public boolean existsTitle(String title, UserLab user) {
        return user.existsNote(title);
    }

    public List<NoteLab> getAllNotes() {
        return noteLabRepository.findAll();
    }
}
