package patterns.behavioral.iterator;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

class Node<T> {
   private T value;
   private Node<T> parent;
   private Node<T> left;
   private Node<T> right;

   public Node(T value) {
      this.value = value;
   }

   public Node(T value, Node<T> left, Node<T> right) {
      this.value = value;
      this.left = left;
      this.right = right;

      left.parent = this;
      right.parent = this;
   }

   public T getValue() {
      return value;
   }

   public void setValue(T value) {
      this.value = value;
   }

   public Node<T> getParent() {
      return parent;
   }

   public void setParent(Node<T> parent) {
      this.parent = parent;
   }

   public Node<T> getLeft() {
      return left;
   }

   public void setLeft(Node<T> left) {
      this.left = left;
   }

   public Node<T> getRight() {
      return right;
   }

   public void setRight(Node<T> right) {
      this.right = right;
   }
}

class InOrderIterator<T> implements Iterator<T> {
   private Node<T> current;
   private Node<T> root;
   private boolean yieldedStart;

   public InOrderIterator(Node<T> root) {
      this.root = root;
      this.current = root;

      while (Objects.nonNull(this.current.getLeft())) {
         this.current = this.current.getLeft();
      }
   }

   private boolean hasRightMostParent(Node<T> node) {
      return Objects.nonNull(node.getParent()) && (node == node.getParent()
         .getLeft() || this.hasRightMostParent(node.getParent()));
   }

   @Override
   public boolean hasNext() {
      return Objects.nonNull(current.getLeft()) || Objects.nonNull(current.getRight()) ||
         this.hasRightMostParent(current);
   }

   @Override
   public T next() {
      T result = null;
      if (!this.yieldedStart) {
         this.yieldedStart = true;
         result = current.getValue();
      } else if (Objects.nonNull(current.getRight())) {
         current = current.getRight();
         while(Objects.nonNull(current.getLeft())) {
            current = current.getLeft();
         }
         result = current.getValue();
      } else {
         Node<T> parent = current.getParent();
         while (Objects.nonNull(parent) && current == parent.getRight()) {
            current = parent;
            parent = parent.getParent();
         }
         current = parent;
         result = current.getValue();
      }
      return result;
   }
}

class BinaryTree<T> implements Iterable<T> {
   private Node<T> root;

   public BinaryTree(Node<T> root) {
      this.root = root;
   }

   @Override
   public Iterator<T> iterator() {
      return new InOrderIterator<>(this.root);
   }

   @Override
   public void forEach(Consumer<? super T> action) {
      for (T item : this) {
         action.accept(item);
      }
   }

   @Override
   public Spliterator<T> spliterator() {
      return null;
   }
}

class DemoIterator {
   public static void main(String[] args) {
      Node node = new Node(1, new Node(2), new Node(3));
      InOrderIterator<Integer> integerInOrderIterator = new InOrderIterator<>(node);
      while(integerInOrderIterator.hasNext()) {
         System.out.print(integerInOrderIterator.next() + ",");
      }
      System.out.println();

      BinaryTree<Integer> binaryTree = new BinaryTree<>(node);
      for (Integer integer : binaryTree) {
         System.out.println(integer + ",");
      }
      System.out.println();
   }
}