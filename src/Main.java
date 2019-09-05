public class Main {
    public static void main(String[] args) {

        SuperDS<Integer> test = new SuperDS<>(5, 100);


        test.addAt(0,0);

        test.addAt(1,1);

        test.addAt(-1,0);

        test.addAt(2,2);

        test.addAt(4,1);

        test.addAt(-2,0);

        test.addAt(3,3);

        test.addAt(5,4);


        test.addAt(0,0);

        test.addAt(1,1);

        test.addAt(-1,0);

        test.addAt(2,2);

        test.addAt(4,1);

        test.addAt(-2,0);

        test.addAt(3,3);

        test.addAt(5,4);

        test.delete(14);

        test.delete(4);



        test.addAt(0,0);




        test.print();

        test.printWormholes();

        test.radixSort();


    }
}
