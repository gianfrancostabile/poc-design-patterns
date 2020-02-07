package patterns.structural.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

interface SomeNeurons extends Iterable<Neuron> {
   default void connectTo(SomeNeurons other) {
      if (this != other) {
         for (Neuron from : this) {
            for (Neuron to : other) {
               from.out.add(to);
               to.in.add(from);
            }
         }
      }
   }
}

class Neuron implements SomeNeurons {
   public ArrayList<Neuron> in;
   public ArrayList<Neuron> out;

   @Override
   public Iterator<Neuron> iterator() {
      return Collections.singleton(this).iterator();
   }

   @Override
   public void forEach(Consumer<? super Neuron> action) {
      action.accept(this);
   }

   @Override
   public Spliterator<Neuron> spliterator() {
      return Collections.singleton(this).spliterator();
   }
}

class NeuronLayer extends ArrayList<Neuron> {

}

class DemoNeuralNetwork {
   public static void main(String[] args) {
   }
}