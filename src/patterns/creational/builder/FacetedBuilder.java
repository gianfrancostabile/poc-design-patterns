package patterns.creational.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PersonFaceted {
   private String streetAddress;
   private String postCode;
   private String city;
   private String companyName;
   private String position;
   private int annualIncome;

   public String getStreetAddress() {
      return streetAddress;
   }

   public void setStreetAddress(String streetAddress) {
      this.streetAddress = streetAddress;
   }

   public String getPostCode() {
      return postCode;
   }

   public void setPostCode(String postCode) {
      this.postCode = postCode;
   }

   public String getCity() {
      return city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getCompanyName() {
      return companyName;
   }

   public void setCompanyName(String companyName) {
      this.companyName = companyName;
   }

   public String getPosition() {
      return position;
   }

   public void setPosition(String position) {
      this.position = position;
   }

   public int getAnnualIncome() {
      return annualIncome;
   }

   public void setAnnualIncome(int annualIncome) {
      this.annualIncome = annualIncome;
   }

   @Override
   public String toString() {
      return "PersonFaceted{" + "streetAddress='" + streetAddress + '\'' + ", postCode='" + postCode + '\'' +
         ", city='" + city + '\'' + ", companyName='" + companyName + '\'' + ", position='" + position + '\'' +
         ", annualIncome=" + annualIncome + '}';
   }
}

class PersonBuilderFaceted {
   protected PersonFaceted person;

   public PersonBuilderFaceted() {
      this.person = new PersonFaceted();
   }

   public PersonAddressBuilder lives() {
      return new PersonAddressBuilder(this.person);
   }

   public PersonJobBuilder works() {
      return new PersonJobBuilder(this.person);
   }

   public PersonFaceted build() {
      return person;
   }
}

class PersonAddressBuilder extends PersonBuilderFaceted {
   public PersonAddressBuilder(PersonFaceted person) {
      this.person = person;
   }

   public PersonAddressBuilder at(String streetAddress) {
      this.person.setStreetAddress(streetAddress);
      return this;
   }

   public PersonAddressBuilder withPostcode(String postCode) {
      this.person.setPostCode(postCode);
      return this;
   }

   public PersonAddressBuilder in(String city) {
      this.person.setCity(city);
      return this;
   }
}

class PersonJobBuilder extends PersonBuilderFaceted {
   public PersonJobBuilder(PersonFaceted person) {
      this.person = person;
   }

   public PersonJobBuilder at(String companyName) {
      this.person.setCompanyName(companyName);
      return this;
   }

   public PersonJobBuilder asA(String position) {
      this.person.setPosition(position);
      return this;
   }

   public PersonJobBuilder earning(int income) {
      this.person.setAnnualIncome(income);
      return this;
   }
}

class DemoFacetedBuilder {
   public static void main(String[] args) {
      PersonBuilderFaceted builder = new PersonBuilderFaceted();
      PersonFaceted person = builder.lives()
         .at("123 London Road")
         .in("London")
         .withPostcode("SW12BC")
         .works()
         .at("Company")
         .asA("Engineer")
         .earning(123213)
         .build();
      System.out.println(person);
   }
}

class Field {
   private String type;
   private String name;

   public Field(String name, String type) {
      this.name = name;
      this.type = type;
   }

   @Override
   public String toString() {
      return String.format("public %s %s;", this.type, this.name);
   }
}

class CodeBuilder
{
   private String className;
   private List<Field> fields;

   public CodeBuilder(String className) {
      this.className = className;
      this.fields = new ArrayList<>();
   }

   public CodeBuilder addField(String name, String type) {
      this.fields.add(new Field(name, type));
      return this;
   }

   @Override
   public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder
         .append(String.format("public class %s", this.className))
         .append("\n{");
      for (Field field : this.fields) {
         stringBuilder.append("\n  ").append(field.toString());
      }
      stringBuilder.append("\n}");
      return stringBuilder.toString();
   }
}

class Exercise {
   public static void main(String[] args) {
      CodeBuilder cb = new CodeBuilder("Person").addField("name", "String").addField("age", "int");
      System.out.println(cb);
   }
}