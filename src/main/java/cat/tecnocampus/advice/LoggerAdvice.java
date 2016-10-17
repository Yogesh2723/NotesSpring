package cat.tecnocampus.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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

    //A pointcut that matches one single method
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

    //A pointctu that matches all methods having the word "Notes" in any position of methods' name
    @Pointcut("execution(* cat.tecnocampus.webControllers.UserUseCaseController.*Notes*(..))")
    public void pointcutNotes() {}

    @Before("pointcutNotes()")
    public void beforeDealingNotes() {
        logger.info("Going to deal with notes");
    }

    @Around("execution(* *.showUserRequestParameter(..))")
    public String dealRequestParam(ProceedingJoinPoint jp) {

        try {
            logger.info("Before showing user (request parameter)");
            String res = (String)jp.proceed();
            logger.info("After showing user (request parameter)");
            return res;
        } catch (Throwable throwable) {
            logger.info("Something went wrong (request parameter)");
            throwable.printStackTrace();
            return "error";
        }
    }


}
