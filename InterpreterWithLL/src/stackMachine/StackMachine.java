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

    public void initialization( LinkedList<Token> tokens)  //������������� ����������
    {
        int i;
        if (tokens.get(0).type.equals(LexGrammar.VAR)) {
            if (!containment(tokens.get(0).value)) {
                Data variable = new Data(tokens.get(0).value, 0);
                data.add(variable);
            }
        }//���� containment ����� false, �� ��������� � ������� ���������� �����, �� ��������� ������ 0
        for (i = 1; i < tokens.size(); i++)
            if (tokens.get(i).getLex() == LexGrammar.VAR) {//���� ����� ���� VAR, �.�. ����������
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
                        data.add(variable);//��������� � ���� �� ��������� 0 ��� ��������� �������������
                    }
            }
        System.out.println("INITIALIZE");
        for (Data aData : data) System.out.println(aData.name + " = " + aData.value);//����� ����������, ��������������� � ���������
        System.out.println("END OF INITIALIZE");
        System.out.println("\n");
    }
    private boolean containment(String dataName) {//���� �� ����� ������� ����������, ��� ���������� � ������� ����������, �� ���, ����� ����
        for (Data aData : data) {
            if (aData.name.equals(dataName)) {
                return (true);
            }
        }
        return (false);
    }
    public void interpreter( LinkedList<Token> poliz1) {   // �������������
        for (Token aPoliz1 : poliz1) { //���������� � ���� �������������� ��������� �� ������
            this.poliz.add(aPoliz1.value);
        }

        CheckAndInterpretation();//����� ������, ������������, ��� ������� ���������, � ��� ����, � ����� ������������ ������ ������������� ����, ������� ������ ��� ��������� � ��� ������.
        System.out.println("\n");
        System.out.println("RESULT");
        for (Data aData : data) System.out.println(aData.name + " = " + aData.value); // ����� ���������� ����� �������������
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
                return; // ���� ����� ����� � �� ��������� ������ � ����������� ��������� "!"
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
                    poliz.get(pos+2).equals("<>")))//���� ���������� �� ����, � ������ �� ��������� ���������, �������� �����
                Cycle();

            else if (Character.isLetter(chars[0]))
                UsualStmt();   // ���� ������� ���������
            else return; // ����� ����� �����, ���������� �� ����� ������
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

    private boolean existingList(String a)//��� �� ������������� ����� ������������ �����
    {
        for (KLL aList : list)
            if (aList.name.equals(a))
                return true;
        return false;
    }

    private void interpList() {//�������� �����

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
                    if (Character.isDigit(chars[0])) {//���� ����� �� ����
                        if(list.get(s).check(Integer.parseInt(poliz.get(pos + 1)))){
                        System.out.println("List " + list.get(s).name + " contains Element " + list.get(s).getElement(Integer.parseInt(poliz.get(pos + 1))) + " on position " + Character.valueOf(chars[0]));}
                        else System.out.println("There's no any element on position " + Integer.parseInt(poliz.get(pos+1))+ " in list " + list.get(s).name);
                    }

                    else if (Character.isLetter(chars[0]))//���� ����������, ���������� �����
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
        // ���������� � �������� ����� ����������� ����������
        int a;
        int b;

        stack.push(poliz.get(pos)); // ����� ������ ����������, ������� ����� ����������� �������� ���������
        pos++;//��� ������ �� �����

        while (!poliz.get(pos).equals("=")) // ���� �� �����, ���������
        {
            char[] chars = poliz.get(pos).toCharArray(); // �� �� �����, ��� � � ������ ����
          //  if(poliz.get(inter).equals())
            if (Character.isLetter(chars[0])) { // ���� ��� ���������� � �� �����, �� ���� � �������� � ������ (�� ������ �������������) � ������� � ����
                for (Data aData : data)
                    if (aData.name.equals(poliz.get(pos)))//������ � ������� ����������
                        stack.push(Integer.toString(aData.value));
                pos++;
            } else if (Character.isDigit(chars[0])) { // �����, ���� ��� ������ �����, �� ������� � ����
                stack.push(poliz.get(pos));
                pos++;
                // ����� ���� ��� ���� ��������, ��
            } else if (poliz.get(pos).equals("+")|
                    poliz.get(pos).equals("-")|
                    poliz.get(pos).equals("/")|
                    poliz.get(pos).equals("*"))
            {
                b = Integer.parseInt(stack.pop()); // �� ����� ����������� 2 ���������� ���������� � ��������� ��������������� �������� � ������� ������� � ����
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
        } if (poliz.get(pos).equals("=")) // ������ ���� �� �� �����
    {
        b = Integer.parseInt(stack.pop()); // ���� �������� ������������� ��������� �� �����
        String word =stack.pop(); // ����� ��� �������� ���������� � ���� � ����� ������
        for (Data aData : data)
            if (aData.name.equals(word))// ���� ���������� � ����� ������
            {
                aData.value = b; // � ����������� �������� ��������� ����������
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
        // � ���������. ������� ���������� �� ���, ������� ���� ������ �� �����������, �� ����������� ������ ������ pos
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
        if (!poliz.get(pos).equals("!F")) // ��� ������ �������� �� ���
            System.out.println("Error in cycle!");

        pos++;

//���� �� ����� �� ����� � ���� �� ����� ��, �� ��������� ����� CheckAndInterpretation
        while ((poliz.size()-1>pos))
        if (!poliz.get(pos+1).equals("!"))

        CheckAndInterpretation();
        else break;

        if (poliz.size()-1>pos)
        pos = Integer.parseInt(poliz.get(pos)); // ���������� ������ ����� ����������� ��������� �� ���� ����� !
    }
}