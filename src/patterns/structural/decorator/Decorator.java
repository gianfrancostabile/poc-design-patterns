package patterns.structural.decorator;

abstract class Shape {
   private Shape next;

   public Shape getNext() {
      return next;
   }

   public void setNext(Shape next) {
      this.next = next;
   }

   @Override
   public String toString() {
      return "Shape{" + "next=" + next + '}';
   }
}

class Circle extends Shape {
   public Circle() {
      this.setNext(null);
   }
}

class ColoredShape extends Shape {
   private String color;
   public ColoredShape(Shape shape, String color) {
      this.setNext(shape);
      this.color = color;
   }

   public String getColor() {
      return color;
   }

   @Override
   public String toString() {
      return "ColoredShape{" + "next=" + this.getNext() + ", color='" + color + '\'' + '}';
   }
}

class TransparentShape extends Shape {
   private double transparency;
   public TransparentShape(Shape shape, double transparency) {
      this.setNext(shape);
      this.transparency = transparency;
   }

   public double getTransparency() {
      return transparency;
   }

   @Override
   public String toString() {
      return "TransparentShape{" + "next=" + this.getNext() + ", transparency=" + transparency + '}';
   }
}

class DemoDecorator {
   public static void main(String[] args) {
      Circle circle = new Circle();
      System.out.println(circle);
      ColoredShape coloredShape = new ColoredShape(circle, "blue");
      System.out.println(coloredShape);
      TransparentShape transparentShape = new TransparentShape(coloredShape, 22);
      System.out.println(transparentShape);
   }
}