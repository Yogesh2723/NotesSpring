package cat.tecnocampus.useCases;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserUseCasesTests {

	@Autowired
	private UserUseCases userUseCases;

	@Test(expected = DuplicateKeyException.class)
	public void testDuplicateUserLab() {
		UserLab u = new UserLab("jrjrjr", "pepe", "popo", "mail");
		
		IntStream.range(1, 5)
				 .mapToObj(i -> new NoteLab("hola " + i, "content " + i, LocalDateTime.now(), LocalDateTime.now()))
				 .forEach(u::addNote);
		
		userUseCases.saveUser(u);
	}
}
