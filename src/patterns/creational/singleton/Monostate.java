package patterns.creational.singleton;

class Monostate {
   private static String name;
   private static int age;

   @Override
   public String toString() {
      return "Monostate{" + "name='" + name + '\'' + ", age=" + age + '}';
   }

   public static String getName() {
      return name;
   }

   public static void setName(String name) {
      Monostate.name = name;
   }

   public static int getAge() {
      return age;
   }

   public static void setAge(int age) {
      Monostate.age = age;
   }
}

class DemoMonostate {
   public static void main(String[] args) {
      Monostate monostate = new Monostate();
      monostate.setName("luis");
      monostate.setAge(22);

      Monostate newMonostate = new Monostate();
      System.out.println(newMonostate);
   }
}
