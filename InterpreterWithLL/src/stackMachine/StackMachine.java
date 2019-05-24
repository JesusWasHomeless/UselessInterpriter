package stackMachine;
import KustarniyLinkedList.KLL;
import KHS.KustarniyHashSet;
import lexer.Token;
import java.util.*;
import lexer.LexGrammar;

public class StackMachine{
    private int pos=0;
    private ArrayList<Data> data = new ArrayList<>();
    private ArrayList<String> poliz = new ArrayList<>();
    private ArrayList<KLL> list = new ArrayList<>();
    ArrayList<KustarniyHashSet>  hash = new ArrayList<>();

    public void initialization( LinkedList<Token> tokens)  //Инициализация переменных
    {
        int i;
        if (tokens.get(0).type.equals(LexGrammar.VAR)) {
            if (!containment(tokens.get(0).value)) {
                Data variable = new Data(tokens.get(0).value, 0);
                data.add(variable);
            }
        }//если containment выдал false, то добавляем в таблицу переменных новую, по умолчанию равную 0
        for (i = 1; i < tokens.size(); i++)
            if (tokens.get(i).getLex() == LexGrammar.VAR) {//Если токен типа VAR, т.е. переменная
                if (!containment(tokens.get(i).value))
                    if (!tokens.get(i-2).value.equals("LIST")&
                            !tokens.get(i-2).value.equals("ADD")&
                            !tokens.get(i-2).value.equals("REMOVE")&
                            !tokens.get(i-2).value.equals("SIZE")&
                            !tokens.get(i-2).value.equals("ELEMENT")&
                            !tokens.get(i-2).value.equals("SUPERREMOVE")&
                            !tokens.get(i-2).value.equals("HASHSET")&
                            !tokens.get(i-2).value.equals("HASHADD")&
                            !tokens.get(i-2).value.equals("HASHREMOVE")&
                            !tokens.get(i-2).value.equals("HASHCONTAINS")&
                            !tokens.get(i-2).value.equals("HASHSIZE")){
                        Data variable = new Data(tokens.get(i).value, 0);
                        data.add(variable);//Добавляем в лист со значением 0 как начальная инициализация
                    }
            }
        System.out.println("INITIALIZE");
        for (Data aData : data) System.out.println(aData.name + " = " + aData.value);//вывод переменных, задействованных в программе
        System.out.println("END OF INITIALIZE");
        System.out.println("\n");
    }
    private boolean containment(String dataName) {//Если на входе имеется переменная, уже записанная в таблице переменных, то тру, иначе ложь
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
            char[] chars = poliz.get(pos).toCharArray();
     //       if (pos!=poliz.size()&pos+1<poliz.size()-1)

               // pos++;
                //else if (poliz.get(pos + 1).equals("ADD"))
                //    interpAdd();
               // else if (poliz.get(pos + 1).equals("REMOVE"))
                //    interpRemove();
               // else if ((poliz.get(pos + 1).equals("SIZE")))
               //     interpSize();
               // else if (poliz.get(pos + 1).equals("ELEMEMT"))
               //     interpElement();
             if (Character.isDigit(chars[0])&poliz.get(pos+1).equals("!"))
                return; // если конец цикла и мы встречаем ссылку с безусловным переходом "!"
             else if (poliz.get(pos + 1).equals("LIST"))
                 interpList();
             else if (poliz.get(pos + 1).equals("SIZE"))
                 interpSize();
             else if (poliz.get(pos + 1).equals("REMOVE"))
                 interpRemove();
             else if (poliz.get(pos + 1).equals("HASHSET"))
                 interpHash();
             else if (poliz.get(pos + 1).equals("HASHSIZE"))
                 interpHashSize();
             else if (poliz.get(pos + 2).equals("HASHREMOVE"))
                 interpHashRemove();
             else if (poliz.get(pos + 2).equals("ADD"))
                 interpAdd();
             else if (poliz.get(pos + 2).equals("ELEMENT"))
                 interpElement();
             else if (poliz.get(pos + 2).equals("SUPERREMOVE"))
                 interpSuperRemove();
             else if (poliz.get(pos + 2).equals("HASHADD"))
                 interpHashAdd();

             else if (poliz.get(pos + 2).equals("HASHCONTAINS"))
                 interpHashContain();


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

    public boolean existHash (String name)
    {
        for(int t=0;t<hash.size();t++)
            if (hash.get(t).name.equals(name))
                return true;
        return false;
    }

    public void interpHashSize(){
        if (existHash(poliz.get(pos)))
    {
        for (int s=0;s<hash.size();s++)
            if (poliz.get(pos).equals(hash.get(s).name))
                System.out.println("Size of hash " + hash.get(s).name + " is " + hash.get(s).size());

        // System.out.println(a.getElement(0));
    }

        else System.out.println("This HashSet named " + poliz.get(pos) + " doesn't exist");
    pos+=2;

}
    public void interpHash ()
    {
        if (!existHash(poliz.get(pos)))
        {
            KustarniyHashSet a = new KustarniyHashSet(poliz.get(pos));
            a.name = poliz.get(pos);
            hash.add(a);
            System.out.println("You have created new HashSet named " + a.name);
        }
        else System.out.println("This HashSet " + poliz.get(pos)+ " is already exists");
        pos+=2;
    }

    public void interpHashAdd ()
    {
        char[] chars = poliz.get(pos+1).toCharArray();
        if (existHash(poliz.get(pos)))
        {
            for (int s=0;s<hash.size();s++)
                if (poliz.get(pos).equals(hash.get(s).name))
                    if (Character.isDigit(chars[0])) {
                        hash.get(s).AddHash(Integer.parseInt(poliz.get(pos + 1)));
                    } else  if (Character.isLetter(chars[0]))
                        for (int h = 0; h < data.size(); h++)
                            if (data.get(h).name.equals(poliz.get(pos+1)))
                                hash.get(s).AddHash(data.get(h).value);

        }
        else System.out.println("This HashSet " + poliz.get(pos)+ " isn't exist");
        pos+=3;
    }

    public void interpHashRemove ()
    {
        char[] chars = poliz.get(pos+1).toCharArray();
        if (existHash(poliz.get(pos))) {
            for (int s = 0; s < hash.size(); s++)
                if (poliz.get(pos).equals(hash.get(s).name))
                    if (Character.isDigit(chars[0])) {
                        hash.get(s).Hashremove(Integer.parseInt(poliz.get(pos + 1)));
                        System.out.println("The element " + Integer.parseInt(poliz.get(pos + 1)) + " has been removed from HashSet " + hash.get(s).name);
                    } else if (Character.isLetter(chars[0]))
                        for (int h = 0; h < data.size(); h++) {
                            if (data.get(h).name.equals(poliz.get(pos + 1)))
                                hash.get(s).Hashremove(data.get(h).value);
                            System.out.println("The element " + data.get(h).name + " = " + data.get(h).value + " has been removed from HashSet " + hash.get(s).name);
                        }
        }
        else System.out.println("This HashSet " + poliz.get(pos)+ " isn't exist");
        pos+=3;
    }
    public void interpHashContain ()
    {
        char[] chars = poliz.get(pos+1).toCharArray();
        if (existHash(poliz.get(pos)))
        {
            for (int s=0;s<hash.size();s++)
                if (poliz.get(pos).equals(hash.get(s).name))
                    if (Character.isDigit(chars[0])) {
                        System.out.println("This Element says that existing of "+poliz.get(pos + 1)+" in set is "+ hash.get(s).contains(Integer.parseInt(poliz.get(pos + 1))));
                    } else if (Character.isLetter(chars[0]))
                        for (int h = 0; h < data.size(); h++)
                            if (data.get(h).name.equals(poliz.get(pos + 1)))
                                System.out.println("This element says that existing of "+data.get(h).name+ " = " + data.get(h).value + " in set is "+ hash.get(h).contains(data.get(h).value));
        }    else System.out.println("This HashSet " + poliz.get(pos)+ " isn't exist");
        pos+=3;
    }

    private boolean existingList(String a)//Чек на существование ранее объявленного листа
    {
        for (KLL aList : list)
            if (aList.name.equals(a))
                return true;
        return false;
    }

    private void interpList() {//Создание листа

        if (!existingList(poliz.get(pos)))
        {
            KLL a = new KLL();
            a.name = poliz.get(pos);
            list.add(a);
            System.out.println("You have created new LinkedList named " + a.name);
        }

        else System.out.println("This list named " + poliz.get(pos) + " is already exists!");
        pos+=2;

    }

    private void interpAdd()
    {

        char[] chars = poliz.get(pos+1).toCharArray();
        if (existingList(poliz.get(pos)))
        {
            for (int s=0;s<list.size();s++)
                if (poliz.get(pos).equals(list.get(s).name))
                    if (Character.isDigit(chars[0])) {
                        list.get(s).add(Integer.parseInt(poliz.get(pos + 1)));
                        System.out.println("New element " + Integer.parseInt(poliz.get(pos + 1)) + " has been added in list " + poliz.get(pos) + "!");
                    } else if (Character.isLetter(chars[0]))
                        for (int h = 0; h < data.size(); h++)
                            if (data.get(h).name.equals(poliz.get(pos + 1))) {
                                list.get(s).add(data.get(h).value);
                                System.out.println("New element " + data.get(s).name + " which equals " + data.get(h).value + " has been added in list " + poliz.get(pos) + "!");
                            }
        }
        else System.out.println("This list named " + poliz.get(pos) + " doesn't exist");
        pos+=3;

    }
    private void interpSuperRemove()
    {

        char[] chars = poliz.get(pos+1).toCharArray();
        if (existingList(poliz.get(pos)))
        {
            for (int s=0;s<list.size();s++)
                if (poliz.get(pos).equals(list.get(s).name))
                    if (Character.isDigit(chars[0])) {
                        list.get(s).removeElement(Integer.parseInt(poliz.get(pos + 1)));
                        System.out.println("The element " + Integer.parseInt(poliz.get(pos + 1)) + " has been deleted from list " + poliz.get(pos) + "!");
                    } else if (Character.isLetter(chars[0]))
                        for (int h = 0; h < data.size(); h++)
                            if (data.get(h).name.equals(poliz.get(pos + 1))) {
                                list.get(s).removeElement(data.get(h).value);
                                System.out.println("The element " + data.get(s).name + " which equals " + data.get(h).value + " has been deleted from list " + poliz.get(pos) + "!");
                            }
        }
        else System.out.println("This list named " + poliz.get(pos) + " doesn't exist");
        pos+=3;

    }

    private void interpRemove() {

        if (existingList(poliz.get(pos))) {
            for (int s=0;s<list.size();s++)
                if (poliz.get(pos).equals(list.get(s).name)) {
                    list.get(s).remove();
                    System.out.println("You have removed last node from your Linked List " + poliz.get(pos) + "!");
                }
        }

        else System.out.println("This list named " + poliz.get(pos) + " doesn't exist");
        pos+=2;

    }

    private void interpSize() {
        if (existingList(poliz.get(pos)))
        {
            for (int s=0;s<list.size();s++)
                if (poliz.get(pos).equals(list.get(s).name))
                    System.out.println("Size of list " + list.get(s).name + " is " + list.get(s).getSize());

            // System.out.println(a.getElement(0));
        }

        else System.out.println("This list named " + poliz.get(pos) + " doesn't exist");
        pos+=2;


    }

    private void interpElement()
    {
        char[] chars = poliz.get(pos+1).toCharArray();
        if (existingList(poliz.get(pos)))
        {
            for (int s=0;s<list.size();s++)
                if (poliz.get(pos).equals(list.get(s).name))
                    if (Character.isDigit(chars[0])) {//если число на вход
                        if(list.get(s).check(Integer.parseInt(poliz.get(pos + 1)))){
                        System.out.println("List " + list.get(s).name + " contains Element " + list.get(s).getElement(Integer.parseInt(poliz.get(pos + 1))) + " on position " + Character.valueOf(chars[0]));}
                        else System.out.println("There's no any element on position " + Integer.parseInt(poliz.get(pos+1))+ " in list " + list.get(s).name);
                    }

                    else if (Character.isLetter(chars[0]))//если переменная, содержашая число
                        for (int h = 0; h < data.size(); h++)
                           if (data.get(h).name.equals(poliz.get(pos + 1)))
                               if(list.get(s).check(Integer.parseInt(poliz.get(pos + 1)))){
                               System.out.println("List " + list.get(h).name + " contains Element " + list.get(h).getElement(data.get(h).value)+ " on position " + Character.valueOf(chars[0]));}
                               else System.out.println("There's no any element on position " + Integer.parseInt(poliz.get(pos+1))+ " in list " + list.get(s).name);
        }
        else System.out.println("This list named " + poliz.get(pos) + " doesn't exist");
        pos+=3;

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