import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Sql2oSquadDao;
import dao.Sql2oHeroDao;
import models.Squad;
import models.Hero;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
      ProcessBuilder processBuilder = new ProcessBuilder();
      if (processBuilder.environment().get("PORT") != null) {
        return Integer.parseInt(processBuilder.environment().get("PORT"));
      } return 4567;
    } public static void main(String[] args) {
      port(getHerokuAssignedPort());
    staticFileLocation("/public");
    //String connectionString = "jdbc:postgresql://localhost:5432/herosquad";
    //Sql2o sql2o = new Sql2o(connectionString, "mark", "infinity");
    String connectionString = "jdbc:postgresql://ec2-54-87-112-29.compute-1.amazonaws" +
                              ".com:5432/d16gf5ctskdn00";
    Sql2o sql2o = new Sql2o(connectionString, "qlnlcfzwfzolyr",
      "3de267ab7d078e0a37d33de40884aae55ff9d09cdaa5403c741c5a19b6118416");
    Sql2oHeroDao heroDao = new Sql2oHeroDao(sql2o);
    Sql2oSquadDao squadDao = new Sql2oSquadDao(sql2o);
    
    
    //get: show all heroes in all squads and show all squads
    get("/", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      List<Squad> allSquads = squadDao.getAll();
      model.put("squads", allSquads);
      List<Hero> heroes = heroDao.getAll();
      model.put("heroes", heroes);
      return new ModelAndView(model, "index.hbs");
    }, new HandlebarsTemplateEngine());
    
    //get: show a form to create a new squad
    get("/squads/new", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      List<Squad> squads = squadDao.getAll();
      model.put("squads", squads);
      return new ModelAndView(model, "squad-form.hbs"); //new layout
    }, new HandlebarsTemplateEngine());
    
    //post: process a form to create a new squad
    post("/squads", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      String name = req.queryParams("name");
      String cause = req.queryParams("cause");
      String members = req.queryParams("members");
      int squadId = Integer.parseInt(req.queryParams("squadId"));
      Squad newSquad = new Squad(name, cause, members, squadId);
      squadDao.add(newSquad);
      res.redirect("/");
      return null;
    }, new HandlebarsTemplateEngine());
    
    
    //get: delete all squads and all heroes
    get("/squad/delete", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      squadDao.clearAllSquads();
      heroDao.clearAllHeroes();
      res.redirect("/");
      return null;
    }, new HandlebarsTemplateEngine());
    
    //get: delete all heroes
    get("/heroes/delete", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      heroDao.clearAllHeroes();
      res.redirect("/");
      return null;
    }, new HandlebarsTemplateEngine());
    
    //get a specific squad (and the heroes it contains)
    get("/squads/:id", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      int idOfSquadToFind = Integer.parseInt(req.params("id"));
      Squad foundSquad = squadDao.findById(idOfSquadToFind);
      model.put("squad", foundSquad);
      List<Hero> allHeroesBySquad = squadDao.getAllHeroesBySquad(idOfSquadToFind);
      model.put("heroes", allHeroesBySquad);
      model.put("squad", squadDao.getAll());
      return new ModelAndView(model, "squad-detail.hbs.hbs");
    }, new HandlebarsTemplateEngine());
    
    //get: show a form to update a squad
    get("/squads/:id/edit", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("editSquad", true);
      Squad squad = squadDao.findById(Integer.parseInt(req.params("id")));
      model.put("squad", squad);
      model.put("squads", squadDao.getAll());
      return new ModelAndView(model, "squad-form.hbs");
    }, new HandlebarsTemplateEngine());
    
    //post: process a form to update a squad
    post("/squads/:id", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      int idOfSquadToEdit = Integer.parseInt(req.params("id"));
      String newName = req.queryParams("newSquadName");
      String newCause = req.queryParams("newCauseDescription");
      String newMembers = req.queryParams("newMembers");
      squadDao.update(idOfSquadToEdit, newName, newCause, newMembers);
      res.redirect("/");
      return null;
    }, new HandlebarsTemplateEngine());
    
    //get: delete an individual task
    get("/squads/:squad_id/heroes/:heroes_id/delete", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      int idOfHeroToDelete = Integer.parseInt(req.params("hero_id"));
      heroDao.deleteById(idOfHeroToDelete);
      res.redirect("/");
      return null;
    }, new HandlebarsTemplateEngine());
    
    //get: show new hero form
    get("/heroes/new", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      List<Squad> squad = squadDao.getAll();
      model.put("squad", squad);
      return new ModelAndView(model, "hero-form.hbs");
    }, new HandlebarsTemplateEngine());
    
    //task: process new hero form
    post("/heroes", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      List<Squad> allSquads = squadDao.getAll();
      model.put("squads", allSquads);
      String name = req.queryParams("name");
      int age = Integer.parseInt(req.queryParams("age"));
      String powers = req.queryParams("powers");
      String weakness = req.queryParams("weakness");
      int squadId = Integer.parseInt(req.queryParams("squadId"));
      Hero newHero = new Hero(name, age, powers, weakness, squadId);
      heroDao.add(newHero);
      res.redirect("/");
      return null;
    }, new HandlebarsTemplateEngine());
    
    //get: show an individual hero that is nested in a squad
    get("/squads/:squad_id/heroes/:hero_id", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      int idOfHeroToFind = Integer.parseInt(req.params("hero_id"));
      Hero foundHero = heroDao.findById(idOfHeroToFind);
      int idOfSquadToFind = Integer.parseInt(req.params("squad_id"));
      Squad foundSquad = squadDao.findById(idOfSquadToFind);
      model.put("squad", foundSquad);
      model.put("hero", foundHero);
      model.put("squads", squadDao.getAll());
      return new ModelAndView(model, "hero-detail.hbs");
    }, new HandlebarsTemplateEngine());
    
    //get: show a form to update a hero
    get("/heroes/:id/edit", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      List<Squad> allSquads = squadDao.getAll();
      model.put("squad", allSquads);
      Hero hero = heroDao.findById(Integer.parseInt(req.params("id")));
      model.put("hero", hero);
      model.put("editHero", true);
      return new ModelAndView(model, "hero-form.hbs");
    }, new HandlebarsTemplateEngine());
    
    //task: process a form to update a squad
    post("/heroes/:id", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      int heroToEditId = Integer.parseInt(req.params("id"));
      String newName = req.queryParams("name");
      String newCause = req.queryParams("weakness");
      String newMembers = req.queryParams("members");
      squadDao.update(heroToEditId, newName, newCause, newMembers);
      res.redirect("/");
      return null;
    }, new HandlebarsTemplateEngine());
    
  }
}