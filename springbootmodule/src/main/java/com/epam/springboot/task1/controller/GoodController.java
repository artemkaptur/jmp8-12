package com.epam.springboot.task1.controller;

import com.epam.springboot.task1.domain.Good;
import com.epam.springboot.task1.domain.User;
import com.epam.springboot.task1.service.impl.GoodServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.springboot.task1.controller.LoginCheckUtil.userNotLoginCheck;
import static com.epam.springboot.task1.controller.UserController.CONTROLLERCALL;

@Controller
@RequestMapping("/")
public class GoodController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private final GoodServiceImpl goodService;

    @Autowired
    public GoodController(GoodServiceImpl goodService) {
        this.goodService = goodService;
    }

    @PostMapping(value = "/addGood")
    public String addGood(HttpServletResponse response, HttpServletRequest request, @ModelAttribute Good good,
                          @RequestParam("file") MultipartFile file, HttpSession session) {
        LOGGER.log(CONTROLLERCALL, "Call /addGood in GoodController");
        User user = (User) session.getAttribute("user");
        if (userNotLoginCheck(response, request, user)) return null;
        try {
            good.setImage(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        goodService.add(good);
        return "redirect:/purchase";
    }

    @PostMapping(value = "/updateGood")
    public String updateGood(HttpServletResponse response, HttpServletRequest request,
                             @ModelAttribute("updateGoodForm") Good good, @RequestParam("file") MultipartFile file,
                             HttpSession session) {
        LOGGER.log(CONTROLLERCALL, "Call /updateGood in GoodController");
        User user = (User) session.getAttribute("user");
        if (userNotLoginCheck(response, request, user)) return null;
        try {
            good.setImage(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        goodService.update(good);
        return "redirect:/purchase";
    }

    @PostMapping(value = "/deleteGood")
    public String deleteGood(HttpServletResponse response, HttpServletRequest request,
                             @ModelAttribute Good good, HttpSession session) {
        LOGGER.log(CONTROLLERCALL, "Call /deleteGood in GoodController");
        User user = (User) session.getAttribute("user");
        if (userNotLoginCheck(response, request, user)) return null;
        goodService.delete(good.getId());
        return "redirect:/purchase";
    }

    @PostMapping(value = "/changeDeliveryStatus")
    public String changeDeliveryStatus(HttpServletResponse response, HttpServletRequest request, HttpSession session,
                                       @ModelAttribute Good good) {
        LOGGER.log(CONTROLLERCALL, "Call /changeDeliveryStatus in GoodController");
        User user = (User) session.getAttribute("user");
        if (userNotLoginCheck(response, request, user)) return null;
        Good newG = goodService.findById(good.getId());
        if (newG.getDeliveryStatus()) {
            newG.setDeliveryStatus(false);
        } else {
            newG.setDeliveryStatus(true);
        }
        goodService.update(newG);
        return "redirect:/delivery";
    }

}
