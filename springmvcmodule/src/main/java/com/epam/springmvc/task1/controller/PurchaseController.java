package com.epam.springmvc.task1.controller;

import com.epam.springmvc.task1.domain.Product;
import com.epam.springmvc.task1.domain.Purchase;
import com.epam.springmvc.task1.service.impl.ProductServiceImpl;
import com.epam.springmvc.task1.service.impl.PurchaseServiceImpl;
import com.epam.springmvc.task1.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static com.epam.springmvc.task1.controller.LoginCheckUtil.isUserLoggedIn;
import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/")
public class PurchaseController {

    private static final int FIRST_PAGE = 1;
    private static final int RECORDS_PER_PAGE = 4;
    private final PurchaseServiceImpl purchaseService;
    private final ProductServiceImpl productService;

    @Autowired
    public PurchaseController(PurchaseServiceImpl purchaseService, ProductServiceImpl productService) {
        this.purchaseService = purchaseService;
        this.productService = productService;
    }

    @RequestMapping(value = "/purchase/{pageId}", method = RequestMethod.GET)
    public String getListOfGoodsWithPageId(@PathVariable int pageId, HttpSession session,
                                           HttpServletResponse response, HttpServletRequest request, Model model) {
        if (isUserLoggedIn(response, request, session)) return null;
        if (pageId != FIRST_PAGE) {
            pageId = (pageId - 1) * RECORDS_PER_PAGE + 1;
        }
        List<Product> products = productService.findAllByPage(pageId, RECORDS_PER_PAGE);
        model.addAttribute("pageCount", calculatePageCount());
        model.addAttribute("products", products);
        model.addAttribute("deleteGoodForm", new Product());
        model.addAttribute("updateGoodForm", new Product());
        model.addAttribute("purchaseForm", new Purchase());
        return "purchase";
    }

    @GetMapping(value = "/purchase")
    public String getListOfGoods(HttpSession session, HttpServletResponse response,
                                 HttpServletRequest request, Model model) {
        if (isUserLoggedIn(response, request, session)) return null;
        List<Product> products = productService.findAllByPage(FIRST_PAGE, RECORDS_PER_PAGE);
        model.addAttribute("pageCount", calculatePageCount());
        model.addAttribute("products", products);
        model.addAttribute("deleteGoodForm", new Product());
        model.addAttribute("updateGoodForm", new Product());
        model.addAttribute("purchaseForm", new Purchase());
        return "purchase";
    }

    @PostMapping(value = "/addPurchase")
    public String savePurchase(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (isUserLoggedIn(response, request, session)) return null;
        String[] idGoods = request.getParameterValues("idGoods");
        List<Product> products = productService.findAll();
        List<Integer> ids = Arrays.stream(idGoods).map(Integer::parseInt).collect(toList());
        List<Product> goodsToPurchase = products.stream().filter(g -> ids.contains(g.getId())).collect(toList());
        Purchase purchase = new Purchase();
        purchase.setProducts(goodsToPurchase);
        purchase.setUserId(user.getId());
        purchaseService.add(purchase);
        return "redirect:/purchase";
    }

    private int calculatePageCount() {
        int productsCount = productService.productCount();
        if (productsCount % RECORDS_PER_PAGE != 0) {
            return productsCount / RECORDS_PER_PAGE + 1;
        } else {
            return productsCount / RECORDS_PER_PAGE;
        }
    }

}
