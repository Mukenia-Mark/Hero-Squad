package models;

import java.util.ArrayList;
import java.util.Objects;

public class Squad {
  private String name;
  private String cause;
  private String members;
  
  public Squad(String name, String cause, String members) {
    this.name = name;
    this.cause = cause;
    this.members = members;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getCause() {
    return cause;
  }
  
  public void setCause(String cause) {
    this.cause = cause;
  }
  
  public String getMembers() {
    return members;
  }
  
  public void setMembers(String members) {
    this.members = members;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Squad squad = (Squad) o;
    return Objects.equals(name, squad.name) &&
           Objects.equals(cause, squad.cause) &&
           Objects.equals(members, squad.members);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(name, cause, members);
  }
}
