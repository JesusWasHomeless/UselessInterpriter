package KustarniyLinkedList;

class Wrapper {
    Wrapper next;
    Wrapper prev;
    int value;

 Wrapper(Wrapper next, Wrapper prev, int value) {
        this.next = next;
        this.prev = prev;
        this.value = value;
    }

    public Wrapper getNext() {
        return next;
    }
}