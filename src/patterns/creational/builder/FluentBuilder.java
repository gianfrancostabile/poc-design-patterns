package patterns.creational.builder;

class Person {
   private String name;
   private String position;

   @Override
   public String toString() {
      return "Person{" + "name='" + name + '\'' + ", position='" + position + '\'' + '}';
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPosition() {
      return position;
   }

   public void setPosition(String position) {
      this.position = position;
   }
}

class PersonBuilder<SELF extends PersonBuilder<SELF>> {
   protected Person person = new Person();

   public SELF withName(String name) {
      person.setName(name);
      return this.self();
   }

   public Person build() {
      return person;
   }

   protected SELF self() {
      return (SELF) this;
   }
}

class EmployeeBuilder extends PersonBuilder<EmployeeBuilder> {
   public EmployeeBuilder worksAt(String position) {
      this.person.setPosition(position);
      return this.self();
   }

   @Override
   protected EmployeeBuilder self() {
      return this;
   }
}

class DemoFluentBuilder {
   public static void main(String[] args) {
      EmployeeBuilder builder = new EmployeeBuilder();
      Person person = builder.withName("Dimitri")
         .worksAt("Developer")
         .build();
   }
}