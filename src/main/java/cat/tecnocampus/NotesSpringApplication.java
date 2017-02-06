package cat.tecnocampus;

import cat.tecnocampus.databaseRepositories.NoteLabRepository;
import cat.tecnocampus.databaseRepositories.UserLabRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication
public class NotesSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserLabRepository userLabRepository, NoteLabRepository noteLabRepository) {
		return (String ... args) -> {
				userLabRepository.findAll()
								 .forEach(System.out::println);

				noteLabRepository.findAll()
								 .forEach(n -> System.out.println( n.getTitle()));
			};
	}
	
}
