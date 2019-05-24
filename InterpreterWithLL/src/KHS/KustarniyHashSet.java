package KHS;

import KustarniyLinkedList.KLL;

public class KustarniyHashSet {
    int n;
    KLL[] set;
    int size;
    public String name;

    public KustarniyHashSet(String name) {
        this.name = name;
        n = 4;
        set = new KLL[n];
        for (int i = 0; i < n; i++) {
            set[i] = new KLL();
        }
    }

    public int size() {
        size = set.length;
        return size;
    }
//Если заполнение корзины более чем на 75 процентов то флаг рехэширования
    private boolean needRehash() {
        int amount = 0;
        for (KLL list : set) {
            if (list != null && list.getSize() >= 2) {
                amount++;
            }
        }
        return amount / n >= 0.75;
    }
//рехэширование (увеличение ячеек вдвое)
    private void rehash() {
        int incBucket = n * 2;
        KLL[] updBucket = new KLL[incBucket];
        for (KLL list : set) {
            if (list != null) {
                for (int i = 0; i < list.getSize(); i++) {
                    Object element = list.getElement(i);
                    int newIndex = element.hashCode() % incBucket;
                    if (updBucket[newIndex] == null) {
                        updBucket[newIndex] = new KLL();
                    }
                    updBucket[newIndex].add((Integer) element);
                }
            }
        }
        set = updBucket;
        n = incBucket;
    }
//остаток от деления элемента на ячейку хэшсета
    private int h(Object element) {
        return  element.hashCode() % n;
    }

    public void AddHash(int element) {
        if (needRehash()) {
            rehash();
            System.out.println("REHASH! NOW YOUR HASHSET HAS SIZE OF " + set.length);
        }
        int h = h(element);
        if (!set[h].contains(element)) {
            set[h].add(element);
            System.out.println("The element " + element + " has been added in HashSet " + name + "!");
        } else System.out.println("This element " + element + " is already exists!");
    }



    public void Hashremove(int element) {
        int h = h(element);
        if (set[h] != null && set[h].contains(element)) {
            set[h].removeElement(element);
        }
    }

    public boolean contains(int element) {
      //  System.out.println(element);
        int h = h(element);
        if (set[h].contains(element))
            return true;
        return false;
    }
}
