package patterns.behavioral.memento;

class Memento {
   private int balance;

   public Memento(int balance) {
      this.balance = balance;
   }

   public int getBalance() {
      return balance;
   }
}

class BankAccount {
   private int balance;

   public BankAccount(int balance) {
      this.balance = balance;
   }

   public Memento deposit(int amount) {
      this.balance += amount;
      return new Memento(this.balance);
   }

   public void restore(Memento memento) {
      this.balance = memento.getBalance();
   }

   @Override
   public String toString() {
      return "BankAccount{" + "balance=" + balance + '}';
   }
}

class DemoMemento {
   public static void main(String[] args) {
      BankAccount bankAccount = new BankAccount(100);
      Memento memento = bankAccount.deposit(50);
      Memento memento1 = bankAccount.deposit(25);
      System.out.println(bankAccount);

      bankAccount.restore(memento);
      System.out.println(bankAccount);
   }
}