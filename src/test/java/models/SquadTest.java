package models;

import org.junit.Test;
import static org.junit.Assert.*;

public class SquadTest {
  
  @Test
  public void NewSquadGetsCorrectlyCreated_true() throws Exception {
    Squad squad = setupNewSquad();
    assertEquals(true, squad instanceof Squad);
  }
  
  @Test
  public void SquadInstantiatesWithName_true() throws Exception {
    Squad squad = setupNewSquad();
    assertEquals("The Elementals", squad.getName());
  }
  
  @Test
  public void SquadInstantiatesWithCause_true() throws Exception {
    Squad squad = setupNewSquad();
    assertEquals("Against World Rape", squad.getCause());
  }
  
  @Test
  public void SquadInstantiatesWithMembers_true() throws Exception {
    Squad squad = setupNewSquad();
    assertEquals("Rain-man", squad.getMembers());
  }
  
  public Squad setupNewSquad(){
    return new Squad("The Elementals", "Against World Rape","Rain-man",1);
  }
}