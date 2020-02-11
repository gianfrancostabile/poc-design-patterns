package patterns.behavioral.strategy;

import java.util.Arrays;
import java.util.List;

enum OutputFormat {
   MARKDOWN, HTML
}

interface ListStrategy {
   default void start(StringBuilder stringBuilder) {
   }

   void add(StringBuilder stringBuilder, String item);

   default void end(StringBuilder stringBuilder) {
   }
}

class MarkdownListStrategy implements ListStrategy {

   @Override
   public void add(StringBuilder stringBuilder, String item) {
      stringBuilder.append(" * ")
         .append(item)
         .append(System.lineSeparator());
   }
}

class HtmlListStrategy implements ListStrategy {

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

class TextProcessor {
   private StringBuilder stringBuilder = new StringBuilder();
   private ListStrategy listStrategy;

   public TextProcessor(OutputFormat outputFormat) {
      this.setOutputFormat(outputFormat);
   }

   public void setOutputFormat(OutputFormat outputFormat) {
      switch (outputFormat) {
         case MARKDOWN:
            this.listStrategy = new MarkdownListStrategy();
            break;
         case HTML:
            this.listStrategy = new HtmlListStrategy();
            break;
      }
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

class DemoDynamicStrategy {
   public static void main(String[] args) {
      TextProcessor textProcessor = new TextProcessor(OutputFormat.MARKDOWN);
      textProcessor.appendList(Arrays.asList("item 1", "item 2", "item 3"));
      System.out.println(textProcessor);

      textProcessor.clear();
      textProcessor.setOutputFormat(OutputFormat.HTML);
      textProcessor.appendList(Arrays.asList("another 1", "another 2", "another 3"));
      System.out.println(textProcessor);
   }
}