package com.epam.javaweb.task1;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JmpAppServlet extends HttpServlet {

    private List<String> innerState = new ArrayList<>();

    public JmpAppServlet() {
        innerState.add("first");
        innerState.add("second");
        innerState.add("third");
        innerState.add("fourth");
        innerState.add("fifth");
        innerState.add("sixth");
        innerState.add("seventh");
        innerState.add("eighth");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        long countOfViewCookie = Arrays.stream(cookies).filter(c -> c.getName().equals("viewCount")).peek(c ->
        {
            int counter = Integer.parseInt(c.getValue());
            counter += 1;
            c.setValue(String.valueOf(counter));
            response.addCookie(c);
        }).count();
        if (countOfViewCookie < 1) {
            Cookie viewCount = new Cookie("viewCount", "1");
            viewCount.setMaxAge(60 * 60 * 24);
            response.addCookie(viewCount);
        }
        request.setAttribute("InnerState", innerState);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String newString = request.getParameter("String");
        if (newString != null && !newString.isEmpty()) {
            innerState.add(newString);
            request.setAttribute("InnerState", innerState);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/jmp");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] responseParams = getParams(request).split("&");
        String stringToUpdate = responseParams[0].split("=")[1];
        String newString = responseParams[1].split("=")[1];
        if (newString != null && !newString.isEmpty()) {
            int updateIndex = innerState.indexOf(stringToUpdate);
            innerState.set(updateIndex, newString);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("{\"result\":\"updated\"}");
            out.flush();
        } else {
            response.sendRedirect(getServletContext().getContextPath() + "/jmp");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = getParams(request);
        String stringToDelete = data.split("=")[1];
        if (stringToDelete != null && !stringToDelete.isEmpty()) {
            String jsonResponce;
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            if (innerState.remove(stringToDelete)) {
                jsonResponce = "{\"result\":\"deleted\"}";
            } else {
                jsonResponce = "{\"result\":\"fail\"}";
            }
            PrintWriter out = response.getWriter();
            out.print(jsonResponce);
            out.flush();
        } else {
            response.sendRedirect(getServletContext().getContextPath() + "/jmp");
        }
    }

    private String getParams(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

}
