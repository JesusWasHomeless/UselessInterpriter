package KustarniyLinkedList;

public class KLL {
    Wrapper first;
    Wrapper last;
    int size = 0;
    public String name;

    public int getSize() {
        return size;
    }

    public String remove() {
        String element = null;
        if (first != null) {
            size--;
            element = "Element " + last.value + " has been removed";
            System.out.println(element);
            if (last.prev != null) {
                last.prev.next = null;
                last = last.prev;
            } else {
                first = null;
                last = null;
            }
        }

        return element;
    }

    public void removeElement(int element) {
        if (first != null) //Если лист не пустой
        {
            if (size == 1 & first.value == element) //если 1 элемент в листе
            {
                first = null;
                last = null;
                size--;
                return;
            } else if (first.value == element) // удаление первого элемента
            {
                first = first.next;
                size--;
                return;
            } else if (last.value == element) // удаление последнего элемента
            {
                last = last.prev;
                size--;
                return;
            } else {
                Wrapper node = first.next; // удаление элемента среди листа
                size--;
                while (node.next != null) {

                    if (node.value == element) {
                        node.prev.next = node.next;
                        node.next.prev = node.prev;
                        return;
                    }
                    node = node.next;
                }
            }
        } else System.out.println("Element isn't exist");

    }

    //Метод добавления элемента С КОНЦА
    public void add(int value) {
        size++;
        if (first == null) //если лист пустой изначально
        {
            first = new Wrapper(null, null, value);//то добавляем элемент в лист, при этом приравниваем first и ласт, т.к. это первый элемент
            last = first;
        } else {
            Wrapper node = new Wrapper(null, last, value);//Затем сдвигаем первый элемент вглубь листа и на его место добавляем новый, и т.д.
            this.last.next = node;
            this.last = node;
        }
    }


    public boolean check(int index) {
        while (index >= 0) {
            if (index < size) {
                return true;
            } else return false;
        }
        return false;
    }

    public int getElement(int index) {
        int element = 0;
        if (index < size) {
            Wrapper node = first;
            if (index == 0) return (first.value);
            else
                while (index > 0) {
                    index--;
                    node = node.next;
                    element = node.value;
                }

        }
        return element;
    }
    public boolean contains(int object) {
        for (Wrapper current = first; current != null; current = current.getNext()) {
            if (current.value == object) {
                    return true;
            }
        }
        return false;
    }
}
