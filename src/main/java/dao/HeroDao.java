package dao;

import models.Hero;
import java.util.List;

public interface HeroDao {
  //List
  List<Hero> getAll();
  
  //Create
  void add(Hero hero);
  
  //Read
  Hero findById(int id);
  
  //Update
  void update(int id, String name, int age, String powers, String weakness, int squadId);
  
  //Delete
  void deleteById(int id);
  void clearAllHeroes();
  
}
