package principles.dip;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

enum Relationship {
   PARENT, CHILD, SIBLING,
}

interface RelationshipBrowser {
   List<Person> findAllChildrenOf(String name);
}

class Person {
   private String name;

   public Person(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }
}

class TripletRelationship {
   private Person personOne;
   private Relationship relationship;
   private Person personTwo;

   public TripletRelationship(Person personOne, Relationship relationship, Person personTwo) {
      this.personOne = personOne;
      this.relationship = relationship;
      this.personTwo = personTwo;
   }

   public Person getPersonOne() {
      return personOne;
   }

   public Relationship getRelationship() {
      return relationship;
   }

   public Person getPersonTwo() {
      return personTwo;
   }
}

class Relationships implements RelationshipBrowser {
   private List<TripletRelationship> relations = new ArrayList<>();

   public void addParentAndChild(Person parent, Person child) {
      relations.add(new TripletRelationship(parent, Relationship.PARENT, child));
      relations.add(new TripletRelationship(child, Relationship.CHILD, parent));
   }

   public List<TripletRelationship> getRelations() {
      return relations;
   }

   @Override
   public List<Person> findAllChildrenOf(String name) {
      return this.relations.stream()
         .filter(relation -> Objects.equals(relation.getPersonOne()
            .getName(), name) && Objects.equals(relation.getRelationship(), Relationship.PARENT))
         .map(TripletRelationship::getPersonTwo)
         .collect(Collectors.toList());
   }
}

class Research {
   public Research(RelationshipBrowser relationshipBrowser) {
      List<Person> children = relationshipBrowser.findAllChildrenOf("John");
      for (Person child : children) {
         System.out.println("John has a child called " + child.getName());
      }
   }
}

class Demo {
   public static void main(String[] args) {
      Person parent = new Person("John");
      Person child1 = new Person("Chris");
      Person child2 = new Person("Matt");

      Relationships relationships = new Relationships();
      relationships.addParentAndChild(parent, child1);
      relationships.addParentAndChild(parent, child2);

      new Research(relationships);
   }
}