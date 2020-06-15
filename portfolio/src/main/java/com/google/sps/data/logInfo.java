/* This class is needed to pass the login/logout status of the user */

package com.google.sps.data;

public class logInfo {
  public String logUrl = "";
  public String isUserLoggedIn = "";
  public String username = "";

  public logInfo(){
    logUrl = "";
    isUserLoggedIn = "";
    username = "";
  }

  public void setLogUrl(String url){ this.logUrl = url;}
  public void setIsUserLoggedIn(String loginBoolean){ this.isUserLoggedIn = loginBoolean;}
  public void setUsername(String user){ this.username = user;}

  public String getLogUrl(){return this.logUrl;}
  public String getIsUserLoggedIn(){return this.isUserLoggedIn;}
  public String getUsername(){return this.username;}
}