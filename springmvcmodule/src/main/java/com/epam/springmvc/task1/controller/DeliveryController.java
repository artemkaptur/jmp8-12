package com.epam.springmvc.task1.controller;

import com.epam.springmvc.task1.domain.Product;
import com.epam.springmvc.task1.domain.Purchase;
import com.epam.springmvc.task1.domain.User;
import com.epam.springmvc.task1.service.impl.PurchaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.epam.springmvc.task1.controller.LoginCheckUtil.isUserLoggedIn;
import static com.epam.springmvc.task1.util.ProductUtils.getProducts;

@Controller
@RequestMapping("/")
public class DeliveryController {

    private final PurchaseServiceImpl purchaseService;

    @Autowired
    public DeliveryController(PurchaseServiceImpl purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping(value = "/delivery")
    public String getListOfGoods(HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) {
        if (isUserLoggedIn(response, request, session)) {
            return null;
        }
        User user = (User) session.getAttribute("user");
        List<Purchase> purchaseList;
        List<Product> products;
        if (user.getIsAdmin()) {
            purchaseList = purchaseService.findAll();
            model.addAttribute("changeDeliveryStatusForm", new Product());
        } else {
            purchaseList = purchaseService.findPurchasesByUserId(user.getId());
        }
        products = getProducts(purchaseList);
        model.addAttribute("products", products);
        model.addAttribute("searchGoodsForm", new Product());
        return "/delivery";
    }

    @GetMapping(value = "/sortDelivery/{order}")
    public String getSortedListOfGoods(@PathVariable String order, HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) {
        if (isUserLoggedIn(response, request, session)) {
            return null;
        }
        User user = (User) session.getAttribute("user");
        List<Purchase> userPurchases = purchaseService.findSortedPurchasesByUserId(user.getId(), order);
        List<Product> products = getProducts(userPurchases);
        model.addAttribute("products", products);
        model.addAttribute("searchGoodsForm", new Product());
        return "/delivery";
    }

}
