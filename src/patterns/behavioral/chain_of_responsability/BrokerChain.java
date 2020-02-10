package patterns.behavioral.chain_of_responsability;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

enum Argument {
   ATTACK, DEFENSE
}

class Event<Args> {
   private Map<Integer, Consumer<Args>> handlers = new HashMap<>();

   public int subscribe(Consumer<Args> handler) {
      int key = this.handlers.size() - 1;
      this.handlers.put(key, handler);
      return key;
   }

   public void unsubscribe(int key) {
      this.handlers.remove(key);
   }

   public void fire(Args args) {
      for (Consumer<Args> handler : this.handlers.values()) {
         handler.accept(args);
      }
   }
}

class Query {
   private String creatureName;
   private Argument argument;
   private int result;

   public Query(String creatureName, Argument argument, int result) {
      this.creatureName = creatureName;
      this.argument = argument;
      this.result = result;
   }

   public String getCreatureName() {
      return creatureName;
   }

   public void setCreatureName(String creatureName) {
      this.creatureName = creatureName;
   }

   public Argument getArgument() {
      return argument;
   }

   public void setArgument(Argument argument) {
      this.argument = argument;
   }

   public int getResult() {
      return result;
   }

   public void setResult(int result) {
      this.result = result;
   }
}

class Game {
   public Event<Query> queries = new Event<>();
}

class Creature1 {
   private Game game;
   private String name;
   private int baseAttack;
   private int baseDefense;

   public Creature1(Game game, String name, int baseAttack, int baseDefense) {
      this.game = game;
      this.name = name;
      this.baseAttack = baseAttack;
      this.baseDefense = baseDefense;
   }

   public Game getGame() {
      return game;
   }

   public String getName() {
      return name;
   }

   public int getBaseAttack() {
      return baseAttack;
   }

   public int getAttack() {
      Query query = new Query(this.name, Argument.ATTACK, this.baseAttack);
      game.queries.fire(query);
      return query.getResult();
   }

   public int getBaseDefense() {
      return baseDefense;
   }

   @Override
   public String toString() {
      return "Creature1{" + "game=" + game + ", name='" + name + '\'' + ", baseAttack=" + baseAttack +
         ", baseDefense=" + baseDefense + '}';
   }
}

class CreatureModifier1 {
   protected Game game;
   protected Creature1 creature1;

   public CreatureModifier1(Game game, Creature1 creature1) {
      this.game = game;
      this.creature1 = creature1;
   }
}

class DoubleAttackModifier1 extends CreatureModifier1 implements AutoCloseable {
   private int token;

   public DoubleAttackModifier1(Game game, Creature1 creature1) {
      super(game, creature1);
      token = game.queries.subscribe(query -> {
         if (query.getCreatureName()
            .equals(creature1.getName()) && query.getArgument() == Argument.ATTACK) {
            query.setResult(query.getResult() * 2);
         }
      });
   }

   @Override
   public void close() throws Exception {
      this.game.queries.unsubscribe(this.token);
   }
}

class IncreaseDefenseModifier1 extends CreatureModifier1 {
   public IncreaseDefenseModifier1(Game game, Creature1 creature1) {
      super(game, creature1);
      game.queries.subscribe(query -> {
         if (query.getCreatureName()
            .equals(creature1.getName()) && query.getArgument() == Argument.DEFENSE) {
            query.setResult(query.getResult() + 3);
         }
      });
   }
}

class DemoBrokerChain {
   public static void main(String[] args) {
      Game game = new Game();
      Creature1 creature1 = new Creature1(game, "Strong Goblin", 2, 2);
      System.out.println(creature1);

      IncreaseDefenseModifier1 increaseDefenseModifier1 = new IncreaseDefenseModifier1(game, creature1);
      try (DoubleAttackModifier1 doubleAttackModifier1 = new DoubleAttackModifier1(game, creature1)) {
         System.out.println(creature1);
      } catch (Exception exception) { }
      System.out.println(creature1);
   }
}