# Percolation

This is the first Homework; It is about utilizing Union Find Algorithm & Weighted Quick Find in specific. We use the algorithm to calculate numerically the probability *p* that can make a system percolate (any top cell connected to any bottom cell); We are required to proof the claim that $p\approx 0.5931$ and the homework does exactly that. The percolation symbolisis a lot of real world applications such as insulating material in a conducting material (or vise versa) or finding a connection between two persons in a large social network and so on.

Grade: 100/100

![Percolation Grades](./Grades/PercolationGrade.png)

<br>

# Queues

The goal of this assignment is to implement elementary data structures using arrays and linked lists, and to introduce usage of generics and iterators. The Assignment emphasizes the apparingly-similar yet different implementation and outcomes from implementing Stacks (LIFO) and Queues (FIFO). In general, the fastest depends on the usage, if nothing is going to crumble if the DS suddenly lagged (amortized time) then Using Arrays is the most suitable solution. If a stream must be maintained without any delay in inputing from stream, then LinkedList ensures such desire.

Grade: 100/100

![Deques and Randomized Queues](./Grades/DequesandRandomizedQueues.png)

<br>

# Collinear

The goal of this assingment is to create a line pattern between any 4 points or more on the same line via two methods; The Brute force method which takes $O(n)\approx N^4$ when it tries to connect 4 points (or takes *n* in general where *n* is the number of consequitive collinear points; so if we want 5 consequitive points, it will take $N^5$). The other method is the mergesorting method which takes $O(n)\approx N^2$ despite the number of elements we are searching for; We use it to sort because it is the fastest & moststable (i.e., Items don't entirely re-arrange when we are re-sorting w.r.t another parameter).

Grade: 100/100

![Collinear](./Grades/collinear.png)

<br>

# 8 Puzzle

The goal of the assignment is to find the shortest path between current board state and goal board state using Heasort (Specifically Priority Queues DS & A\* Algorithm and Manhattan priority distance for the Algorithm). The reason we are using Heapsort is because the addition & removal of items takes $O(n)\leq 2N\lg (N)$ in the worst case and deleting min takes $O(1)$ which makes it faster in adding, finding & removing nodes. The A\* Algorithm lazily evaluates the neighbors (next-step-board) which even makes least addition to the PQ as much as possible.

![8Puzzle](./Grades/8Puzzule.png)

<br>

# KdTrees

KdTrees are the generalization of BST trees but enhances API performance when dealing with a multi-dimension space; This is illustrated via PointSET VS. KdTree when searching for nearest or points in range; The slight difference is that the tree is composed of nodes and nodes are compared to others depending on their position in the tree (Vertical or horizontal in this case). So briefly, BST's complexity is $O(n)=n^2$ VS. KdTree's complexity which is $O(n)\approx N\lg(N)$

![KdTree](./Grades/KdTree.png)

<br>

# WordNet

WordNet assignement is about graphing relations between english words which is essentially Directed & Undirected graphs; WordNet is a semantic lexicon for English Language that computational linguists and cognitive scientist use extensively. It ggroups words into sets of synonyms called *synsets*; it relates *synsets* to each other; a *hypernym* (more specific synset) points to *hypernym* (more general synset); E.g., synset { *gate*, *logic gate*} is a hypernym of {*AND cirrcuit*, *AND gate*} because *AND* gate is a kind of logic gate. To draw WordNet digraph, set an id -which is an integer- to each synset and draw the digraph using the ids. In this way, we can conclude distance between word and other or even a cluster and other.

![WordNet](./Grades/WordNet.png)

<br>

# Seam Carving

Seam-carving is a content-aware image resizing technique where the image is reduced in size by one pixel of
height (or width) at a time. A vertical seam in an image is a path of pixels connected from the top to the
bottom with one pixel in each row; a horizontal seam is a path of pixels connected from the left to the right
with one pixel in each column. Check [this Youtube Video](https://www.youtube.com/watch?v=6NcIJXTlugc) for visualization. The complexity of such algorithm is $O(n)=V+E$ where each vertex is visited only once. The algorithm used is Acyclic Shortest Path for Edge Weighted Directed Acyclic Graphs with slight modification; the procedures of the algorithm can be described as following:
1. Assign energy for each vertex/pixel; Energy function is: $e_p(c) = |C'| \hat{x}+|C'| \hat{y} = |\frac{\partial}{\partial x} C|+|\frac{\partial}{\partial y} C| = e_p(x,y)=\sqrt{{\triangle_x}^2(x,y)+{\triangle_y}^2(x,y)}$ 
2. Find seam; Seam will be top to bottom if we want the vertical seam; Seam will be left to right if we want the horizontal seam. The seam is the shortest path connecting high (bottom, right) to low (left, up) where the pixel itself has a weight instead of the edge itself.
	1. For vertical seam, for each row, compare each pixel in each column with the three pixels above it, that connects to this pixel, and relax (I.e., find the least distance to this pixel from pixels (row-1, col), (row-1, col), (row-1, col+1) and store this least distance and the least pixel in distTo and edgeTo respectively).
	2. For horizontal seam, for each column, compare each pixel in each row with the three pixels on its left, that connects to this pixel, and relax (I.e., find the least distance to this pixel from pixels (row-1, col-1), (row, col-1), (row+1, col-1).
	3. Find least distTo item when reaching last row (if vertical) or column (if horizontal) and trace back the route to it via tracing edgeTo until we reach first row or column.
3. Remove/insert least energetic seam from picture and reconstruct picture.

![Seam Carving](./Grades/SeamCarving.png)

<br>

# Baseball Elimination

It is one of many applications of maxflow-mincut-theorem applications for anticipating destined/undoubtable consequences from current scenario circumstances. The trick about this assignment is that it doesn't clearly appear, from the beginning, that there is any relation between graphs and anticipating an elimination of a team in a sport game and that is what this assignment hardwires in one's brain. Baseball elimination problem is a specific case of a general case called Sports Elimination Problem where one trys all possible scenarios for a certain team to win or tie with another winning team and if it is not the case, then the algorithm detects such thing via finding mincut from maxflow.

Despite the assignment implementation, there are better implementations of the Baseball Elimination Problem where there is no need to study each team independtly as they are essentially the same problem, with the same scenario, and there exissts a *win* constant that a team is to be eliminated if it didn't pass it under the most optimistic value (current wins so far + remaining games under the assumption that the team will win them all)

To begin, solving the problem consists of 3 main stages; Constructing the flow network, Finding the maxflow & computing the mincut from the maxflow. We construct the flow network by connecting network vertices and edges as the following:

<img src='./Week 8 - Baseball Elimination/Miscellaneous/Baseball Elimination.png'/>

Second, We run Ford-Fulkerson algorithm that computes maxflow with complexity of $O(n) \approx n^2$ as the current princeton implementation uses BFS which means that the `hasAugmentingPath()` will have worst case scenario $\propto EV$ but it does not matter as the number of teams studied are relatively small and doesn't need further optimization.

Finally, we compute mincut via following the augmenting path (Forward edge that is not full and backward edge that is not empty) and any vertex inside of the mincut is the team that eliminates current team *i*. Each vertex represents a team that can't play further games than $m_x=wins_{team\ i} + remaingingGames_{team\ i}$.

![Baseball Elimination](./Grades/BaseballElimination.png)
