# BestDataStructure
This project is to create a linear ADT where all operations are sublinear, except insert/delete end which is O(1). 
I use a DoubleLinkedList to represent the linear Data Strucuture and an array of nodes to access specific nodes (nth) in the list.

This is the general idea of the project:
Assume you have a 100 elements list and that we want to add an element at k=50, in a regular linkedList this will be O(N). However,
in this data structure the complexity will be sublinear. Before I mentioned an array that contains nodes, well, this "special" nodes
(when N = 100) are created every 10 nodes, meaning that elements at indexes 0,10,20,30,40,50,60,70,80,90,100 (0 is head of linkedList and
100 is tail) are stored in this array. This means we can access in contant time all of this nodes. For this exxample we want to add at 
index 50, so by doing the following operation we access the 50th element in constant time: arr[4].info (this is 50), this mens that we can
now dereference pointers or point to the new node (do the normal nodes operations to add and also remove a node from a list).

The point here is not to show how to remove or add nodes from a linkedList, but to demostrate that accessing a node at kth index can be 
done in sublinear time.

In the previous example we had the best complexity possible O(1), however the complexity is not constant and here is the math:

Suppose we want to insert a new node at element 59. We would take 1 operation to get to element 50 ( arr[4] ) and 9 iterations of 
iterator = iterator.next which gives a total of 10 operations per add/remove operation, and this number depends is not random. If we go back
we see that we partinioned the list in 10 partitions of 10 elements each, so the range of every partition is equals to the total amount of
partitions and this in Mathematics is called sqrt(N), sqrt(100) = 10, thus the complexity to add/remove an element from this list is 
O(sqrt(N)) which is not exactly the same as O(logN), but it is much faster than linear.

Now, there is another operation we need to do, after we inserted an element in index 59, now we increased the range between nodes 50 and 60
and we need to do somethign about this, because the range must be a fix size in order to obtain this complexity (think if the size keeps 
increasing the amount of operations wont be equivalent to sqrt(N)). This is when I introduced a private method called percoletaUp and 
percolateDown. For this case after we inserted an element at index 59, which we used node at arr[4] to get there in sublinear time, now we
need to go up the list, from arr[5] to arr[9] and "move back" 1 position this node (arr[4] = arr[4].back ), now we fixed this! How, ever 
this introduced more operations in add/remove complexity that ultimatelly won't change it, but it is worth mentioning it. 

From the previous notes we stated that for each add/remove we separate this operations in two steps: one is to get thru the arr[4] and 
iterate 9 times and the second one is to percolateUp or down the list to update or fix the range, each of these two operations takes
O(sqrt(N)) because the range and the size of the arr (which is equals the to total amount of partitions) are the same (both equals 10) and
10 is sqrt(N).

If you made it this far I am so glad that you are interested in this project. I cannot describe with words how glad I am to share it and I 
hope this gets somewhere. Also, the actual code is easier to understand than all these notes.
