package dao;

import models.Squad;
import models.Hero;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class Sql2oSquadDaoTest {
  private static Sql2oSquadDao squadDao;
  private static Sql2oHeroDao heroDao;
  private static Connection conn;
  
 @BeforeClass
  public static void setUp() throws Exception {
    String connectionString = "jdbc:postgresql://localhost:5432/herosquad_test";
    Sql2o sql2o = new Sql2o(connectionString, "mark", "infinity");
    squadDao = new Sql2oSquadDao(sql2o);
    heroDao = new Sql2oHeroDao(sql2o);
    conn = sql2o.open();
  }
  
  @After
    public void tearDown() throws Exception {
    System.out.println("clearing database");
    squadDao.clearAllSquads();
    heroDao.clearAllHeroes();
  }
  
  @AfterClass
  public static void shutDown() throws Exception{
    conn.close();
    System.out.println("connection closed");
  }
  
  @Test
  public void addingSquadSetsId() throws Exception {
    Squad squad = setupNewSquad();
    int originalSquadId = squad.getId();
    squadDao.add(squad);
    assertNotEquals(originalSquadId, squad.getId());
  }
  
  @Test
  public void noSquadsReturnsEmptyList() throws Exception {
    assertEquals(0, squadDao.getAll().size());
  }
  
  @Test
  public void deleteByIdDeletesCorrectSquad() throws Exception {
    Squad squad = setupNewSquad();
    squadDao.add(squad);
    squadDao.deleteById(squad.getId());
    assertEquals(0, squadDao.getAll().size());
  }
  
  @Test
  public void getAllHeroesBySquadReturnsTasksCorrectly() throws Exception {
    Squad squad = setupNewSquad();
    squadDao.add(squad);
    int squadId = squad.getId();
    Hero newHero = new Hero("Rain-man", 27,"controls the weather","being indoors",squadId);
    Hero otherHero = new Hero("Water-boy",22,"controls water","sponge", squadId);
    Hero thirdHero = new Hero("Fire-girl",20,"controls fire","water", squadId);
    heroDao.add(newHero);
    heroDao.add(otherHero);
    assertEquals(2, squadDao.getAllHeroesBySquad(squadId).size());
    assertTrue(squadDao.getAllHeroesBySquad(squadId).contains(newHero));
    assertTrue(squadDao.getAllHeroesBySquad(squadId).contains(otherHero));
    assertFalse(squadDao.getAllHeroesBySquad(squadId).contains(thirdHero));
  }
  // helper method
  public Squad setupNewSquad(){
    return new Squad("The Elementals","Against World Rape","Rain-man", 1);
  }
}
