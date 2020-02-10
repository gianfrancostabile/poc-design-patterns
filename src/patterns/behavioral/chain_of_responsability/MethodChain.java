package patterns.behavioral.chain_of_responsability;

import java.util.Objects;

class Creature {
   public String name;
   public int attack;
   public int defense;

   public Creature(String name, int attack, int defense) {
      this.name = name;
      this.attack = attack;
      this.defense = defense;
   }

   @Override
   public String toString() {
      return "Creature{" + "name='" + name + '\'' + ", attack=" + attack + ", defense=" + defense + '}';
   }
}

class CreatureModifier {
   protected Creature creature;
   protected CreatureModifier next;

   public CreatureModifier(Creature creature) {
      this.creature = creature;
   }

   public void add(CreatureModifier creatureModifier) {
      if (Objects.nonNull(this.next)) {
         this.next.add(creatureModifier);
      } else {
         this.next = creatureModifier;
      }
   }

   public void handle() {
      if (Objects.nonNull(this.next)) {
         this.next.handle();
      }
   }
}

class DoubleAttackModifier extends CreatureModifier {
   public DoubleAttackModifier(Creature creature) {
      super(creature);
   }

   @Override
   public void handle() {
      System.out.println(String.format("Doubling %s's attack", this.creature.name));
      this.creature.attack *= 2;
      super.handle();
   }
}

class IncreaseDefenseModifier extends CreatureModifier {
   public IncreaseDefenseModifier(Creature creature) {
      super(creature);
   }

   @Override
   public void handle() {
      System.out.println(String.format("Increasing %s's defense", this.creature.name));
      creature.defense += 3;
      super.handle();
   }
}

class NoBonusesModifier extends CreatureModifier {
   public NoBonusesModifier(Creature creature) {
      super(creature);
   }

   @Override
   public void handle() {
      System.out.println(String.format("No bonuses for %s", this.creature.name));
   }
}

class DemoMethodChain {
   public static void main(String[] args) {
      Creature creature = new Creature("Goblin", 2, 2);
      System.out.println(creature);

      CreatureModifier root = new CreatureModifier(creature);
      root.add(new NoBonusesModifier(creature));
      root.add(new DoubleAttackModifier(creature));
      root.add(new IncreaseDefenseModifier(creature));
      root.handle();
      System.out.println(creature);
   }
}