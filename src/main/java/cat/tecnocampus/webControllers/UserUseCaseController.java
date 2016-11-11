package cat.tecnocampus.webControllers;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.exceptions.UserLabNotFoundException;
import cat.tecnocampus.exceptions.UserLabUsernameAlreadyExistsException;
import cat.tecnocampus.useCases.UserUseCases;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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

/*
    //This is the most implicit it's best to use a more explicit one
    @GetMapping("notes")
    public List<NoteLab> listNotes() {
        return userUseCases.getAllNotes();
    }
*/

    //same as @RequestMapping(path="notes", method= RequestMethod.GET)
    //This is the most explicit
    @GetMapping("notes")
    public String listNotes(Model model) {
        model.addAttribute("noteLabList", userUseCases.getAllNotes());
        return "notes";
    }


/*
    @GetMapping("notes")
    public void listNotes(Model model) {
        model.addAttribute("noteLabList", userUseCases.getAllNotes());
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

/*
    //most implicit
    @GetMapping("users")
    public List<UserLab> listUsers() {
        return userUseCases.getUsers();
    }
*/

    //most explicit
    @GetMapping("users")
    public String listUsers(Model model) {
        model.addAttribute("userLabList", userUseCases.getUsers());
        return "users";
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

    @GetMapping("createuser")
    public String createUser(Model model) {
        model.addAttribute(new UserLab());
        return "userform";
    }

    @PostMapping("createuser")
    public String processCreateUser(@Valid UserLab user, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) return "userform";

        userUseCases.registerUser(user);

        //return "redirect:users/" + user.getUsername(); //this is dangerous because username can contain a dangerous string (sql injection)

        redirectAttributes.addAttribute("username", user.getUsername());
        redirectAttributes.addAttribute("pepe", "pepe"); // this attribute shows in the calling url as a parameter
        redirectAttributes.addFlashAttribute("userLab", user);

        return "redirect:users/{username}"; //in this way username is scaped and dangerous chars changed
    }

    /*
    This method is called whenever a UserLabUsernameAlreadyExistsException is signalled from any of the
    @RequestMapping annotated methods in this controller.
    We can have Advising Controllers that handle exceptions from all the controllers (no just one).
    The advising controllers must be annotated with @ControllerAdvice and have one or more methods annotated
    with @ExceptionHandler
     */
    @ExceptionHandler(UserLabUsernameAlreadyExistsException.class)
    public String handleUsernameAlreadyExists() {

        return "error/usernameAlreadyExists";
    }
}
