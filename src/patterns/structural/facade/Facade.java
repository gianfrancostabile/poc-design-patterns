package patterns.structural.facade;

import java.util.ArrayList;
import java.util.List;

class Buffer {
   private char[] characters;
   private int lineWidth;

   public Buffer(int lineHeight, int lineWidth) {
      this.lineWidth = lineWidth;
      this.characters = new char[lineWidth * lineHeight];
   }

   public char charAt(int x, int y) {
      return this.characters[y * this.lineWidth + x];
   }
}

class Viewport {
   private final Buffer buffer;
   private final int offsetX;
   private final int offsetY;

   public Viewport(Buffer buffer, int offsetX, int offsetY) {
      this.buffer = buffer;
      this.offsetX = offsetX;
      this.offsetY = offsetY;
   }

   public char charAt(int x, int y) {
      return this.buffer.charAt(x + this.offsetX, y + this.offsetY);
   }
}

class Console {
   private List<Viewport> viewports;
   private int width;
   private int height;

   private Console(int width, int height) {
      this.viewports = new ArrayList<>();
      this.width = width;
      this.height = height;
   }

   public void addViewport(Viewport viewport) {
      this.viewports.add(viewport);
   }

   public static Console newConsole(int width, int height) {
      Buffer buffer = new Buffer(width, height);
      Viewport viewport = new Viewport(buffer, 0, 0);
      Console console = new Console(width, height);
      console.addViewport(viewport);
      return console;
   }

   public void render() {
      for (int y = 0; y < height; y++) {
         for (int x = 0; x < width; x++) {
            for (Viewport viewport : this.viewports) {
               System.out.print(viewport.charAt(x, y));
            }
         }
         System.out.println();
      }
   }
}

class DemoFacade {
   public static void main(String[] args) {
      Console console = Console.newConsole(30, 20);
      console.render();
   }
}
