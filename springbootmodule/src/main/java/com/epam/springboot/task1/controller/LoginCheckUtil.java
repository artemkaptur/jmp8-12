package com.epam.springboot.task1.controller;

import com.epam.springboot.task1.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginCheckUtil {

    static boolean userNotLoginCheck(HttpServletResponse response, HttpServletRequest request, User user) {
        if (user == null) {
            try {
                response.sendRedirect(request.getContextPath() + "/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

}
