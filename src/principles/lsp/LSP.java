package principles.lsp;

class Rectangle {
   protected int width;
   protected int height;

   public Rectangle(int width, int height) {
      this.width = width;
      this.height = height;
   }

   public int getWidth() {
      return width;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public int getHeight() {
      return height;
   }

   public void setHeight(int height) {
      this.height = height;
   }

   public int getArea() {
      return this.width * this.height;
   }

   @Override
   public String toString() {
      return "Rectangle{" + "width=" + width + ", height=" + height + '}';
   }

   public boolean isSquare() {
      return this.getWidth() == this.getHeight();
   }
}

class Square extends Rectangle {
   public Square(int size) {
      super(size, size);
   }

   @Override
   public void setWidth(int size) {
      super.setWidth(size);
      super.setHeight(size);
   }

   @Override
   public void setHeight(int size) {
      super.setWidth(size);
      super.setHeight(size);
   }
}

class RectangleFactory {
   public static Rectangle newRectangle(int width, int height) {
      return new Rectangle(width, height);
   }

   public static Rectangle newSquare(int size) {
      return new Rectangle(size, size);
   }
}
class Demo {
   static void useIt(Rectangle rectangle) {
      int width = rectangle.getWidth();
      rectangle.setHeight(10);
      System.out.println("Expected area of " + (width * 10) + ", got " + rectangle.getArea());
   }

   public static void main(String[] args) {
      Rectangle rectangle = new Rectangle(2, 3);
      useIt(rectangle);

      Rectangle square = new Square(5);
      square.setWidth(7);
      useIt(square);
   }
}