package patterns.creational.builder;

import java.util.ArrayList;
import java.util.Collections;

class HtmlElement {
   private final int indentSize;
   private final String newLine;
   private String name;
   private String text;
   private ArrayList<HtmlElement> elements;

   public HtmlElement() {
      this.elements = new ArrayList<>();
      this.indentSize = 2;
      this.newLine = System.lineSeparator();
   }

   public HtmlElement(String name, String text) {
      this();
      this.name = name;
      this.text = text;
   }

   private String toStringImpl(int indent) {
      StringBuilder stringBuilder = new StringBuilder();
      String i = String.join("", Collections.nCopies(indent * this.indentSize, " "));
      stringBuilder.append(String.format("%s<%s>%s", i, this.name, this.newLine));
      if (this.text != null && !this.text.isEmpty()) {
         stringBuilder.append(String.join("", Collections.nCopies(this.indentSize * (indent + 1), " ")))
            .append(text)
            .append(this.newLine);
      }

      for (HtmlElement element : this.elements) {
         stringBuilder.append(element.toStringImpl(indent + 1));
      }
      stringBuilder.append(String.format("%s<%s>%s", i, this.name, this.newLine));
      return stringBuilder.toString();
   }

   @Override
   public String toString() {
      return this.toStringImpl(0);
   }

   public void setName(String name) {
      this.name = name;
   }

   public ArrayList<HtmlElement> getElements() {
      return elements;
   }
}

class HtmlBuilder {
   private String rootName;
   private HtmlElement root;

   public HtmlBuilder(String rootName) {
      this.root = new HtmlElement();
      this.rootName = rootName;
      this.root.setName(rootName);
   }

   public HtmlBuilder addChild(String childName, String childText) {
      HtmlElement element = new HtmlElement(childName, childText);
      this.root.getElements()
         .add(element);
      return this;
   }

   public void clear() {
      this.root = new HtmlElement();
      this.root.setName(rootName);
   }

   @Override
   public String toString() {
      return this.root.toString();
   }
}

class DemoBuilder {
   public static void main(String[] args) {
      HtmlBuilder builder = new HtmlBuilder("ul").addChild("li", "hello")
         .addChild("li", "world");
      System.out.println(builder);
   }
}
