package com.epam.springboot.task1.controller;

import com.epam.springboot.task1.domain.Good;
import com.epam.springboot.task1.domain.Order;
import com.epam.springboot.task1.domain.Purchase;
import com.epam.springboot.task1.domain.User;
import com.epam.springboot.task1.service.impl.OrderServiceImpl;
import com.epam.springboot.task1.service.impl.PurchaseServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
public class OrderController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private final OrderServiceImpl orderService;
    private final PurchaseServiceImpl purchaseService;

    @Autowired
    public OrderController(OrderServiceImpl orderService, PurchaseServiceImpl purchaseService) {
        this.orderService = orderService;
        this.purchaseService = purchaseService;
    }

    @GetMapping(value = "/order")
    public String getListOfGoods(HttpServletResponse response, HttpServletRequest request, Model model, HttpSession session) {
        LOGGER.log(CONTROLLERCALL, "Call /order in OrderController");
        User user = (User) session.getAttribute("user");
        if (userNotLoginCheck(response, request, user)) return null;
        List<Purchase> userPurchases = purchaseService.findByUserId(user.getId());
        List<Good> goods = userPurchases.stream()
                .map(Purchase::getGoods)
                .flatMap(Collection::stream)
                .collect(toList());
        model.addAttribute("goods", goods);
        return "/order";
    }

    @PostMapping(value = "/addOrder")
    public String saveOrder(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        LOGGER.log(CONTROLLERCALL, "Call /addOrder in OrderController");
        User user = (User) session.getAttribute("user");
        if (userNotLoginCheck(response, request, user)) return null;
        Order order = new Order();
        order.setUserId(user.getId());
        orderService.add(order);
        return "redirect:/order";
    }

}
