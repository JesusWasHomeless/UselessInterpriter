package stackMachine;
import lexer.Token;
import java.util.*;
import lexer.LexGrammar;

public class StackMachine{
    private int pos=0;
    private ArrayList<Data> data = new ArrayList<>();
    private ArrayList<String> poliz = new ArrayList<>();

    public void initialization( LinkedList<Token> tokens)  //Инициализация переменных
    {
        int i;
        for (i = 0; i < tokens.size(); i++)
            if (tokens.get(i).getLex() == LexGrammar.VAR) {//Если токен типа VAR, т.е. переменная
                if (!eq(tokens.get(i).value)) {//если eq выдал false, то добавляем в таблицу переменных новую, по умолчанию равную 0
                    Data variable = new Data(tokens.get(i).value, 0);
                    data.add(variable);//Добавляем в лист со значением 0 как начальная инициализация
                }
            }
        System.out.println("INITIALIZE");
        for (Data aData : data) System.out.println(aData.name + " = " + aData.value);//вывод переменных, задействованных в программе
        System.out.println("END OF INITIALIZE");
        System.out.println("\n");
    }
    private boolean eq(String dataName) {//Если на входе имеется переменная, уже записанная в таблице переменных, то тру, иначе ложь
        for (Data aData : data) {
            if (aData.name.equals(dataName)) {
                return (true);
            }
        }
        return (false);
    }
    public void interpreter( LinkedList<Token> poliz1) {   // Интерпретатор
        for (Token aPoliz1 : poliz1) { //Добавление в лист интерпретатора элементов из полиза
            this.poliz.add(aPoliz1.value);
        }

        CheckAndInterpretation();//Вызов метода, проверяющего, что обычное выражение, а что цикл, а затем выполняющего работу интерпретации кода, вызывая методы для выражения и для циклов.
        System.out.println("\n");
        System.out.println("RESULT");
        for (Data aData : data) System.out.println(aData.name + " = " + aData.value); // вывод результата после интерпретации
        System.out.println("END OF RESULT");

    }
    private void CheckAndInterpretation() {
        while (pos < poliz.size()-1) {
            char[] chars = poliz.get(pos).toCharArray();//Переводим токены из ЭрейЛиста в ЧарЭрэй для более упрощённого поиска
           // System.out.println(chars);
            if (Character.isDigit(chars[0])&poliz.get(pos+1).equals("!"))
                return; // если конец цикла и мы встречаем ссылку с безусловным переходом "!"
            else if (Character.isLetter(chars[0])&(poliz.size()>pos+1)&(poliz.get(pos+2).equals(">")|
                    poliz.get(pos+2).equals("<") |
                    poliz.get(pos+2).equals("==")|
                    poliz.get(pos+2).equals(">=")|
                    poliz.get(pos+2).equals("<=")|
                    poliz.get(pos+2).equals("<>")))//Если натыкаемся на цикл, а именно на булевское выражение, присущее циклу
                Cycle();
            else if (Character.isLetter(chars[0]))
                UsualStmt();   // если обычное выражение
            else return; // иначе идите нафиг, улепётываем из этого метода
        }
    }
    private void UsualStmt()
    {
        Stack<String> stack = new Stack<>();
        // переменные с которыми будут происходить вычисления
        int a;
        int b;

        stack.push(poliz.get(pos)); // самая первая переменная, которая будет присваивать значение выражения
        pos++;//идём дальше по листу

        while (!poliz.get(pos).equals("=")) // пока не равно, выполняем
        {
            char[] chars = poliz.get(pos).toCharArray(); // та же песня, что и в методе выше
          //  if(poliz.get(inter).equals())
            if (Character.isLetter(chars[0])) { // если это переменная а не число, то ищем её значение в памяти (из метода инициализация) и заносим в стек
                for (Data aData : data)
                    if (aData.name.equals(poliz.get(pos)))//чекаем в таблице переменных
                        stack.push(Integer.toString(aData.value));
                pos++;
            } else if (Character.isDigit(chars[0])) { // иначе, если это просто число, то заносим в стек
                stack.push(poliz.get(pos));
                pos++;
                // иначе если это знак операции, то
            } else if (poliz.get(pos).equals("+")|
                    poliz.get(pos).equals("-")|
                    poliz.get(pos).equals("/")|
                    poliz.get(pos).equals("*"))
            {
                b = Integer.parseInt(stack.pop()); // из стека вытаскиваем 2 переменные предыдущие и выполняем соответствующую операцию и заносим обратно в стек
                a = Integer.parseInt(stack.pop());
                switch (poliz.get(pos)) {
                    case "+":
                        stack.push(Integer.toString(a + b));
                        break;
                    case "-":
                        stack.push(Integer.toString(a - b));
                        break;
                    case "*":
                        stack.push(Integer.toString(a * b));
                        break;
                    case "/":
                        stack.push(Integer.toString(a / b));
                        break;
                }
                pos++;
            }
        } if (poliz.get(pos).equals("=")) // теперь если всё же равно
    {
        b = Integer.parseInt(stack.pop()); // берём значение получившегося выражения из стека
        String word =stack.pop(); // также имя внесённой переменной в стек в самом начале
        for (Data aData : data)
            if (aData.name.equals(word))// ищем переменную в нашей памяти
            {
                aData.value = b; // и присваеваем значение выражения переменной
            }
        if (poliz.size()-1>pos) pos++;
    }
    }
    private void Cycle()
    {
      //используем переменные, которые будут участвовать в булевских выражениях (а>b, a<b и т.д.)
        int m = 0;
        int n = 0;
        for (Data aData : data)
            if (aData.name.equals(poliz.get(pos))) {
                m = aData.value; 
            } else if (aData.name.equals(poliz.get(pos + 1)))
                n = aData.value;
        char[] chars1 = poliz.get(pos).toCharArray();
        if (Character.isDigit(chars1[0]))
            m = Integer.parseInt(poliz.get(pos));
        char[] chars2 = poliz.get(pos+1).toCharArray();
        if (Character.isDigit(chars2[0]))
            n = Integer.parseInt(poliz.get(pos+1));
        pos+=2;
        // и понеслась. переход происходит по лжи, поэтому если больше не выполняется, то присваеваем ссылку нашему pos
        switch (poliz.get(pos)) {
            case ">":
                if (m <= n) {
                    pos = Integer.parseInt(poliz.get(pos + 1));
                    return;
                } else pos++;
                break;
            case "<":
                if (m >= n) {
                    pos = Integer.parseInt(poliz.get(pos + 1));
                    return;
                } else pos++;
                break;
            case "==":
                if (m != n) {
                    pos = Integer.parseInt(poliz.get(pos + 1));
                    return;
                } else pos++;
                break;
            case "<>":
                if (m == n) {
                    pos = Integer.parseInt(poliz.get(pos + 1));
                    return;
                } else pos++;
                break;
            case "<=":
                if (m > n) {
                    pos = Integer.parseInt(poliz.get(pos + 1));
                    return;
                } else pos++;
                break;
            case ">=":
                if (m < n) {
                    pos = Integer.parseInt(poliz.get(pos + 1));
                    return;
                } else pos++;
                break;
        }
        pos++;
        if (!poliz.get(pos).equals("!F")) // чек ошибки перехода по лжи
            System.out.println("Error in cycle!");

        pos++;

//пока не дошли до конца и пока не равно БП, то выполняем метод CheckAndInterpretation
        while ((poliz.size()-1>pos))
        if (!poliz.get(pos+1).equals("!"))

        CheckAndInterpretation();
        else break;

        if (poliz.size()-1>pos)
        pos = Integer.parseInt(poliz.get(pos)); // присвоение ссылки перед безусловным переходом то есть перед !
    }
}
