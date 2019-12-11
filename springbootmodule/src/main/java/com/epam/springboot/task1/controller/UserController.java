package com.epam.springboot.task1.controller;

import com.epam.springboot.task1.domain.User;
import com.epam.springboot.task1.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private final UserServiceImpl userService;
    public static final Level CONTROLLERCALL = Level.forName("CONTROLLERCALL", 350);

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public String login(Locale locale, Model model) {
        model.addAttribute("user", new User());
        LOGGER.log(CONTROLLERCALL, "Call /login in UserController");
        return "loginPage";
    }

    @GetMapping(value = "/registration")
    public ModelAndView registration(Locale locale) {
        LOGGER.log(CONTROLLERCALL, "Call /registration in UserController");
        return new ModelAndView("registrationPage", "user", new User());
    }

    @PostMapping(value = "/registration")
    public String registration(@ModelAttribute("user") @Valid User user, BindingResult result, HttpServletResponse response) {
        LOGGER.log(CONTROLLERCALL, "Call /registration POST in UserController");
        if (result.hasErrors()) {
            return "registrationPage";
        }
        if (userService.add(user)) {
            Cookie cookie = new Cookie("userLogin", user.getLogin());
            response.addCookie(cookie);
            return "redirect:/login";
        } else {
            return "redirect:/registration";
        }
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute User userForm, HttpSession session) {
        LOGGER.log(CONTROLLERCALL, "Call /login POST in UserController");
        User user = userService.findUserByLogin(userForm.getLogin());
        if (user == null) {
            return "/registration";
        } else {
            session.setAttribute("user", user);
            return "redirect:/purchase";
        }
    }

    @GetMapping(value = "/")
    public String index(Model model) {
        return "redirect:/login";
    }

}
