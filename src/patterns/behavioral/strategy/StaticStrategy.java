package patterns.behavioral.strategy;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

interface ListStaticStrategy {
   default void start(StringBuilder stringBuilder) {
   }

   void add(StringBuilder stringBuilder, String item);

   default void end(StringBuilder stringBuilder) {
   }
}

class MarkdownListStaticStrategy implements ListStaticStrategy {

   @Override
   public void add(StringBuilder stringBuilder, String item) {
      stringBuilder.append(" * ")
         .append(item)
         .append(System.lineSeparator());
   }
}

class HtmlListStaticStrategy implements ListStaticStrategy {

   @Override
   public void start(StringBuilder stringBuilder) {
      stringBuilder.append("<ul>")
         .append(System.lineSeparator());
   }

   @Override
   public void add(StringBuilder stringBuilder, String item) {
      stringBuilder.append("  <li>")
         .append(item)
         .append("</li>")
         .append(System.lineSeparator());
   }

   @Override
   public void end(StringBuilder stringBuilder) {
      stringBuilder.append("</ul>")
         .append(System.lineSeparator());
   }
}

class TextStaticProcessor<T extends ListStaticStrategy> {
   private StringBuilder stringBuilder = new StringBuilder();
   private T listStrategy;

   public TextStaticProcessor(T listStaticStrategy) {
      this.listStrategy = listStaticStrategy;
   }

   public TextStaticProcessor(Supplier<T> supplier) {
      this.listStrategy = supplier.get();
   }

   public void appendList(List<String> items) {
      this.listStrategy.start(this.stringBuilder);
      for (String item : items) {
         this.listStrategy.add(this.stringBuilder, item);
      }
      this.listStrategy.end(this.stringBuilder);
   }

   public void clear() {
      this.stringBuilder.setLength(0);
   }

   @Override
   public String toString() {
      return this.stringBuilder.toString();
   }
}

class DemoStaticStrategy {
   public static void main(String[] args) {
      TextStaticProcessor<MarkdownListStaticStrategy> textStaticProcessor =
         new TextStaticProcessor<MarkdownListStaticStrategy>(MarkdownListStaticStrategy::new);
      textStaticProcessor.appendList(Arrays.asList("item 1", "item 2", "item 3"));
      System.out.println(textStaticProcessor);

      TextStaticProcessor<HtmlListStaticStrategy> textStaticProcessor2 =
         new TextStaticProcessor<HtmlListStaticStrategy>(HtmlListStaticStrategy::new);
      textStaticProcessor2.appendList(Arrays.asList("another 1", "another 2", "another 3"));
      System.out.println(textStaticProcessor2);


   }
}