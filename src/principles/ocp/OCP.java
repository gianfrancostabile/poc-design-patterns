package principles.ocp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

enum Color {
   RED, GREEN, BLUE
}

enum Size {
   SMALL, MEDIUM, LARGE, YUGE
}

interface Specification<T> {
   boolean isSatisfied(T item);
}

interface Filter<T> {
   Stream<T> filter(List<T> items, Specification<T> spec);
}

class Product {
   public String name;
   public Color color;
   public Size size;

   public Product(String name, Color color, Size size) {
      this.name = name;
      this.color = color;
      this.size = size;
   }
}

class ProductFilter {
   public Stream<Product> filterByColor(List<Product> products, Color color) {
      return products.stream()
         .filter(product -> product.color.equals(color));
   }

   public Stream<Product> filterBySize(List<Product> products, Size size) {
      return products.stream()
         .filter(product -> product.size.equals(size));
   }

   public Stream<Product> filterByColorAndSize(List<Product> products, Color color, Size size) {
      return products.stream()
         .filter(product -> Boolean.logicalAnd(product.color.equals(color), product.size.equals(size)));
   }
}

class ColorSpecification implements Specification<Product> {
   private Color color;

   public ColorSpecification(Color color) {
      this.color = color;
   }

   @Override
   public boolean isSatisfied(Product item) {
      return item.color.equals(this.color);
   }
}

class SizeSpecification implements Specification<Product> {
   private Size size;

   public SizeSpecification(Size size) {
      this.size = size;
   }

   @Override
   public boolean isSatisfied(Product item) {
      return item.size.equals(this.size);
   }
}

class AndSpecification<T> implements Specification<T> {
   private Specification<T> first;
   private Specification<T> second;

   public AndSpecification(Specification<T> first, Specification<T> second) {
      this.first = first;
      this.second = second;
   }

   @Override
   public boolean isSatisfied(T item) {
      return this.first.isSatisfied(item) && this.second.isSatisfied(item);
   }
}

class BetterFilter implements Filter<Product> {
   @Override
   public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
      return items.stream()
         .filter(spec::isSatisfied);
   }
}

class Demo {
   public static void main(String[] args) {
      Product apple = new Product("apple", Color.RED, Size.SMALL);
      Product tree = new Product("tree", Color.GREEN, Size.LARGE);
      Product house = new Product("house", Color.BLUE, Size.LARGE);

      List<Product> products = Arrays.asList(apple, tree, house);

      ProductFilter productFilter = new ProductFilter();
      System.out.println("Green product (old):");
      productFilter.filterByColor(products, Color.GREEN)
         .forEach(product -> System.out.println(" - " + product.name + " is green"));

      BetterFilter betterFilter = new BetterFilter();
      System.out.println("Green product (new):");
      betterFilter.filter(products, new ColorSpecification(Color.GREEN))
         .forEach(product -> System.out.println(" - " + product.name + " is green"));

      System.out.println("Blue and Large product:");
      betterFilter.filter(products,
         new AndSpecification<>(new ColorSpecification(Color.BLUE), new SizeSpecification(Size.LARGE)))
         .forEach(product -> System.out.println(" - " + product.name + " is large and blue"));
   }
}