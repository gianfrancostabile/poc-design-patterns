package patterns.behavioral.null_object;

import java.lang.reflect.Proxy;
import java.util.Objects;

interface Log {
   void info(String message);

   void warn(String message);
}

class ConsoleLog implements Log {

   @Override
   public void info(String message) {

   }

   @Override
   public void warn(String message) {

   }
}

final class NullLog implements Log {

   @Override
   public void info(String message) {

   }

   @Override
   public void warn(String message) {

   }
}

class BankAccount {
   private Log log;
   private int balance = 0;

   public BankAccount(Log log) {
      this.log = Objects.isNull(log) ? new NullLog() : log;
   }

   public void deposit(int amount) {
      this.balance += amount;
      log.info("Deposited " + amount);
   }

   public void withdraw(int amount) {
      if (this.balance - amount >= 0) {
         this.balance -= amount;
         log.info("Withdrew " + amount);
      } else {
         log.warn("You cannot withdrew an amount greatest than " + this.balance);
      }
   }
}

class DemoNullObject {
   public static void main(String[] args) {
      BankAccount bankAccount = new BankAccount(null);

      bankAccount.deposit(200);
      bankAccount.withdraw(100);
   }
}