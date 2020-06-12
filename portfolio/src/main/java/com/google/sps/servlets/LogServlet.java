package com.google.sps.servlets;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import com.google.gson.Gson;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/log")
public class LogServlet extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json");
        ArrayList<String> logInfo = new ArrayList<String>();
        UserService userService = UserServiceFactory.getUserService();
        Gson gson = new Gson();

        /* If user is not logged in, they'll be directed to a login page.
        Otherwise, they'll be directed to a logout page */
        if(!userService.isUserLoggedIn()){
          String loginUrl = userService.createLoginURL("/index.html");

          logInfo.add(loginUrl);
          logInfo.add(Boolean.toString((userService.isUserLoggedIn())));

          String json = gson.toJson(logInfo);
          response.getWriter().println(json);
          return;
        }

        String logoutUrl = userService.createLogoutURL("/index.html");

        logInfo.add(logoutUrl);
        logInfo.add(Boolean.toString(userService.isUserLoggedIn()));

        String json = gson.toJson(logInfo);
        response.getWriter().println(json);
    }
}