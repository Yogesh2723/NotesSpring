package cat.tecnocampus.webControllers;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.useCases.UserUseCases;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by roure on 05/10/2016.
 */
@Controller
@RequestMapping("/")
public class UserUseCaseController {
    private UserUseCases userUseCases;

    public UserUseCaseController(UserUseCases userUseCases) {
        this.userUseCases = userUseCases;
    }

    //saem as @RequestMapping(path="notes", method= RequestMethod.GET)
    @GetMapping("notes")
    public Iterable<NoteLab> listNotes() {
        return userUseCases.getAllNotes();
    }
}
