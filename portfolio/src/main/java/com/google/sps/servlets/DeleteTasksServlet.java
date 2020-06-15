import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import com.google.gson.Gson;

import com.google.sps.data.Task;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@WebServlet("/delete-tasks")
public class DeleteTasksServlet extends HttpServlet{
    
    @Override
    // Uses the task key to search for and delete specified entity
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        long id = Long.parseLong(request.getParameter("id"));

        Key taskKey = KeyFactory.createKey("Task", id);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        datastore.delete(taskKey);
    }
}