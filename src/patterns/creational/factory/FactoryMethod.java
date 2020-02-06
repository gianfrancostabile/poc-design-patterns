package patterns.creational.factory;

enum CoordiateSystem {
   CARTESIAN, POLAR
}

class Point {
   private double x;
   private double y;

   /**
    * UGLY
    * @param a is x if cartesian or rho if polar
    * @param b is y if cartesian or theta if polar
    * @param coordiateSystem
    */
   private Point(double a, double b, CoordiateSystem coordiateSystem) {
      switch (coordiateSystem) {
         case CARTESIAN:
            this.x = a;
            this.y = b;
            break;
         case POLAR:
            this.x = a * Math.cos(b);
            this.y = a * Math.sin(b);
            break;
      }
   }

   private Point(double x, double y) {
      this.x = x;
      this.y = y;
   }

   static class Factory {
      public static Point newCartesianPoint(double x, double y) {
         return new Point(x, y);
      }

      public static Point newPolarPoint(double rho, double theta) {
         return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
      }
   }
}

class DemoFactoryMethod {
   public static void main(String[] args) {
      Point cartesianPoint = Point.Factory.newCartesianPoint(130, 22);
      Point polarPoint = Point.Factory.newPolarPoint(2, 34);
   }
}
