package com.epam.springmvc.task1.controller.exceptionHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AppExceptionHandler {

    private static final String DEFAULT_ERROR_VIEW = "error";
    private Logger logger = LogManager.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
        logger.error(e.getMessage(), e, e.getStackTrace());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e.getCause());
        modelAndView.addObject("url", req.getRequestURL());
        modelAndView.setViewName(DEFAULT_ERROR_VIEW);
        return modelAndView;
    }

}
