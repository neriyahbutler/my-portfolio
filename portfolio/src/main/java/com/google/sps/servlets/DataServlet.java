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
<<<<<<< HEAD
 
=======

>>>>>>> master
import java.util.*;
import java.io.*;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
<<<<<<< HEAD
 
  ArrayList<String> commentArray = new ArrayList<String>();
 
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String json = convertToJson(commentArray);
 
      response.setContentType("application/json");
      response.getWriter().println(json);
  }
 
=======

  ArrayList<String> commentArray = new ArrayList<String>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String json = convertToJson(commentArray);

      response.setContentType("application/json");
      response.getWriter().println(json);
  }

>>>>>>> master
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String comment = getComments(request);
    commentArray.add(comment);
<<<<<<< HEAD
 
    response.sendRedirect("/index.html");
  }
 
  public String getComments(HttpServletRequest request) {
    String value = request.getParameter("comment-input");
 
    if (value == null) { return "";}
    return value;
  }
 
=======

    response.sendRedirect("/index.html");
  }

  public String getComments(HttpServletRequest request) {
    String value = request.getParameter("comment-input");

    if (value == null) { return "";}
    return value;
  }

>>>>>>> master
  public String convertToJson(ArrayList<String> comments){
    String json = "{";
    for (int i = 0; i < comments.size(); i++){
      if (i != 0){ json += ", ";}
        json += "\"" + Integer.toString(i) +"\": ";
        json += "\"" + comments.get(i) + "\"";
      }
    json += "}";
    return json;
  }
}