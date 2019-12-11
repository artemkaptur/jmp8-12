package com.epam.springboot.task1.controller;

import com.epam.springboot.task1.domain.Good;
import com.epam.springboot.task1.domain.Purchase;
import com.epam.springboot.task1.domain.User;
import com.epam.springboot.task1.service.impl.GoodServiceImpl;
import com.epam.springboot.task1.service.impl.PurchaseServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.epam.springboot.task1.controller.LoginCheckUtil.userNotLoginCheck;
import static com.epam.springboot.task1.controller.UserController.CONTROLLERCALL;
import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/")
public class PurchaseController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private static final int FIRST_PAGE = 1;
    private final PurchaseServiceImpl purchaseService;
    private final GoodServiceImpl goodService;

    @Autowired
    public PurchaseController(PurchaseServiceImpl purchaseService, GoodServiceImpl goodService) {
        this.purchaseService = purchaseService;
        this.goodService = goodService;
    }

    @RequestMapping(value = "/purchase/{page_id}", method = GET)
    public ModelAndView getListOfGoodsWithPageId(@PathVariable int page_id, Locale locale, HttpSession session,
                                                 HttpServletResponse response, HttpServletRequest request) {
        LOGGER.log(CONTROLLERCALL, "Call /purchase/{page_id} in PurchaseController");
        User user = (User) session.getAttribute("user");
        if (userNotLoginCheck(response, request, user)) return null;
        int total = 4;
        if (page_id != 1) {
            page_id = (page_id - 1) * total + 1;
        }
        List<Good> goods = goodService.findAllByPage(page_id, total);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.addObject("goods", goods);
        modelAndView.addObject("deleteGoodForm", new Good());
        modelAndView.addObject("updateGoodForm", new Good());
        modelAndView.addObject("purchaseForm", new Purchase());
        modelAndView.setViewName("purchase");
        return modelAndView;
    }

    @GetMapping(value = "/purchase")
    public ModelAndView getListOfGoods(Locale locale, HttpSession session, HttpServletResponse response,
                                       HttpServletRequest request) {
        LOGGER.log(CONTROLLERCALL, "Call /getListOfGoods in PurchaseController");
        User user = (User) session.getAttribute("user");
        if (userNotLoginCheck(response, request, user)) return null;
        int total = 4;
        List<Good> goods = goodService.findAllByPage(FIRST_PAGE, total);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.addObject("goods", goods);
        modelAndView.addObject("deleteGoodForm", new Good());
        modelAndView.addObject("updateGoodForm", new Good());
        modelAndView.addObject("purchaseForm", new Purchase());
        return modelAndView;
    }

    @PostMapping(value = "/addPurchase")
    public String savePurchase(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        LOGGER.log(CONTROLLERCALL, "Call /addPurchase in PurchaseController");
        User user = (User) session.getAttribute("user");
        if (userNotLoginCheck(response, request, user)) return null;
        String[] idGoods = request.getParameterValues("idGoods");
        List<Good> goods = goodService.findAll();
        List<Integer> ids = Arrays.stream(idGoods).map(Integer::parseInt).collect(toList());
        List<Good> goodsToPurchase = goods.stream().filter(g -> ids.contains(g.getId())).collect(toList());
        Purchase purchase = new Purchase();
        purchase.setGoods(goodsToPurchase);
        purchase.setUserId(user.getId());
        purchaseService.add(purchase);
        return "redirect:/purchase";
    }

}
