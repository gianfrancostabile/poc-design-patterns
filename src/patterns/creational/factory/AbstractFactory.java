package patterns.creational.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

interface HotDrink {
   void consume();
}

interface HotDrinkFactory {
   HotDrink prepare();
}

class Tea implements HotDrink {
   private Tea() { }

   @Override
   public void consume() {
      System.out.println("This tea is delicious");
   }

   static class Factory implements HotDrinkFactory {
      @Override
      public Tea prepare() {
         return new Tea();
      }
   }
}

class Coffee implements HotDrink {
   private Coffee() { }

   @Override
   public void consume() {
      System.out.println("This coffee is delicious");
   }

   static class Factory implements HotDrinkFactory {
      @Override
      public HotDrink prepare() {
         return new Coffee();
      }
   }
}


class HotDrinkMachine {
   private Map<String, Supplier<HotDrink>> factorySuppliers = new HashMap<>();

   public HotDrinkMachine() {
      this.factorySuppliers.put("tea", new Tea.Factory()::prepare);
      this.factorySuppliers.put("coffee", new Coffee.Factory()::prepare);
   }

   public Optional<HotDrink> makeDrink(String drink) {
      Optional<Supplier<HotDrink>> optionalSupplier = Optional.ofNullable(this.factorySuppliers.get(drink));
      return optionalSupplier.map(supplier -> Optional.of(supplier.get()))
         .orElse(null);
   }
}

class DemoAbstractFactory {
   public static void main(String[] args) {
      HotDrinkMachine hotDrinkMachine = new HotDrinkMachine();
      Optional<HotDrink> optionalHotDrink = hotDrinkMachine.makeDrink("tea");
      optionalHotDrink.ifPresent(HotDrink::consume);
   }
}
