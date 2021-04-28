package models;

import org.junit.Test;
import static org.junit.Assert.*;

public class HeroTest {
  
  @Test
  public void NewHeroGetsCorrectlyCreated_true() throws Exception {
    Hero hero = setupNewHero();
    assertEquals(true, hero instanceof Hero);
  }
  
  @Test
  public void HeroInstantiatesWithName_true() throws Exception {
    Hero hero = setupNewHero();
    assertEquals("Rain-man", hero.getName());
  }
  
  @Test
  public void HeroInstantiatesWithAge_true() throws Exception {
    Hero hero = setupNewHero();
    assertEquals(27, hero.getAge());
  }
  
  @Test
  public void HeroInstantiatesWithPowers_true() throws Exception {
    Hero hero = setupNewHero();
    assertEquals("controls the weather", hero.getPowers());
  }
  
  @Test
  public void HeroInstantiatesWithWeakness_true() throws Exception {
    Hero hero = setupNewHero();
    assertEquals("being indoors", hero.getWeakness());
  }
  
  
  //helper methods
  public Hero setupNewHero(){
    return new Hero("Rain-man", 27, "controls the weather", "being indoors",1);
  }
  
}