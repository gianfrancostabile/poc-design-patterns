package patterns.behavioral.template_method;

abstract class Game {
   protected int currentPlayer;
   protected final int numberOfPlayer;

   public Game(int numberOfPlayer) {
      this.numberOfPlayer = numberOfPlayer;
   }

   public void run() {
      start();
      while(!haveWinner()) {
         takeTurn();
      }
      System.out.println(String.format("Player %d wins", getWinningPlayer()));
   }

   protected abstract int getWinningPlayer();
   protected abstract void takeTurn();
   protected abstract boolean haveWinner();
   protected abstract void start();
}

class Chess extends Game {
   private int maxTurns = 10;
   private int turn = 1;

   public Chess() {
      super(2);
   }

   @Override
   protected int getWinningPlayer() {
      return 0;
   }

   @Override
   protected void takeTurn() {
      System.out.println(String.format("Turn %d taken by player %s", turn++, currentPlayer));
      currentPlayer = (currentPlayer + 1) % 2;
   }

   @Override
   protected boolean haveWinner() {
      return turn == maxTurns;
   }

   @Override
   protected void start() {
      System.out.println("Starting a game of chess");
   }
}

class DemoTemplateMethod {
   public static void main(String[] args) {
      Chess chess = new Chess();
      chess.run();
   }
}