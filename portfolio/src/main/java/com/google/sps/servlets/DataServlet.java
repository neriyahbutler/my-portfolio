// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.util.*;
import java.io.*;
import java.io.IOException;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/data")
public class DataServlet extends HttpServlet {

  // Where comments will be stored
  ArrayList<String> commentArray = new ArrayList<String>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ArrayList<String> commentHolder = new ArrayList<String>();
    ArrayList<String> usernameHolder = new ArrayList<String>();

    Query query = new Query("Task").addSort("comment-input", SortDirection.DESCENDING);
    Query queryUsername = new Query("Task").addSort("user-name", SortDirection.DESCENDING);

    // Stores comment and usernames in database
    DatastoreService datastoreComments = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastoreComments.prepare(query);

    DatastoreService datastoreUsernames = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery resultsUsernames = datastoreUsernames.prepare(queryUsername);

    // Collects the comment and username, stores them into arrays and then writes it on "/data"
    for(Entity entity : results.asIterable()) {
        String comment = (String) entity.getProperty("comment-input");
        commentHolder.add(comment);
    }

    for(Entity entity : resultsUsernames.asIterable()){
        String username = (String) entity.getProperty("user-name");
        usernameHolder.add(username);
    }

    String json = convertToJson(commentHolder, usernameHolder);
    response.setContentType("application/json");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String comment = getComments(request);
    String username = getName(request);

    // If comment or username is blank, it will not be recorded. Otherwise it will be
    if (comment.equals("") == false && username.equals("") == false){
      Entity taskEntity = new Entity("Task");
      taskEntity.setProperty("comment-input", comment);
      taskEntity.setProperty("user-name", username);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(taskEntity);
    }
    response.sendRedirect("/index.html");
  }

  public String getComments(HttpServletRequest request) {
    String value = request.getParameter("comment-input");

    if (value == null) { return "";}
    return value;
  }

  public String getName(HttpServletRequest request){
      String value = request.getParameter("user-name");

      if (value == null){ return "";}
      return value;
  }

  public String convertToJson(ArrayList<String> comments, ArrayList<String> username){
    String json = "{";
    for (int i = 0; i < comments.size(); i++){
      if (i != 0){ json += ", ";}
        json += "\"" + username.get(i) +"\": ";
        json += "\"" + comments.get(i) + "\"";
      }
    json += "}";

    return json;
  }
}