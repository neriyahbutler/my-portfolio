/* This class is needed to pass the login/logout status of the user */

package com.google.sps.data;

public class logInfo {
  public String logUrl = "";
  public String isUserLoggedIn = "";
  public String username = "";

  public logInfo(String logUrlInput, String isUserLoggedInInput, String usernameInput){
    this.logUrl = logUrlInput;
    this.isUserLoggedIn = isUserLoggedInInput;
    this.username = usernameInput;
  }
}