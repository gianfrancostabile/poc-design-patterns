package principles.srp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

class Journal {
   private static int count = 0;
   private final List<String> entries = new ArrayList<>();

   public void addEntry(String text) {
      this.entries.add("" + (++count) + ": " + text);
   }

   public void removeEntry(int index) {
      this.entries.remove(index);
   }

   @Override
   public String toString() {
      return String.join(System.lineSeparator(), entries);
   }

   public void save(String filename) throws FileNotFoundException {
      try (PrintStream out = new PrintStream(filename)) {
         out.println(toString());
      }
   }

   public void load(String filename) {
   }

   public void load(URI url) {
   }
}

class Persistence {
   public void saveToFile(Journal journal, String filename, boolean overwrite) throws FileNotFoundException {
      if (overwrite || new File(filename).exists()) {
         try (PrintStream out = new PrintStream(filename)) {
            out.println(journal.toString());
         }
      }
   }
}

class Demo {
   public static void main(String[] args) throws IOException {
      Journal journal = new Journal();
      journal.addEntry("I cried today");
      journal.addEntry("I ate a bug");
      System.out.println(journal);

      Persistence persistence = new Persistence();
      String filename = "c:\\temp\\journal.txt";
      persistence.saveToFile(journal, filename, true);

      Runtime.getRuntime()
         .exec("notepad.exe" + filename);
   }
}
