package cat.tecnocampus.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by roure on 12/10/2016.
 */
public class UserLabUsernameAlreadyExistsException extends RuntimeException {
    public UserLabUsernameAlreadyExistsException(String s) {
        super(s);
    }
}
