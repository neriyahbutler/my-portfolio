/* This class is used to store the id, username, and comment for each entity */

package com.google.sps.data;

public class Task {
  public long id;
  private String username;
  private String comment;

  public Task(long idInput, String usernameInput, String commentInput){
    this.id = idInput;
    this.username = usernameInput;
    this.comment = commentInput;
  }
}