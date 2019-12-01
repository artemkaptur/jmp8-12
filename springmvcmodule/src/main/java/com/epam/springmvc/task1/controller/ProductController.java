package com.epam.springmvc.task1.controller;

import com.epam.springmvc.task1.domain.Product;
import com.epam.springmvc.task1.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class ProductController {

    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/addProduct")
    public String addGood(HttpServletResponse response, HttpServletRequest request, @ModelAttribute Product product,
                          @RequestParam("file") MultipartFile file, HttpSession session) {
        if (LoginCheckUtil.isUserLoggedIn(response, request, session)) {
            return null;
        }
        try {
            product.setImage(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        productService.add(product);

        return "redirect:/purchase";
    }

    @PostMapping(value = "/updateProduct")
    public String updateGood(HttpServletResponse response, HttpServletRequest request, @ModelAttribute Product product,
                             @RequestParam("file") MultipartFile file, HttpSession session) {
        if (LoginCheckUtil.isUserLoggedIn(response, request, session)) {
            return null;
        }
        try {
            product.setImage(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        productService.update(product);

        return "redirect:/purchase";
    }

    @PostMapping(value = "/deleteProduct")
    public String deleteGood(HttpServletResponse response, HttpServletRequest request, @ModelAttribute Product product,
                             HttpSession session) {
        if (LoginCheckUtil.isUserLoggedIn(response, request, session)) {
            return null;
        }
        productService.delete(product.getId());

        return "redirect:/purchase";
    }

    @PostMapping(value = "/findGoods")
    public String findGood(HttpServletResponse response, HttpServletRequest request,
                           @ModelAttribute("searchGoodsForm") Product product, HttpSession session, Model model) {
        if (LoginCheckUtil.isUserLoggedIn(response, request, session)) {
            return null;
        }
        if (productService.isExist(product.getName())) {
            Product searchedProduct = productService.findByName(product.getName());
            model.addAttribute("searchedProduct", searchedProduct);
        } else {
            String message = "product by " + product.getName() + " not found";
            model.addAttribute("searchResultMessage", message);
        }
        model.addAttribute("isExist", productService.isExist(product.getName()));

        return "searchResult";
    }

    @PostMapping(value = "/changeDeliveryStatus")
    public String changeDeliveryStatus(HttpServletResponse response, HttpServletRequest request, HttpSession session,
                                       @ModelAttribute Product product) {
        if (LoginCheckUtil.isUserLoggedIn(response, request, session)) {
            return null;
        }
        Product newProduct = productService.findById(product.getId());
        changeDeliveryStatus(newProduct);
        productService.update(newProduct);

        return "redirect:/delivery";
    }

    @GetMapping(value = "/downloadImage/{productId}")
    public ResponseEntity<ByteArrayResource> downloadProductImage(@PathVariable int productId) {
        Product product = productService.findById(productId);
        ByteArrayResource resource = new ByteArrayResource(product.getImage());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + product.getName())
                .contentType(MediaType.IMAGE_PNG).contentLength(product.getImage().length)
                .body(resource);
    }

    private void changeDeliveryStatus(Product newProduct) {
        newProduct.setDeliveryStatus(!newProduct.getDeliveryStatus());
    }

}
