import java.util.ArrayList;

public class RadixSort {
    private ArrayList<DoubleLinkedList<Integer>> myList = new ArrayList<>();
    private int[] arr;


    /**
     * CAUTION!!!!!!! because I am using a DoubleLinkedList I am NOT creating a table of wormholes for every linkedList
     * at every index of the arrayList because the concept of the wormholes is inserted in the SuperDS class, not in the
     * DoubleLinkedList class, therefore we have to go thru the list in O(n) and create new wormholes.
     * * @param x
     */
    public RadixSort(DoubleLinkedList x)
    {
        for (int i = 0; i < 10; i++) //set size of 0 to 8, since 8 is last even digit
        {
            myList.add(new DoubleLinkedList<>());
        }

        arr = new int[x.getSize()];

        DoubleLinkedList.Node y = x.head;

        int counter = 0;
        while(y.getNext() != null)
        {
            arr[counter] = (Integer) y.getInfo();
            y = y.getNext();
            counter++;
        }
        arr[counter] = (Integer) y.getInfo();

        //printArray();
    }

    public void printArray(){
        for (int x:arr
        ) {
            System.out.print(x + " ");
        }
        System.out.println();
    }

    public DoubleLinkedList radixSort() {

        int digits = findMax() +1; //Returns digits not the value of max

        //System.out.println("The amount of digits are " + digits);

        int digitIterator = 1;

        while(digits > 0) {

            int position;

            for (int i = 0; i < arr.length; i++){
                position = Math.abs((arr[i]/digitIterator)%10);

                if(arr[i] < 0 && digits == 1)
                    myList.get(position).addFront(arr[i]);
                else
                    myList.get(position).addBack(arr[i]); //This changes the original list!!!!
            }
            digits--;

            digitIterator *= 10;

            System.out.println("You are about to print the list:");
            printList();

            if(digits != 0)
            updateList();
            //System.out.println("Printing the arr");
            //printArray();
        }
        //System.out.println(myList.get(0).print());

        System.out.println(myList.get(0).print());
        //System.out.println(myList.get(0));
        return myList.get(0);
    }

    private void updateList() {

        int counter = 0;
        for(int i = 0; i < myList.size(); i++) {
            while (myList.get(i).getSize() != 0) {
                arr[counter] = myList.get(i).removeFirst();
                counter++;
            }
        }
    }


    private int findMax() {

        int max=0;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] > max)
                max = arr[i];
        }

        boolean moreDigits = true;
        int i = 1;
        int digits = 1;

        while(moreDigits){
            if(max / i > 0){
                i*= 10;
                digits++;
            }
            else
                moreDigits = false;

        }
        //System.out.println("Number of digits of the max is " +digits);
        return digits;
    }

    public void printList() {
        for (int i = 0; i < 10; i++) {
            System.out.println(myList.get(i).print());
        }
    }

}
