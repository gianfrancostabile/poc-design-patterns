package patterns.behavioral.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

interface Element {
   int eval();
}

class Token {
   Type type;
   String text;
   public Token(Type type, String text) {
      this.type = type;
      this.text = text;
   }

   @Override
   public String toString() {
      return "`" + text + "`";
   }

   public enum Type {
      INTEGER, PLUS, MINUS, LPAREN, RPAREN
   }
}

class Integer implements Element {
   int value;

   public Integer(int value) {
      this.value = value;
   }

   public void setValue(int value) {
      this.value = value;
   }

   @Override
   public int eval() {
      return value;
   }
}

class BinaryOperation implements Element {
   Type type;
   Element left, right;

   @Override
   public int eval() {
      switch (type) {
         case ADDITION:
            return left.eval() + right.eval();
         case SUBTRACTION:
            return left.eval() - right.eval();
         default:
            return 0;
      }
   }

   public enum Type {
      ADDITION, SUBTRACTION
   }
}

class Lexing {

   static Element parse(List<Token> tokens) {
      BinaryOperation result = new BinaryOperation();
      boolean haveLHS = false;

      for (int i = 0; i < tokens.size(); i++) {
         Token token = tokens.get(i);
         switch (token.type) {
            case INTEGER:
               Integer integer = new Integer(java.lang.Integer.parseInt(token.text));
               if (!haveLHS) {
                  result.left = integer;
                  haveLHS = true;
               } else
                  result.right = integer;
               break;
            case PLUS:
               result.type = BinaryOperation.Type.ADDITION;
               break;
            case MINUS:
               result.type = BinaryOperation.Type.SUBTRACTION;
               break;
            case LPAREN:
               int j = i;
               for (; j < tokens.size(); ++j) {
                  if (tokens.get(j).type.equals(Token.Type.RPAREN))
                     break;
               }
               List<Token> subTokens = tokens.stream()
                  .skip(i + 1)
                  .limit(j - i - 1)
                  .collect(Collectors.toList());
               Element element = parse(subTokens);

               if (!haveLHS) {
                  result.left = element;
                  haveLHS = true;
               } else
                  result.right = element;
               i = j++;
               break;

         }
      }
      return result;
   }

   static List<Token> lex(String input) {
      List<Token> result = new ArrayList();
      for (int i = 0; i < input.length(); i++) {
         switch (input.charAt(i)) {
            case '+':
               result.add(new Token(Token.Type.PLUS, "+"));
               break;
            case '-':
               result.add(new Token(Token.Type.MINUS, "-"));
               break;
            case '(':
               result.add(new Token(Token.Type.LPAREN, "("));
               break;
            case ')':
               result.add(new Token(Token.Type.RPAREN, ")"));
               break;
            default:
               StringBuilder stringBuilder = new StringBuilder(input.charAt(i));
               for (int j = i; j < input.length(); j++) {
                  if (Character.isDigit(input.charAt(j))) {
                     stringBuilder.append(input.charAt(j));
                     i = j;
                  } else {
                     result.add(new Token(Token.Type.INTEGER, stringBuilder.toString()));
                     break;
                  }
               }
               break;
         }
      }
      return result;
   }
}


class DemoInterpreter {
   public static void main(String[] args) {
      final String input = "(13+4)-(12+1)";
      List<Token> tokens = Lexing.lex(input);
      System.out.println("tokens = " + tokens.stream()
         .map(Token::toString)
         .collect(Collectors.joining("\t")));

      Element results = Lexing.parse(tokens);
      System.out.println(input + " = " + results.eval());
   }
}