package patterns.creational.singleton;

class BasicSingleton {
   private static BasicSingleton INSTANCE;
   private int value;

   private BasicSingleton() {
   }

   public static BasicSingleton getInstance() {
      if (INSTANCE == null) {
         synchronized (BasicSingleton.class) {
            INSTANCE = new BasicSingleton();
         }
      }
      return INSTANCE;
   }

   public int getValue() {
      return value;
   }

   public void setValue(int value) {
      this.value = value;
   }
}

class DemoBasicSingleton {
   public static void main(String[] args) {
      BasicSingleton basicSingleton = BasicSingleton.getInstance();
      basicSingleton.setValue(32);

      System.out.println(String.format("First object value %s", basicSingleton.getValue()));

      BasicSingleton basicSingleton1 = BasicSingleton.getInstance();
      System.out.println(String.format("Second object before change %s", basicSingleton1.getValue()));
      basicSingleton1.setValue(11);
      System.out.println(String.format("After change -> Second object %s; First object %s", basicSingleton1.getValue(),
         basicSingleton.getValue()));
   }
}
