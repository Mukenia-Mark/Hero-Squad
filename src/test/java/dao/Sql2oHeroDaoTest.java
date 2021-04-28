package dao;

import models.Hero;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class Sql2oHeroDaoTest {
  private static Sql2oHeroDao heroDao;
  private static Connection conn;
  
  @BeforeClass
  public static void setUp() throws Exception {
    String connectionString = "jdbc:postgresql://localhost:5432/herosquad_test";
    Sql2o sql2o = new Sql2o(connectionString, "mark", "infinity");
    heroDao = new Sql2oHeroDao(sql2o);
    conn = sql2o.open();
  }
  
  @After
  public void tearDown() throws Exception {
    System.out.println("clearing database");
    heroDao.clearAllHeroes();
    
  }
  
  @AfterClass
  public static void shutDown() throws Exception{
    conn.close();
    System.out.println("connection closed");
  }
  
  @Test
  public void addingHeroSetsId() throws Exception {
    Hero hero = setupNewHero();
    int originalHeroId = hero.getId();
    heroDao.add(hero);
    assertNotEquals(originalHeroId, hero.getId());
  }
  
  @Test
  public void existingHeroCanBeFoundById() throws Exception {
    Hero hero = setupNewHero();
    heroDao.add(hero);
    Hero foundHero = heroDao.findById(hero.getId());
    assertEquals(hero, foundHero);
  }
  
  @Test
  public void addedHeroesAreReturnedFromgetAll() throws Exception {
    Hero hero = setupNewHero();
    heroDao.add(hero);
    assertEquals(1, heroDao.getAll().size());
  }
  
  @Test
  public void noHeroesReturnsEmptyList() throws Exception {
    assertEquals(0, heroDao.getAll().size());
  }
  
  @Test
  public void updateChangesHeroesContent() throws Exception {
    String initialName = "rain-man";
    Hero hero = setupNewHero();
    heroDao.add(hero);
    heroDao.update(hero.getId(),"Rain-man", 27,"controls the weather", "being indoors",1);
    Hero updatedHero = heroDao.findById(hero.getId());
    assertNotEquals(initialName, updatedHero.getName());
  }
  
  @Test
  public void deleteByIdDeletesCorrectHero() throws Exception {
    Hero hero = setupNewHero();
    heroDao.add(hero);
    heroDao.deleteById(hero.getId());
    assertEquals(0, heroDao.getAll().size());
  }
  
  @Test
  public void clearAllClearsAll() throws Exception {
    Hero hero = setupNewHero();
    Hero otherHero = new Hero("Rain-man",27, "controls the weather","being indoors", 1);
    heroDao.add(hero);
    heroDao.add(otherHero);
    int daoSize = heroDao.getAll().size();
    heroDao.clearAllHeroes();
    assertTrue(daoSize > 0 && daoSize > heroDao.getAll().size());
  }
  
  @Test
  public void squadIdIsReturnedCorrectly() throws Exception {
    Hero hero = setupNewHero();
    int originalSquadId = hero.getSquadId();
    heroDao.add(hero);
    assertEquals(originalSquadId, heroDao.findById(hero.getId()).getSquadId());
  }
  
  //helper methods
  public Hero setupNewHero(){
    return new Hero("Rain-man", 27, "controls the weather", "being indoors", 1);
  }
}