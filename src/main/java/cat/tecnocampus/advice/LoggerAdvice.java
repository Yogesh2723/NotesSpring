package cat.tecnocampus.advice;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by roure on 17/10/2016.
 */
@Aspect
@Component
public class LoggerAdvice {
    Logger logger = LoggerFactory.getLogger(LoggerAdvice.class);

    @Pointcut("execution(* cat.tecnocampus.webControllers.UserUseCaseController.listUsers(..))")
    public void pointcutListUsers() {}

    @Before("pointcutListUsers()")
    public void beforeListUsers() {
        logger.info("Going to list all users");
    }

    @After("pointcutListUsers()")
    public void afterListUsers() {
        logger.info("Already listed all users");
    }
}
