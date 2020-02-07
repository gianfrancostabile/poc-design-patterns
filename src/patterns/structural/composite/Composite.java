package patterns.structural.composite;

import java.util.ArrayList;
import java.util.List;

class GraphicObject {
   private String name = "Group";
   private String color;
   private List<GraphicObject> children = new ArrayList<>();

   public GraphicObject() {}

   @Override
   public String toString() {
      return "GraphicObject{" + "name='" + name + '\'' + ", color='" + color + '\'' + ", children=" + children + '}';
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getColor() {
      return color;
   }

   public void setColor(String color) {
      this.color = color;
   }

   public List<GraphicObject> getChildren() {
      return children;
   }

   public void setChildren(List<GraphicObject> children) {
      this.children = children;
   }
}

class Circle extends GraphicObject {
   public Circle(String color) {
      this.setName("Circle");
      this.setColor(color);
   }
}

class Square extends GraphicObject {
   public Square(String color) {
      this.setName("Square");
      this.setColor(color);
   }
}

class DemoComposite {
   public static void main(String[] args) {
      GraphicObject graphicObject = new GraphicObject();
      graphicObject.setName("My Drawing");
      graphicObject.getChildren().add(new Circle("blue"));
      graphicObject.getChildren().add(new Square("red"));

      GraphicObject anotherGraphic = new GraphicObject();
      anotherGraphic.getChildren().add(new Circle("yellow"));
      anotherGraphic.getChildren().add(new Square("yellow"));
      anotherGraphic.getChildren().add(graphicObject);

      System.out.println(anotherGraphic);
   }
}
