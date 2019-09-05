/*
Author: Robert Rodriguez. Student at FIU.
Date: 9/5/2019
*/
public class SuperDS<T> {

    private DoubleLinkedList<T> myDLL;
    private DoubleLinkedList.Node[] wormholes;
    private int JUMP;
    private int counter; //Make it private
    private int arrCounter = 0;


    public SuperDS(int jump, int size)
    {
        myDLL = new DoubleLinkedList<>();
        counter = 0;
        JUMP = jump;
        this.wormholes = new DoubleLinkedList.Node[(size/JUMP) + (size/2)];

    }


    public void add(T newValue) {

        myDLL.addBack(newValue);
        counter++;

        percolateUp(arrCounter); //I CHANGED THIS, BUT IT SHOULD BE OK
    }

    public void addAt(T newValue, int index)
    {

        if (index == 0)
            addFront(newValue); //Calls a helper method that contains algo to update wormholes if any.

        else if( index == myDLL.getSize())
            add(newValue); //Takes care of counter

        //if index is between head and 1st wormhole
        else if( (index + 1) < JUMP) {

            myDLL.addAt(newValue, index, myDLL.head);
            counter++;

            percolateUp(0);

        }
        else {  //If number to be inserted is after first wormhole

            myDLL.addAt(newValue, (index + 1) % JUMP,wormholes[ ( (index + 1) /JUMP) -1  ]);

            counter++;

            if( (index + 1) % JUMP == 0) //If a new node is inserted exactly at a wormhole location
                percolateUp( ( (index +1) /JUMP) -1); //percolate including the current wormhole
            else
                percolateUp((index/ JUMP)); //percolate for the remaining wormholes in the list
        }
    }

    private void addFront(T newValue) {
        myDLL.addFront(newValue);
        counter++;

        percolateUp(0);
    }


    /**
     * This method updates the list if a node is to be inserted in between wormholes
     * @param indexWormholesList
     */
    private void percolateUp(int indexWormholesList)
    {
        for (int i = indexWormholesList; i < arrCounter; i++) {

            wormholes[i] = wormholes[i].getBack();

        }

        //Checks if a new wormhole can be inserted MUST BE AFTER PERCOLATION!
        if(counter == JUMP) {
            wormholes[arrCounter] = myDLL.tail;
            arrCounter++;
            counter = 0;
        }
    }

    public void delete(int index) {

        if(index == 0) {
            myDLL.removeFirst();
            counter--;
            percolateDown(0);
        }
        else if(index == (myDLL.getSize() - 1) ) {
            myDLL.removeLast();
            counter--;
            percolateDown(arrCounter);
        }
        else if ((index + 1) < JUMP){

            myDLL.removeAt(index,myDLL.head);
            counter--;
            percolateDown(0);

        }
        else {

            myDLL.removeAt((index + 1) % JUMP,wormholes[ ( (index + 1) /JUMP) -1  ]);

            counter--;

            if( (index + 1) % JUMP == 0) //If a new node is deleted exactly at a wormhole location
                percolateDown( ( (index +1) /JUMP) -1); //percolate including the current wormhole
            else
                percolateDown((index/ JUMP)); //percolate for the remaining wormholes in the list


        }
    }

    /**
     * This method updates new position of wormholes depending on the new JUMP.
     * @param newJump New amount of nodes between wormholes.
     */
    public void updateWormholes(int newJump) {

        JUMP = newJump;
        arrCounter = 0;
        counter = 1;

        DoubleLinkedList.Node iterator = myDLL.head;

        while(iterator.getNext() != null) {

            iterator = iterator.getNext();
            counter++;

            if(counter == JUMP) {
                wormholes[arrCounter] = iterator;
                arrCounter++;
                counter = 0;
            }
        }

        //It seems to work without this but im not sure, must check latter
        /*counter++;

        if(counter == JUMP) {
            wormholes[arrCounter] = iterator;
            arrCounter++;
            counter = 0;
        }*/

    }

    private void percolateDown( int lastWormholeIndex) {

        //When you reach this point arrCounter is pointing to the next index that a wormhole will be added at (garbage).
        for (int i = lastWormholeIndex; i < arrCounter; i++) // i<size-1 not included bc last pointer is garbage
        {
            wormholes[i] = wormholes[i].getNext();
        }

        if(counter < 0)
        {
            arrCounter--; //Dereferences the last wormhole
            counter = JUMP -1; //Careful with this if JUMP is 10 then set it to 9 so that next time you add you insert a wormhole
        }
    }

    public void printWormholes() {
        System.out.println("Printing wormholes: ");
        for (int i = 0; i < arrCounter; i++) {
            System.out.println(wormholes[i].getInfo());
        }
    }


    public void print() {
        System.out.println(myDLL.print());
    }

    public void printReverse() {
        System.out.println(myDLL.printReverse());
    }

    //This is still in testing!
    public void radixSort() {

        //IMPORTANT NOTE: the list being printed is the arr of space complexity
        RadixSort sort = new RadixSort(myDLL);


        //Here is the problem, in radix sort the array contains doublelinked lists (3rd class in this project)
        // so it works for both .next and .back, however it doesnt do the wormholes bc the concept of it is in this class
        // so unless you come up with something crazy, you will have to do the linear iteration to fix wormholes.
        sort.radixSort();
        System.out.println("Fix this! (Not the real list)");

    }
}
