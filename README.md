# BestDataStructure
This project creates a linear Data Structure where with the exception of print O(n) and add/delete at the end O(1), the rest of the operations are sublinear O(sqrt(n)).I used a DoubleLinkedList to represent the linear Data Strucuture and an array of nodes to save and access specific nodes at constant time in the list.

This is the general idea of the project:
Assume you have a 100 elements list and that we want to add an element at the middle, in a regular linkedList this will be O(N). However, in this data structure the complexity will be sublinear. Before, I mentioned an array that contains nodes, well, this "special" nodes are "created" or saved every sqrt(n) nodes, meaning that for our 100 elements LinkedListelements we will save the nodes at indexes 0,10,20,30,40,50,60,70,80,90,100 (0 is head of linkedList and 100 is tail) in this array. This means we can access in contants time all of this nodes. For this exxample we want to add at index 50, so by doing the following operation we access the 50th element in constant time: arr[5].info (this is 50), this means that we can now do all operations with nodes (do the normal nodes operations to add and also remove a node from a list).

The point here is not to show how to remove or add nodes from a linkedList, but to demostrate that accessing a node at kth index can be 
done in sublinear time.

In the previous example we had the best complexity possible O(1). However, the complexity is not always constant, but i most cases sqrt(n) and here is the math:

Suppose we want to insert a new node at element 59. We would take 1 operation to get to element 50 ( arr[5] ) and 9 iterations (think of 
iterator = iterator.next) which gives a total of 10 operations per add/remove method, and this number represents sqrt(100). If we think carefully, we see that we divided the list in 10 partitions every 10 nodes each, so the range of every partition is equals to the total amount of partitions and this in Mathematics is called sqrt(N), sqrt(100) = 10, thus the complexity to add/remove an element from this list is O(sqrt(N)) which is not exactly the same as O(logN), but it is much faster than linear.

Now, there is another operation we need to do, after we inserted an element in index 59, now we increased the range between nodes 50 and 60 and we need to do something about this, because the range must be the same size in order for this to work everywhere (think if the size keeps increasing the amount of operations wont be equivalent to sqrt(n)). This is when I created a method called percoletaUp and percolateDown. For this case, after we inserted an element at index 59, which we used node at arr[5] to get there in sublinear time, now we need to go up the list, from arr[5] to arr[10] and "move back" 1 position this node (arr[5] = arr[5].back ), now we fixed it! 

Even if this introduced more operations to add/remove methods, that ultimatelly won't change its complexity, but it is important to lay down why: 
For each add/remove, we separate this operations in two steps: one is to get through the arr[5] and iterate 9 times and the second one is to percolateUp or down the list to update or fix the amount of nodes btw each node saved in the array, each of these two operations takes O(sqrt(n)) because the distance btw each "special node" and the size of the arr are the same (both equals 10) and 10 is sqrt(n). O(sqrt(n) + sqrt(n)) = O(sqrt(n)).

It was very challenging to try explain this project in a document. However, I have more documentation on the java files and I beleive the project itself is somewhat self-explanatory and the code is nice to the eye.
