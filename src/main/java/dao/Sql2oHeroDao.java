package dao;

import models.Hero;
import org.sql2o.*;
import java.util.List;

public class Sql2oHeroDao implements HeroDao {
  
  private final Sql2o sql2o;
  
  public Sql2oHeroDao(Sql2o sql2o){
    this.sql2o = sql2o;
  }
  
  @Override
  public List<Hero> getAll() {
    try(Connection con = sql2o.open()) {
      return con.createQuery("SELECT * FROM heroes")
        .executeAndFetch(Hero.class);
    }
  }
  
  @Override
  public void add(Hero hero) {
    String sql= "INSERT INTO heroes (name, age, powers, weakness, squadId) VALUES (:name, :age, " +
                ":powers, :weakness, :squadId)";
    try(Connection con = sql2o.open()){
      int id = (int) con.createQuery(sql,true)
        .bind(hero)
        .executeUpdate()
        .getKey();
      hero.setId(id);
    } catch (Sql2oException ex) {
      System.out.println(ex);
    }
  }
  
  @Override
  public Hero findById(int id) {
    try(Connection con = sql2o.open()){
      return con.createQuery("SELECT * FROM heroes WHERE id = :id")
        .addParameter("id", id)
        .executeAndFetchFirst(Hero.class);
    }
  }
  
  @Override
  public void update(int id, String newName, int newAge, String newPowers, String newWeakness,
                     int newSquadId) {
    String sql = "UPDATE heroes SET (name, age, powers, weakness, squadId) = (:name ,:age ," +
                 ":powers ,:weakness, :squadId) WHERE id=:id";
    try(Connection con = sql2o.open()){
      con.createQuery(sql)
      .addParameter("name", newName)
      .addParameter("age", newAge)
      .addParameter("powers", newPowers)
      .addParameter("weakness", newWeakness)
      .addParameter("squadId", newSquadId)
      .addParameter("id", id)
      .executeUpdate();
    }catch (Sql2oException ex) {
      System.out.println(ex);
    }
  }
  
  @Override
  public void deleteById(int id) {
    String sql = "DELETE from heroes WHERE id=:id";
    try (Connection con = sql2o.open()){
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    } catch (Sql2oException ex) {
      System.out.println(ex);
    }
  }
  
  @Override
  public void clearAllHeroes() {
    String sql = "DELETE from heroes";
    try (Connection con = sql2o.open()) {
      con.createQuery(sql)
      .executeUpdate();
    }catch (Sql2oException ex) {
      System.out.println(ex);
    }
  }
}
