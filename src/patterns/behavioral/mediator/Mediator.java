package patterns.behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

class Person {
   private String name;
   private ChatRoom room;
   private List<String> chatLog = new ArrayList<>();

   public Person(String name) {
      this.name = name;
   }

   public void receive(String sender, String message) {
      String chatMessage = String.format("%s: '%s'", sender, message);
      System.out.println(String.format("[%s's chat session] %s", this.name, chatMessage));
      chatLog.add(chatMessage);
   }

   public void say(String message) {
      room.broadcast(name, message);
   }

   public void privateMessage(String who, String message) {
      room.message(name, who, message);
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public ChatRoom getRoom() {
      return room;
   }

   public void setRoom(ChatRoom room) {
      this.room = room;
   }

   public List<String> getChatLog() {
      return chatLog;
   }

   public void setChatLog(List<String> chatLog) {
      this.chatLog = chatLog;
   }
}

class ChatRoom {
   private List<Person> people = new ArrayList<>();

   public void join(Person person) {
      String joinMessage = person.getName() + " joins the room";
      broadcast("room", joinMessage);
      person.setRoom(this);
      people.add(person);
   }

   public void broadcast(String source, String message) {
      for (Person person : this.people) {
         if (!person.getName()
            .equals(source)) {
            person.receive(source, message);
         }
      }
   }

   public void message(String source, String destination, String message) {
      this.people.stream()
         .filter(person -> person.getName()
            .equals(destination))
         .findFirst()
         .ifPresent(person -> person.receive(source, message));
   }
}

class DemoMediator {
   public static void main(String[] args) {
      ChatRoom chatRoom = new ChatRoom();

      Person john = new Person("John");
      Person jane = new Person("Jane");

      chatRoom.join(jane);
      chatRoom.join(john);

      john.say("hi room");
      jane.say("oh, hey john");

      Person simon = new Person("Simon");
      chatRoom.join(simon);
      simon.say("hi everyone!");

      jane.privateMessage("Simon", "glad you could join us!");
   }
}
