package patterns.structural.flyweight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

class User {
   private String fullName;

   public User(String fullName) {
      this.fullName = fullName;
   }
}

class User2 {
   private List<String> strings = new ArrayList<>();
   private int[] names;

   public User2(String fullName) {
      Function<String, Integer> getOrAdd = (String string) -> {
         int idx = this.strings.indexOf(string);
         if (idx == -1) {
            this.strings.add(string);
            return this.strings.size() - 1;
         } else {
            return idx;
         }
      };

      this.names = Arrays.stream(fullName.split(" "))
         .mapToInt(getOrAdd::apply)
         .toArray();
   }
}

class FormattedText {
   private String plainText;
   private boolean[] capitalize;

   public FormattedText(String plainText) {
      this.plainText = plainText;
      this.capitalize = new boolean[plainText.length()];
   }

   public void capitalize(int start, int end) {
      for (int i = start; i <= end; i++) {
         this.capitalize[i] = true;
      }
   }

   @Override
   public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < this.plainText.length(); ++i) {
         char c = this.plainText.charAt(i);
         stringBuilder.append(this.capitalize[i] ? Character.toUpperCase(c) : c);
      }
      return stringBuilder.toString();
   }
}

class BetterFormattedText {
   private String plainText;
   private List<TextRange> formatting = new ArrayList<>();

   public BetterFormattedText(String plainText) {
      this.plainText = plainText;
   }

   public TextRange getRange(int start, int end) {
      TextRange range = new TextRange(start, end);
      this.formatting.add(range);
      return range;
   }

   @Override
   public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < this.plainText.length(); ++i) {
         char c = this.plainText.charAt(i);
         for (TextRange range : this.formatting) {
            if (range.covers(i) && range.capitalize) {
               c = Character.toUpperCase(c);
            }
         }
         stringBuilder.append(c);
      }
      return stringBuilder.toString();
   }

   public class TextRange {
      public int start;
      public int end;
      public boolean capitalize;
      public boolean bold;
      public boolean italic;

      public TextRange(int start, int end) {
         this.start = start;
         this.end = end;
      }

      public boolean covers(int position) {
         return position >= this.start  && position <= this.end;
      }
   }
}

class DemoFlyweight {
   public static void main(String[] args) {
      User2 user = new User2("Gian");
      User2 user1 = new User2("Gian");

      FormattedText formattedText = new FormattedText("This is a brave new world");
      formattedText.capitalize(10, 15);
      System.out.println(formattedText);

      BetterFormattedText betterFormattedText = new BetterFormattedText("Make Argentina Great Again");
      betterFormattedText.getRange(13, 18).capitalize = true;
      System.out.println(betterFormattedText);
   }
}