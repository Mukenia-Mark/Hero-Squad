package models;

import java.util.Objects;
import java.util.ArrayList;

public class Hero {
  private String name;
  private int squadId;
  private int age;
  private String powers;
  private String weakness;
  private int id;
  
  public Hero(String name, int age, String powers, String weakness, int squadId) {
    this.name = name;
    this.squadId = squadId;
    this.age = age;
    this.powers = powers;
    this.weakness = weakness;
  }
  
  public String getName() {
    return name;
  }
  
  public int getSquadId() { return squadId; }
  
  public void setSquadId(int squadId) { this.squadId = squadId; }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public int getAge() {
    return age;
  }
  
  public void setAge(int age) {
    this.age = age;
  }
  
  public String getPowers() {
    return powers;
  }
  
  public void setPowers(String powers) {
    this.powers = powers;
  }
  
  public String getWeakness() {
    return weakness;
  }
  
  public void setWeakness(String weakness) {
    this.weakness = weakness;
  }
  
  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Hero hero = (Hero) o;
    return age == hero.age && id == hero.id && Objects.equals(name, hero.name) &&
      Objects.equals(powers, hero.powers) && Objects.equals(weakness, hero.weakness);
  }
  
  @Override public int hashCode() {
    return Objects.hash(name, age, powers, weakness, id);
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
}
