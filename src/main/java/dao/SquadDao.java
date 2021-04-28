package dao;

import models.Hero;
import models.Squad;
import java.util.List;

public interface SquadDao {
  //List
  List<Squad> getAll();
  
  //Create
  void add (Squad squad);
  
  //Read
  Squad findById(int id);
  List<Hero> getAllHeroesBySquad(int squadId);
  
  //Update
  void update(int id, String name, String cause, String members);
  
  //Delete
  void deleteById(int id);
  void clearAllSquads();
}
