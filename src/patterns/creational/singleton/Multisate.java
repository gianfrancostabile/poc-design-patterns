package patterns.creational.singleton;


import java.util.HashMap;

enum Subsystem {
   PRIMARY,
   AUXILIARY,
   FALLBACK
}

class Printer {
   private static int instanceCount = 0;
   private Printer() {
      instanceCount++;
      System.out.println(String.format("A total of %s instances created", instanceCount));
   }

   private static HashMap<Subsystem, Printer> instances = new HashMap<>();

   public static Printer get(Subsystem subsystem) {
      if (!instances.containsKey(subsystem)) {
         instances.put(subsystem, new Printer());
      }
      return instances.get(subsystem);
   }
}

class DemoMultiton {
   public static void main(String[] args) {
      Printer main = Printer.get(Subsystem.PRIMARY);
      Printer auxiliary = Printer.get(Subsystem.AUXILIARY);
      Printer fallback = Printer.get(Subsystem.PRIMARY);
   }
}