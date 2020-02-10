package patterns.behavioral.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

enum Action {
   DEPOSIT, WITHDRAW
}

interface Command {
   void call();

   void undo();
}

class BankAccount {
   private int balance;
   private int overdraftLimit = -500;

   public void deposit(int amount) {
      this.balance += amount;
      System.out.println(String.format("Deposited %d, balance is now %d", amount, balance));
   }

   public boolean withDraw(int amount) {
      boolean success = false;
      if (this.balance - amount >= overdraftLimit) {
         this.balance -= amount;
         System.out.println(String.format("Withdrew %d, balance is now %d", amount, balance));
         success = true;
      }
      return success;
   }

   @Override
   public String toString() {
      return "BankAccount{" + "balance=" + balance + ", overdraftLimit=" + overdraftLimit + '}';
   }
}

class BankAccountCommand implements Command {
   private BankAccount bankAccount;
   private Action action;
   private int amount;
   private boolean succeeded;

   public BankAccountCommand(BankAccount bankAccount, Action action, int amount) {
      this.bankAccount = bankAccount;
      this.action = action;
      this.amount = amount;
   }

   @Override
   public void call() {
      switch (this.action) {
         case DEPOSIT:
            this.succeeded = true;
            this.bankAccount.deposit(this.amount);
            break;
         case WITHDRAW:
            this.succeeded = this.bankAccount.withDraw(this.amount);
            break;
      }
   }

   @Override
   public void undo() {
      if (this.succeeded) {
         switch (this.action) {
            case DEPOSIT:
               this.bankAccount.withDraw(this.amount);
               break;
            case WITHDRAW:
               this.bankAccount.deposit(this.amount);
               break;
         }
      }
   }
}

class DemoCommand {
   public static void main(String[] args) {
      BankAccount bankAccount = new BankAccount();
      System.out.println(bankAccount);

      List<Command> bankAccountCommandList = Arrays.asList(new BankAccountCommand(bankAccount, Action.DEPOSIT, 100),
         new BankAccountCommand(bankAccount, Action.WITHDRAW, 500));

      for (Command command : bankAccountCommandList) {
         command.call();
         System.out.println(bankAccount);
      }

      Collections.reverse(bankAccountCommandList);
      for (Command command : bankAccountCommandList) {
         command.undo();
         System.out.println(bankAccount);
      }
   }
}