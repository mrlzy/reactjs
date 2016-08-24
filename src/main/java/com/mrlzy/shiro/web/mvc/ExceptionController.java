package com.mrlzy.shiro.web.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionController {

    private static Log log = LogFactory.getLog(ExceptionController.class);





    @ExceptionHandler(NullPointerException.class)
    public String nullPointerHandler(NullPointerException e) {
        log.error(e.getMessage(),e);
        return "error_WITH_jsp";
    }


    @ExceptionHandler()
    public String exceptionHandler(Exception e) {
        log.error(e.getMessage(),e);
        return "error_WITH_jsp";
    }



}
