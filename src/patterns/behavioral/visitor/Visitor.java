package patterns.behavioral.visitor;

interface ExpressionVisitor {
   void visit(DoubleExpression doubleExpression);

   void visit(AdditionExpression additionExpression);
}

class ExpressionPrinter implements ExpressionVisitor {
   private StringBuilder stringBuilder = new StringBuilder();

   @Override
   public void visit(DoubleExpression doubleExpression) {
      this.stringBuilder.append(doubleExpression.getValue());
   }

   @Override
   public void visit(AdditionExpression additionExpression) {
      this.stringBuilder.append("(");
      additionExpression.getLeft()
         .accept(this);
      this.stringBuilder.append("+");
      additionExpression.getRight()
         .accept(this);
      this.stringBuilder.append(")");
   }

   @Override
   public String toString() {
      return this.stringBuilder.toString();
   }
}

abstract class Expression {
   public abstract void accept(ExpressionVisitor visitor);
}

class DoubleExpression extends Expression {
   private double value;

   public DoubleExpression(double value) {
      this.value = value;
   }

   @Override
   public void accept(ExpressionVisitor visitor) {
      visitor.visit(this);
   }

   public double getValue() {
      return value;
   }
}

class AdditionExpression extends Expression {
   private Expression left;
   private Expression right;

   public AdditionExpression(Expression left, Expression right) {
      this.left = left;
      this.right = right;
   }

   @Override
   public void accept(ExpressionVisitor visitor) {
      visitor.visit(this);
   }

   public Expression getLeft() {
      return left;
   }

   public Expression getRight() {
      return right;
   }
}

class ExpressionCalculator implements ExpressionVisitor {
   public double result;

   @Override
   public void visit(DoubleExpression doubleExpression) {
      result = doubleExpression.getValue();
   }

   @Override
   public void visit(AdditionExpression additionExpression) {
      additionExpression.getLeft().accept(this);
      double leftValue = result;
      additionExpression.getRight().accept(this);
      double rightValue = result;
      result = leftValue + rightValue;
   }

   public double getResult() {
      return result;
   }
}

class DemoVisitor {
   public static void main(String[] args) {
      AdditionExpression additionExpression = new AdditionExpression(
         new DoubleExpression(1),
         new AdditionExpression(
            new DoubleExpression(2),
            new DoubleExpression(3)
         )
      );

      ExpressionPrinter expressionPrinter = new ExpressionPrinter();
      expressionPrinter.visit(additionExpression);
      System.out.println(expressionPrinter);

      ExpressionCalculator expressionCalculator = new ExpressionCalculator();
      expressionCalculator.visit(additionExpression);
      System.out.println(expressionPrinter + " = " + expressionCalculator.getResult());
   }
}