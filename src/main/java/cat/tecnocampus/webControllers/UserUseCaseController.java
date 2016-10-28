package cat.tecnocampus.webControllers;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.exceptions.UserLabUsernameAlreadyExistsException;
import cat.tecnocampus.security.SecurityService;
import cat.tecnocampus.security.UserSecurityRepository;
import cat.tecnocampus.useCases.UserUseCases;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    //same as @RequestMapping(path="notes", method= RequestMethod.GET)
    @GetMapping("notes")
    public List<NoteLab> listNotes() {
        return userUseCases.getAllNotes();
    }

/*
    @GetMapping("notes")
    public void listNotes(Model model) {
        model.addAttribute("noteLabList", userUseCases.getAllNotes());
    }
*/

/*
    @GetMapping("notes")
    public String listNotes(Model model) {
        model.addAttribute("noteLabList", userUseCases.getAllNotes());
        return "notes";
    }
*/

/*
    @GetMapping("notes")
    public Model listNotes(Model model) {
        model.addAttribute("noteLabList", userUseCases.getAllNotes());
        return model;
    }
*/

/*
    @GetMapping("notes")
    public ModelAndView listNotes(ModelAndView model) {
        model.addObject("noteLabList", userUseCases.getAllNotes());
        model.setViewName("notes");
        return model;
    }
*/

    @GetMapping("users")
    public List<UserLab> listUsers() {
        return userUseCases.getUsers();
    }

    @GetMapping("usersReqParam")
    public String showUserRequestParameter(@RequestParam String username, Model model) {
        model.addAttribute("userLab", userUseCases.getUser(username));
        return "showUser";
    }
    
    @GetMapping("users/{user}")
    public String showUser(@PathVariable("user") String user, Model model) {
        //we're going to ask to UserUseCases for a user only if the model
        //doesn't already carry one (from a redirect)
        if (!model.containsAttribute("userLab")) {
            model.addAttribute("userLab",userUseCases.getUser(user));
        }
        return "showUser";
    }

    @GetMapping("users/{user}/notes")
    public String listUserNotes(@PathVariable String user, Model model) {

        model.addAttribute("userNotesList", userUseCases.getUserNotes(user));
        model.addAttribute("user", user);

        return "userNotes";
    }

    /*
    This method is called whenever a UserLabUsernameAlreadyExistsException is signalled from any of the
    @RequestMapping annotated methods in this controller.
    We can have Advising Controllers that handle exceptions from all the controllers (no just one).
    The advising controllers must be annotated with @ControllerAdvice and have one or more methods annotated
    with @ExceptionHandler
     */
    @ExceptionHandler(UserLabUsernameAlreadyExistsException.class)
    public String handleUsernameAlreadyExists(Model model, HttpServletRequest request) {
        model.addAttribute("username", request.getAttribute("username"));
        return "error/usernameAlreadyExists";
    }
}
