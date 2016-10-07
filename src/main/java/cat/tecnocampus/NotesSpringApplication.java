package cat.tecnocampus;

import cat.tecnocampus.databaseRepositories.NoteLabRepository;
import cat.tecnocampus.databaseRepositories.UserLabRepository;
import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.useCases.UserUseCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class NotesSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesSpringApplication.class, args);
	}

	@Autowired
	private UserLabRepository userLabRepository;

	@Autowired
	private NoteLabRepository noteLabRepository;

	@Autowired
	private UserUseCases userUseCases;

	@Bean
	CommandLineRunner runner() {
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				Iterable<UserLab> users = userLabRepository.findAll();
				users.forEach(u -> System.out.println(u.getUsername() + u.getName()));

				Iterable<NoteLab> notes = noteLabRepository.findAll();
				notes.forEach(n -> System.out.println( n.getTitle() + "  " + n.getDateEdit()));

				userUseCases.createUser("jr", "pepe", "roure", "mail");

				createUserTransaction();
			}

			//Creates an user with notes that have the same title. When saved a DuplicateKeyException is signalled
			//and the user is not saved in the database
			public void createUserTransaction() {
				UserLab u = new UserLab("aa", "pepe", "popo", "mail");
				try {
					for (int i = 0; i < 5; i++) {
						u.addNote(new NoteLab("hola " + i, "content " + i, LocalDateTime.now(), LocalDateTime.now()));
					}
					userUseCases.saveUser(u);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	}


}
