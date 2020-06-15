/* This class is used to store the id, username, and comment for each entity */

package com.google.sps.data;

public class Task {
  public long id;
  private String username;
  private String comment;

  public Task(){
    this.id = 0;
    this.username = "";
    this.comment = "";
  }

  public Task(long taskId, String user, String commentMsg){
    this.id = taskId;
    this.username = user;
    this.comment = commentMsg;
  }

  public void setUsername(String user){ this.username = user;}
  public void setComment(String commentMsg){ this.comment = commentMsg;}
  public void setId(long taskId){ this.id = taskId;}

  public long getId(){ return this.id;}
  public String getUsername(){ return this.username;}
  public String getComment(){ return this.comment;}
}