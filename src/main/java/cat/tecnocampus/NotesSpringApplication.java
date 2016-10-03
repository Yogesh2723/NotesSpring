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

import java.util.ArrayList;
import java.util.List;

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
				notes.forEach(n -> System.out.println( n.getTitle()));

				userUseCases.createUser("jr", "pepe", "roure", "mail");
			}
		};
	}


}
