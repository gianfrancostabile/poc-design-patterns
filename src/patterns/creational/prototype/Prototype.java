package patterns.creational.prototype;

import java.util.Arrays;

class Address implements Cloneable {
   private String streetName;
   private int houseNumber;

   public Address(String streetName, int houseNumber) {
      this.streetName = streetName;
      this.houseNumber = houseNumber;
   }

   public Address(Address address) {
      this.streetName = address.streetName;
      this.houseNumber = address.houseNumber;
   }

   @Override
   public String toString() {
      return "Address{" + "streetName='" + streetName + '\'' + ", houseNumber=" + houseNumber + '}';
   }

   public void setHouseNumber(int houseNumber) {
      this.houseNumber = houseNumber;
   }

   @Override
   public Address clone() throws CloneNotSupportedException {
      return new Address(this.streetName, this.houseNumber);
   }
}

class Person implements Cloneable {
   private String[] names;
   private Address address;

   public Person(String[] names, Address address) {
      this.names = names;
      this.address = address;
   }

   public Person(Person person) {
      this.names = person.names;
      this.address = new Address(person.address);
   }

   @Override
   public String toString() {
      return "Person{" + "names=" + Arrays.toString(names) + ", address=" + address + '}';
   }

   public String[] getNames() {
      return names;
   }

   public Address getAddress() {
      return address;
   }

   @Override
   public Person clone() throws CloneNotSupportedException {
      return new Person(this.names.clone(), this.address.clone());
   }
}

class DemoPrototype {
   public static void main(String[] args) throws CloneNotSupportedException {
      Person john = new Person(new String[] { "John", "Smith" }, new Address("London Road", 2133));
      Person jane = john.clone();
      jane.getNames()[0] = "Jane";
      jane.getAddress()
         .setHouseNumber(2134);
      System.out.println(john);
      System.out.println(jane);
   }
}
