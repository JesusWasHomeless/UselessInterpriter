package poliz;

import lexer.LexGrammar;
import lexer.Token;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Poliz {
    //REVERSE POLISH NOTATION---------------------------------
//--------------------------------------------------------
/*Надоело по-английски писать :D В общем, логика такова, что мы создаём лист, в котором будет полиз,
и лист связанный с очередью, чекаем его на ключевые слова: если их нет, то используется обычный полиз (PolizExpr),
иначе в зависимости от ключевого слова используется метод, работающий специально под тот или иной полиз.
На вход подаётся код, написанный в файле test.txt. Код разбивается на токены и проверяется в парсере, после этого идёт лишь
разрешение на работу полиза. При циклах используются переходы по лжи <number> !F и безусловные переходы <number> !. При
обычном полизе выражение обращается в польскую инверсную запись (как ни странно, в полиз :D). В стек помещаются знаки бинарных
операций.
Пример работы полиза:
Вход: A = A+3*(2+4*(3+2/1));
Выход: A, A, 3, 2, 4, 3, 2, 1, /, +, *, +, *, +, =
Вход: WHILE(A>Q) START Q = Z+1; END IF(X>C)START D = 6; END FOR ( A=5;B<4;C=C+1) START D = 5; END
Выход: A, Q, >, 12, !F, Q, Z, 1, +, =, 0, !, X, C, >, 20, !F, D, 6, =, A, 5, =, B, 4, <, C, C, 1, +, =, 38, !F, D, 5, =, 20, ! */
    public  LinkedList<Token> poliz = new LinkedList<>();
    //public ArrayList<Token> sm = new ArrayList<>();
    int j;
    public LinkedList<Token> Poliz(LinkedList<Token> input) {
        Token token = input.peek();
        while (!input.isEmpty()) {
            if (token.type == LexGrammar.FOR_W){
                PolizFor(input); }

            if (token.type == LexGrammar.WHILE_W){
                PolizWhile(input);}

            if (token.type == LexGrammar.IF_W){
                PolizIF(input);}
            if(token.type == LexGrammar.ELSE_W){
                PolizELSE(input);
            }
            else PolizExpr(input);

        }
        int i = -1;
        for (Token t : poliz) {
            i++;
            System.out.println(i + " " + t);
        }
       // for(Token t : poliz){
       //     sm.add(t);
      // }
        System.out.println("Your poliz is:" + poliz);
        return poliz;
    }

    private void PolizFor(Queue<Token> input) {
        Queue<Token> boolExprFor = new LinkedList<>();
        input.poll();
        Token token = input.poll();
        int index = poliz.size();
        while (token.type != LexGrammar.START_W) {
            if (token.type != LexGrammar.SPACE)
                boolExprFor.add(token);
            token = input.poll();
        }
        PolizExpr(boolExprFor);
        poliz.add(index+6,(new Token(LexGrammar.START_W,"START")));
        poliz.add(index+7,(new Token(LexGrammar.JUMP_F,"!F")));
        PolizCycle(input);
        JUMP(index+3);
    }
    private void PolizWhile(Queue<Token> input) {
        Queue<Token> boolExprWhile = new LinkedList<>();
        input.poll();
        Token token = input.poll();
        int index = poliz.size();
        while (token.type != LexGrammar.START_W) {
            if (token.type != LexGrammar.SPACE)
                boolExprWhile.add(token);
            token = input.poll();
        }
        PolizExpr(boolExprWhile);
        poliz.add(new Token(LexGrammar.START_W, "START"));
        poliz.add(new Token(LexGrammar.JUMP_F, "!F"));
        PolizCycle(input);
        JUMP(index);
    }
    private void PolizIF(Queue<Token> input) {
        j = poliz.size();
        //System.out.println(j);
        Queue<Token> boolExprIF = new LinkedList<>();
        input.poll();
        Token token = input.poll();
        int index = poliz.size();
        while (token.type != LexGrammar.START_W) {
            if (token.type != LexGrammar.SPACE)
                boolExprIF.add(token);
            token = input.poll();
        }
        PolizExpr(boolExprIF);
        poliz.add(new Token(LexGrammar.START_W, "START"));
        poliz.add(new Token(LexGrammar.JUMP_F, "!F"));
        PolizCycle(input);
        int end = poliz.size();
       // System.out.println(end);
        poliz.get(index + 3).setValue(String.valueOf(end));

    }
    private void PolizELSE(Queue<Token> input) {
        int end = poliz.size();
        int i;
        poliz.add(new Token(LexGrammar.JUMP_F, String.valueOf((end))));
        poliz.add(new Token(LexGrammar.JUMP, "!"));
        PolizCycle(input);
        i = poliz.size();
        poliz.get(j+3).setValue(String.valueOf(end+2));
        poliz.get(end).setValue(String.valueOf(i));
    }
    private void PolizCycle(Queue<Token> input) {
        Queue<Token> ExprCycle = new LinkedList<>();
        Token token = input.peek();
        while (token.type != LexGrammar.END_W) {
            if ((token.type != LexGrammar.WHILE_W) && (token.type != LexGrammar.FOR_W)&&(token.type != LexGrammar.IF_W)&&(token.type != LexGrammar.ELSE_W)) {
                ExprCycle.add(token);
            }
            else if ((token.type == LexGrammar.IF_W)){
                PolizExpr(ExprCycle);
                PolizIF(input);
            }
            else if ((token.type == LexGrammar.ELSE_W)){
                //PolizExpr(ExprCycle);
                PolizELSE(input);
            }
            else if ((token.type == LexGrammar.WHILE_W)) {
                PolizExpr(ExprCycle);
                PolizWhile(input);
            }

            else if ((token.type == LexGrammar.FOR_W)) {
                PolizExpr(ExprCycle);
                PolizFor(input);
            }

            token = input.poll();
        }
        PolizExpr(ExprCycle);
    }
    private void JUMP(int start){
        poliz.add(new Token(LexGrammar.JUMP_F, String.valueOf(start)));
        poliz.add(new Token(LexGrammar.JUMP, "!"));
        int end = poliz.size();
        poliz.get(start+3).setValue(String.valueOf(end));

    }

    private void PolizExpr(Queue<Token> input) {
        Stack<Token> stack = new Stack<>();
        Token token = input.peek();
        while (!input.isEmpty()) {
            if (token.type == LexGrammar.FOR_W) {
                PolizFor(input);
            }
            if (token.type == LexGrammar.IF_W){
                PolizIF(input);
            }
            if (token.type == LexGrammar.ELSE_W){
                PolizELSE(input);
            }

            if (token.type == LexGrammar.WHILE_W) {
                PolizWhile(input);
            }


            if ((token.type == LexGrammar.WHILE_W) || (token.type == LexGrammar.IF_W) || (token.type == LexGrammar.FOR_W) || (token.type == LexGrammar.ELSE_W)) {
                break;
            }

            token = input.poll();
            if ((token.type == LexGrammar.VAR || token.type == LexGrammar.NUMBER)) {
                poliz.add(token);
            }
           // if (token.type == LexGrammar.TYPE_W) {
          //     stack.add(token);
           //  }

         //   if (token.type == LexGrammar.TYPE_LIST) {
               // poliz.add(token);
              //  poliz.add(stack.pop());
          //  }
            if (token.type == LexGrammar.OP_MUL ||
                    token.type == LexGrammar.OP_DIV ||
                    token.type == LexGrammar.OP_PLUS ||
                    token.type == LexGrammar.OP_MINUS ||
                    token.type == LexGrammar.COMPARE_EQUALS ||
                    token.type == LexGrammar.COMPARE_MORE ||
                    token.type == LexGrammar.COMPARE_MORE_EQ ||
                    token.type == LexGrammar.COMPARE_LESS ||
                    token.type == LexGrammar.COMPARE_LESS_EQ ||
                    token.type == LexGrammar.COMPARE_NON_EQUALS ||
                    token.type == LexGrammar.ASSIGN_OP ||
                    token.type == LexGrammar.TYPE_LIST_ADD||
                    token.type == LexGrammar.TYPE_LIST||
                    token.type == LexGrammar.TYPE_LIST_REMOVE||
                    token.type == LexGrammar.TYPE_LIST_REMOVE_ELEM||
                    token.type == LexGrammar.TYPE_LIST_ELEMENTS||
                    token.type == LexGrammar.TYPE_LIST_SIZE||
                    token.type == LexGrammar.TYPE_SET||
                    token.type == LexGrammar.TYPE_SET_ADD||
                    token.type == LexGrammar.TYPE_SET_REMOVE||
                    token.type == LexGrammar.TYPE_SET_CONTAINS||
                    token.type == LexGrammar.TYPE_SET_SIZE){
                if (!stack.empty()) {
                    while (getPriority(token.value) < getPriority(stack.peek().value)) {
                        poliz.add(stack.pop());
                    }
                }
                stack.push(token);

            }

            if (token.type == LexGrammar.PARENTHESIS_OP) {
                stack.push(token);
            }

            if (token.type == LexGrammar.PARENTHESIS_CL) {
                if (!stack.empty()) {
                    while (!stack.empty() && stack.peek().type != LexGrammar.PARENTHESIS_OP) {
                        poliz.add(stack.pop());
                    }
                    if (!stack.empty() && stack.peek().type == LexGrammar.PARENTHESIS_OP) {
                        stack.pop();
                    }
                }
            }

            if ((token.type == LexGrammar.SEMICOLON)) {
                while (!stack.empty()) {
                    poliz.add(stack.pop());
                }
            }
            if ((token.type == LexGrammar.DIV)) {
                if (!stack.empty()) {
                    poliz.add(stack.pop());
                }
            }
        }

        while (!stack.empty()) {
            poliz.add(stack.pop());
        }
    }

    private int getPriority(String bin_op) {
        switch (bin_op) {
            case "*":
            case "/":
                return 3;
            case "+":
            case "-":
                return 2;
            case ">":
            case ">=":
            case "<":
            case "<=":
            case "==":
            case "<>":
                return 1;
            default:
                return 0;
        }
    }

}