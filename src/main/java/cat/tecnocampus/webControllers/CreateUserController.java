package cat.tecnocampus.webControllers;

import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.security.SecurityService;
import cat.tecnocampus.security.UserSecurityRepository;
import cat.tecnocampus.useCases.UserUseCases;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by roure on 28/10/2016.
 */
@Profile({"jdbc_auth","memory"})
@Controller
@RequestMapping("/")
public class CreateUserController {
    private UserUseCases userUseCases;
    private UserSecurityRepository userSecurityRepository;

    public CreateUserController(UserUseCases userUseCases, UserSecurityRepository userSecurityRepository) {
        this.userUseCases = userUseCases;
        this.userSecurityRepository = userSecurityRepository;
    }

    @GetMapping("createuser")
    public String createUser(Model model) {
        model.addAttribute(new UserLab());
        return "userform";
    }

    @PostMapping("createuser")
    public String processCreateUser(@Valid UserLab user, Errors errors, Model model,
                                    RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String password;

        if (errors.hasErrors()) return "userform";

        request.setAttribute("username", user.getUsername());
        password = request.getParameter("password");

        userUseCases.registerUser(user);

        //saving to authoritation database
        userSecurityRepository.save(user.getUsername(),password);

        //return "redirect:users/" + user.getUsername(); //this is dangerous because username can contain a dangerous string (sql injection)

        redirectAttributes.addAttribute("username", user.getUsername());
        redirectAttributes.addAttribute("pepe", "pepe"); // this attribute shows in the calling url as a parameter
        redirectAttributes.addFlashAttribute("userLab", user);

        return "redirect:/users/{username}"; //in this way username is scaped and dangerous chars changed
    }
}
