public class SuperDS<T>
{
    private DoubleLinkedList<T> myDLL;
    private DoubleLinkedList.Node[] wormholes;
    private int JUMP;                           // size btw wormholes
    private int counter;                        // every time counter = JUMP, insert a wormhole and reset counter
    private int arrCounter = 0;                 // pointer for wormholes array

    public SuperDS()
    {
        JUMP = 2; // DO NOT MAKE JUMP < 2, all sorts of bugs happen (it wont affect complexity anyways)
        myDLL = new DoubleLinkedList<>();
        counter = 0;
        wormholes = new DoubleLinkedList.Node[11]; // initial capacity
    }

    // This method is called every time you insert a node, if sqrt(n) != JUMP, an action must be taken to
    // update the list, making sure we maintain the complexity and order of the structure
    private boolean needIncreaseJUMP()
    {
        int size = myDLL.getSize();
        int newSize = (int)Math.sqrt(size); //drop the decimal places (the idea is that the JUMP only changes in units of 1s)

        if ( newSize > JUMP)
            return true;
        else
            return false;
    }

    private void increaseJUMP()
    {
        arrCounter--; // need to delete last wormhole
        counter = 0; // resets counter
        JUMP++;

        for (int i = 0; i < arrCounter; i++) // perhaps the worst complexity in the entire project, but it is still sqrt(n)
        {
            int j = i + 1; // the first wormhole will be moved next once
            while(j > 0) // at worst i = sqrt(n)
            {
                wormholes[i] = wormholes[i].getNext();
                j--;
            }
        }
    }

    // I had to recreate this method only changing the comparison operator because I was getting bugs if I tried to use
    // the same method for deletion and insertion
    private boolean needDecreaseJUMP()
    {
        int size = myDLL.getSize();
        int newSize = (int)Math.sqrt(size);

        if ( newSize < JUMP)
            return true;
        else
            return false;
    }

    private void decreaseJUMP()
    {
        counter = 0; // resets counter
        JUMP--;

        int i;
        for (i = 0; i < arrCounter; i++) // perhaps the worst complexity in the entire project, but it is still sqrt(n)
        {
            int j = i + 1; // the first wormhole will be moved next once
            while(j > 0) // at worst i = sqrt(n)
            {
                wormholes[i] = wormholes[i].getBack();
                j--;
            }
        }
        // we need to insert pointers, since decrementing JUMP will increase partitions of the list or amount of wormholes
        wormholes[arrCounter] = myDLL.tail;
        while(i > 0) // at worst i = sqrt(n)
        {
            wormholes[arrCounter] = wormholes[arrCounter].getBack();
            i--;
        }
        arrCounter++;
        // adding the tail
        wormholes[arrCounter] = myDLL.tail;
        arrCounter++;
    }

    public void addBack(T newValue)
    {
        myDLL.addBack(newValue);
        counter++;

        percolateUp(arrCounter);
    }

    public void addAt(T newValue, int index)
    {
        if (index == 0)
            addFront(newValue);

        else if( index == myDLL.getSize()) // if add last
            addBack(newValue);

        //if index is between head and 1st wormhole
        else if( (index + 1) < JUMP)
        {
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

    public void addFront(T newValue)
    {
        myDLL.addFront(newValue);
        counter++;

        percolateUp(0);
    }

    /**
     * This method updates the list if a node is to be inserted in between wormholes
     * @param indexWormholesList Index of new value to be inserted
     */
    private void percolateUp(int indexWormholesList)
    {
        for (int i = indexWormholesList; i < arrCounter; i++)
        {
            wormholes[i] = wormholes[i].getBack();
        }

        //Checks if a new wormhole can be inserted MUST BE AFTER PERCOLATION!
        if(counter >= JUMP)
        {
            wormholes[arrCounter] = myDLL.tail;
            arrCounter++;
            counter = 0;
        }

        if (arrCounter >= wormholes.length)
            enlargeWormholesList();

        if (needIncreaseJUMP()) // if new size increases the JUMP (Ex: 120 to 121, the JUMP goes from 10 to 11, int division)
            increaseJUMP();
    }

    /**
     * Method to enlarge wormholes list. Copies every element in original list to the new list with bigger size,
     * keep in mind that "every element" is still sqrt(n), this is not linear complexity.
     */
    private void enlargeWormholesList()
    {
        DoubleLinkedList.Node[] newWormholesList = new DoubleLinkedList.Node[arrCounter*2];

        for (int i = 0; i < arrCounter; i++)
            newWormholesList[i] = wormholes[i];

        wormholes = newWormholesList;
    }

    public void delete(int index)
    {
        if(index == 0)
        {
            myDLL.removeFirst();
            counter--;
            percolateDown(0);
        }
        else if(index == (myDLL.getSize() - 1) ) // delete last
        {
            myDLL.removeLast();
            counter--;
            percolateDown(arrCounter);
        }
        else if ((index + 1) < JUMP) // btw head and first wormhole
        {
            myDLL.removeAt(index,myDLL.head);
            counter--;
            percolateDown(0);

        }
        else // deleting btw wormholes
        {
            myDLL.removeAt((index + 1) % JUMP,wormholes[ ( (index + 1) /JUMP) -1  ]); // removeAt(remaining iterations, wormhole at index);
            counter--;

            if( (index + 1) % JUMP == 0) //If a new node is deleted exactly at a wormhole location
                percolateDown( ( (index +1) /JUMP) -1); //percolate including the current wormhole
            else
                percolateDown((index/ JUMP)); //percolate for the remaining wormholes in the list
        }
    }

    private void percolateDown( int posWormholeDeleted)
    {
        for (int i = posWormholeDeleted; i < arrCounter; i++) // arr[size-1] not included bc last pointer is garbage
        {
            wormholes[i] = wormholes[i].getNext();
        }

        if(counter < 0)
        {
            arrCounter--; //Dereferences the last wormhole
            counter = JUMP -1; //Careful with this if JUMP is 10 then set it to 9 so that next time you addBack you insert a wormhole
        }

        if (needDecreaseJUMP())
            decreaseJUMP();
    }

    public void printWormholes() {
        System.out.println("Printing wormholes: ");
        for (int i = 0; i < arrCounter; i++) {
            System.out.print(wormholes[i].getInfo() + " ");
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
