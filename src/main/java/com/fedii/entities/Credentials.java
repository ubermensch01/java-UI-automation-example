package com.fedii.entities;

/**
 * Created by sfedii on 3/23/16.
 */
public class Credentials
{
  private final String password;
  private final String username;

  public Credentials(String username,
                     String password)
  {
    this.username = username;
    this.password = password;
  }

  public String getPassword()
  {
    return password;
  }

  public String getUsername()
  {
    return username;
  }

  @Override
  public String toString() {
    return username + "/" + password;
  }
}
