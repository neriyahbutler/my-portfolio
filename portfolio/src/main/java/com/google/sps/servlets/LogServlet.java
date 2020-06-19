package com.google.sps.servlets;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.sps.data.Task;
import com.google.sps.data.logInfo;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.gson.Gson;




@WebServlet("/log")
public class LogServlet extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json");

        UserService userService = UserServiceFactory.getUserService();
        String username = "";
        Gson gson = new Gson();

        /* If user is not logged in, they'll be directed to a login page.
        Otherwise, they'll be directed to a logout page */
        if(!userService.isUserLoggedIn()){
          String loginUrl = userService.createLoginURL("/index.html");

          logInfo userLogInfo = new logInfo(loginUrl, Boolean.toString((userService.isUserLoggedIn())), username);

          String json = gson.toJson(userLogInfo);
          response.getWriter().println(json);
          return;
        }

        String logoutUrl = userService.createLogoutURL("/index.html");
        username = request.getUserPrincipal().getName();
        username = (username.split("@"))[0];
        
        logInfo userLogInfo = new logInfo(logoutUrl, Boolean.toString((userService.isUserLoggedIn())), username);

        String json = gson.toJson(userLogInfo);
        response.getWriter().println(json);
    }
}