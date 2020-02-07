package patterns.creational.singleton;

/**
 * The only problem is to Serialize the singleton object,
 * after se serialization it returns the Enum name as value.
 */
enum EnumBasedSingleton {
   INSTANCE(21);

   private int value;

   EnumBasedSingleton(int value) {
      this.value = value;
   }

   public int getValue() {
      return value;
   }

   public void setValue(int value) {
      this.value = value;
   }
}