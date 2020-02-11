package patterns.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

interface Observer<T> {
   void handle(PropertyChangedEventArgs<T> args);
}

class PropertyChangedEventArgs<T> {
   private T source;
   private String propertyName;
   private Object newValue;

   public PropertyChangedEventArgs(T source, String propertyName, Object newValue) {
      this.source = source;
      this.propertyName = propertyName;
      this.newValue = newValue;
   }

   public T getSource() {
      return source;
   }

   public String getPropertyName() {
      return propertyName;
   }

   public Object getNewValue() {
      return newValue;
   }
}

class Observable<T> {
   private List<Observer<T>> observerList = new ArrayList<>();

   public void subscribe(Observer<T> observer) {
      this.observerList.add(observer);
   }

   protected void propertyChanged(T source, String propertyName, Object newValue) {
      for (Observer<T> observer : this.observerList) {
         observer.handle(new PropertyChangedEventArgs<>(source, propertyName, newValue));
      }
   }
}

class Person extends Observable<Person> {
   private int age;

   public int getAge() {
      return age;
   }

   public void setAge(int age) {
      if (this.age != age) {
         this.age = age;
         this.propertyChanged(this, "age", age);
      }
   }
}

class DemoObserver implements Observer<Person> {

   public DemoObserver() {
      Person person = new Person();
      for (int i = 20; i < 24; i++) {
         person.setAge(i);
      }
   }

   public static void main(String[] args) {
      new DemoObserver();
   }

   @Override
   public void handle(PropertyChangedEventArgs<Person> args) {
      System.out.println(String.format("Person's %s has changed to %s", args.getPropertyName(), args.getNewValue()));
   }
}