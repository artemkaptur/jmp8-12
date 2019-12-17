package com.epam.springboot.task1.controller;

import com.epam.springboot.task1.domain.Good;
import com.epam.springboot.task1.domain.Purchase;
import com.epam.springboot.task1.domain.User;
import com.epam.springboot.task1.service.impl.PurchaseServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

import static com.epam.springboot.task1.controller.LoginCheckUtil.userNotLoginCheck;
import static com.epam.springboot.task1.controller.UserController.CONTROLLERCALL;
import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/")
public class DeliveryController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private final PurchaseServiceImpl purchaseService;

    @Autowired
    public DeliveryController(PurchaseServiceImpl purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping(value = "/delivery")
    public String getListOfGoods(HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) {
        LOGGER.log(CONTROLLERCALL, "Call /delivery in DeliveryController");
        User user = (User) session.getAttribute("user");
        if (userNotLoginCheck(response, request, user)) return null;
        if (user.getIsAdmin()) {
            List<Purchase> usersPurchases = purchaseService.findAll();
            List<Good> goods = usersPurchases.stream()
                    .map(Purchase::getGoods)
                    .flatMap(Collection::stream)
                    .collect(toList());
            model.addAttribute("user", user);
            model.addAttribute("goods", goods);
            model.addAttribute("changeDeliveryStatusForm", new Good());
        } else {
            List<Purchase> userPurchases = purchaseService.findByUserId(user.getId());
            List<Good> goods = userPurchases.stream()
                    .map(Purchase::getGoods)
                    .flatMap(Collection::stream)
                    .collect(toList());
            model.addAttribute("goods", goods);
            model.addAttribute("user", user);
        }
        return "/delivery";
    }

}
