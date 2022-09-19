# Percolation

This is the first Homework; It is about utilizing Union Find Algorithm & Weighted Quick Find in specific. We use the algorithm to calculate numerically the probability *p* that can make a system percolate (any top cell connected to any bottom cell); We are required to proof the claim that *p*~=0.5931 and the homework does exactly that. The percolation symbolisis a lot of real world applications such as insulating material in a conducting material (or vise versa) or finding a connection between two persons in a large social network and so on.

Grade: 100/100

![Percolation Grades](./Grades/PercolationGrade.png)

<br>

# Queues

The goal of this assignment is to implement elementary data structures using arrays and linked lists, and to introduce usage of generics and iterators. The Assignment emphasizes the apparingly-similar yet different implementation and outcomes from implementing Stacks (LIFO) and Queues (FIFO). In general, the fastest depends on the usage, if nothing is going to crumble if the DS suddenly lagged (amortized time) then Using Arrays is the most suitable solution. If a stream must be maintained without any delay in inputing from stream, then LinkedList ensures such desire.

Grade: 100/100

![Deques and Randomized Queues](./Grades/DequesandRandomizedQueues.png)

<br>

# Collinear

The goal of this assingment is to create a line pattern between any 4 points or more on the same line via two methods; The Brute force method which takes O(n)\~=N^4 when it tries to connect 4 points (or takes *n* in general where *n* is the number of consequitive collinear points; so if we want 5 consequitive points, it will take N^5). The other method is the mergesorting method which takes O(n)\~=n^2 despite the number of elements we are searching for; We use it to sort because it is the fastest & moststable (i.e., Items don't entirely re-arrange when we are re-sorting w.r.t another parameter).

Grade: 100/100

![Collinear](./Grades/collinear.png)

<br>

# 8 Puzzle

The goal of the assignment is to find the shortest path between current board state and goal board state using Heasort (Specifically Priority Queues DS & A\* Algorithm and Manhattan priority distance for the Algorithm). The reason we are using Heapsort is because the addition & removal of items takes $O(n)\leq 2N\lg (N)$ in the worst case and deleting min takes $O(1)$ which makes it faster in adding, finding & removing nodes. The A\* Algorithm lazily evaluates the neighbors (next-step-board) which even makes least addition to the PQ as much as possible.

![8Puzzle](./Grades/8Puzzule.png)

<br>
