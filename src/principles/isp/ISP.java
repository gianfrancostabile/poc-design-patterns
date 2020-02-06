package principles.isp;

class Document {

}

interface Machine {
   void print(Document document);
   void fax(Document document);
   void scan(Document document);
}

class MultiFunctionPrinter implements Machine {
   @Override
   public void print(Document document) {

   }

   @Override
   public void fax(Document document) {

   }

   @Override
   public void scan(Document document) {

   }
}

class OldFashionPrinter implements Machine {

   @Override
   public void print(Document document) {

   }

   @Override
   public void fax(Document document) {

   }

   @Override
   public void scan(Document document) {

   }
}

interface Printer {
   void print(Document document);
}
interface Scanner {
   void scan(Document document);
}
class JustAPrinter implements Printer {

   @Override
   public void print(Document document) {

   }
}

class Photocopier implements Printer, Scanner {

   @Override
   public void print(Document document) {

   }

   @Override
   public void scan(Document document) {

   }
}

interface MultiFunctionDevice extends Printer, Scanner {}
class MultiFunctionMachine implements MultiFunctionDevice {
   private Printer printer;
   private Scanner scanner;

   public MultiFunctionMachine(Printer printer, Scanner scanner) {
      this.printer = printer;
      this.scanner = scanner;
   }

   @Override
   public void print(Document document) {
      this.printer.print(document);
   }

   @Override
   public void scan(Document document) {
      this.scanner.scan(document);
   }
}