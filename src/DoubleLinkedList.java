/*
Author: Robert Rodriguez. Student at FIU.
Date: 9/5/2019
*/
class DoubleLinkedList<T> {

    protected Node<T> head, tail;
    private int size;


    public int getSize() {
        return size;
    }

    DoubleLinkedList() {

        head = null;
        tail = null;
        size = 0;

    }


    Node addFront(T newValue) {

        Node<T> node = new Node<>(newValue);

        if(head == null) {
            head = node;
            tail = node;
            size += 1;
            return head;

        } else {

            node.next = head;
            head.back = node;
            head = node;
            size += 1;
            return head;
        }
    }

    Node addBack(T newValue) {

        Node<T> node = new Node<>(newValue);

        if(head == null) {
            head = node;
            tail = node;
            size += 1;
            return head;
        } else {

            tail.next = node;
            node.back = tail;
            tail = node;
            size += 1;
            return tail;
        }
    }

    /**
     * This method iterates through list, in between wormholes. Once you appear in a wormhole, you still need to move
     * next if index isn't 0.
     * @param newValue value of new info
     * @param index How many iterations are needed to reach desired index.
     * @param wormhole Node from which method will iterate.
     * @return
     */
    Node addAt(T newValue, int index, Node<T> wormhole) {

        Node<T> node = new Node<>(newValue);

        if(wormhole == head) {

            Node<T> iterator = head;

            while(index > 0)
            {
                iterator = iterator.next;
                index--;
            }


            node.next = iterator;
            node.back = iterator.back;
            iterator.back.next = node;
            iterator.back = node;

            size++;
        }else {

            Node<T> wormholeIterator = wormhole;

            while (index > 0) {
                wormholeIterator = wormholeIterator.next;
                index--;
            }

            node.next = wormholeIterator;
            node.back = wormholeIterator.back;
            wormholeIterator.back.next = node;
            wormholeIterator.back = node;

            size++;
        }
        return null;
    }

    //Temporary remove method to implement radixSort, may have to do changes latter
    public T removeFirst() {

        T value = head.info;

        if(size == 0)
            return null;
        else if(size == 1) {

            head = null;
            size--;

            return value;
        }
        else {

            head = head.next;
            head.back = null;
            size--;

            return value;
        }
    }

    protected void removeLast() {

        tail = tail.back;
        tail.next = null;
        size--;

    }


    protected void removeAt(int index, Node wormhole) {

        Node iterator = wormhole;

        if(wormhole == head) {

            while (index > 0) {
                iterator = iterator.next;
                index--;
            }

            //Deleting node
            iterator.back.next = iterator.next;
            iterator.next.back = iterator.back;

            size--;
        }
        else {

            while (index > 0) {
                iterator = iterator.next;
                index--;
            }

            //Deleting node
            iterator.back.next = iterator.next;
            iterator.next.back = iterator.back;

            size--;
        }



    }


    String print()
    {
        if(this.head == null)
            return null;

        Node<T> iterator = head;
        String msg = "";


        while (iterator.next != null) {

            msg += iterator.info.toString() + ", ";
            iterator = iterator.next;

        }

        msg += iterator.info.toString();

        return msg;
    }



    String printReverse() {

        if(this.tail == null)
            return null;

        Node<T> iterator = tail;
        String msg = "";

        while (iterator.back != null) {
            msg += iterator.info.toString() + ", ";
            iterator = iterator.back;
        }

        msg += head.info.toString();

        return msg;
    }

    protected class Node <E>
    {
        private E info ;     // each node stores an object of the
        // type-parameter class...
        private Node next ;  // ...and a pointer to another node
        private Node back;

        // Node Constructor
        // parameter x is an object of the type-parameter class
        Node(E x)
        {
            info = x;	    // set info portion to parameter passed
            next = null;
            back = null;
        }

        //MAY DELETE AFTER FINISH TESTING!!!!!!!!!!!!!!!!!!
        public E getInfo() {
            return info;
        }

        public Node getBack() {
            return back;
        }

        public void setInfo(E info) {
            this.info = info;
        }

        public Node getNext() {
            return next;
        }
    } // end of Node class definition ============================
}
