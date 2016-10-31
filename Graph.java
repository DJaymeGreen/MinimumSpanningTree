import sun.awt.image.ImageWatched;

import java.lang.reflect.Array;
import java.util.*;


/**
 * The Graph. A connected, undirected Graph to do MST and everything else
 * Created by Jayme on 2/10/2016.
 */
public class Graph {

    private static final int min_weight = 1;
    private int adjacencyMatrix[][];
    private ArrayList<ArrayList<Vertex>> adjacencyList;
    private ArrayList<Vertex> allVertex;
    private Edge[] allEdges;
    private ArrayList<pqNode> priorityQueue;
    long time = 0;
    private int predecessors[];

    private int countDFS = 0;
    private boolean[] seen;

    /**
     * Constructor for Graph which makes a randomly connected, undirected,
     * weighted graph.
     * @param n         The amount of vertex's to generate
     * @param seed      The randomness factor
     * @param p         Probability of having an edge
     */
    public Graph(int n, int seed, double p){

        long startTime = System.currentTimeMillis();

        makeAdjacencyMatrixZero(n);
        makeAdjacencyList(n);

        Random edgeRand = new Random(seed);
        Random weightRand = new Random(2 * seed);

        int max_weight = n;

        int count = 0;
        int range = max_weight;

        allVertex = new ArrayList<Vertex>();
        for(int i = 0; i < n; ++i){
            allVertex.add(new Vertex(i));
        }

        int numOfEdges = 0;
        for(int x = 0; x < n; ++x){
            for(int y = x; y < n; ++y){
                if(allVertex.get(x).getNumber() == allVertex.get(y).getNumber()){
                }
                else{
                    double r = edgeRand.nextDouble();
                    if(r <= p){
                        int weight = min_weight + weightRand.nextInt(range);
                        makeConnection(allVertex.get(x),allVertex.get(y),weight);
                        numOfEdges++;
                    }
                }

            }
        }

        allEdges = new Edge[numOfEdges];
        time = System.currentTimeMillis() - startTime;
    }

    /**
     * Getter for Time (how long it takes for the Graph to form
     * @return      The time in milliseconds of the Graph formation
     */
    public long getTime(){
        return time;
    }

    /**
     * Getter of Predecessors
     * @return      The list of Predecessors from the DFS
     */
    public int[] getPredecessors(){
        return predecessors;
    }

    /**
     * Makes the n*n adjacency matrix all 0's
     * @param n     Amount of Nodes in the Graph
     */
    private void makeAdjacencyMatrixZero(int n){
        adjacencyMatrix = new int[n][n];
        for (int r = 0; r < n; ++r){
            for (int c = 0; c < n; ++c){
                adjacencyMatrix[r][c] = 0;
            }
        }
    }

    /**
     * Makes the adjacencyList usable for the graph
     * @param n     Amount of Nodes in the Graph
     */
    private void makeAdjacencyList(int n){
        adjacencyList = new ArrayList<ArrayList<Vertex>>();
        for(int i = 0; i < n; ++i){
            adjacencyList.add(new ArrayList<Vertex>());
        }
    }

    /**
     * Makes a Edge and connects the two Vertex's together
     * @param a         Vertex a to connect
     * @param b         Vertex b to connect
     * @param weight    The weight between the connection
     */
    private void makeConnection(Vertex a, Vertex b, int weight){
        Edge connection = new Edge(a,b,weight);
        a.addEdge(connection);
        b.addEdge(connection);
        adjacencyMatrix[a.getNumber()][b.getNumber()] = weight;
        adjacencyMatrix[b.getNumber()][a.getNumber()] = weight;
        adjacencyList.get(a.getNumber()).add(b);
        adjacencyList.get(b.getNumber()).add(a);
    }


    /**
     * Helper function of Recursive DFS
     * @param start     Node to start DFS
     * @return          How many Nodes were visited by DFS
     */
    public int doDepthFirstSearch(int start){
        predecessors = new int[adjacencyList.size()];
        predecessors[start] = -1;
        countDFS = 0;
        seen = new boolean[adjacencyList.size()];
        depthFirstSearch(start);
        return countDFS;
    }
    /**
     * Does a depth first search through the entire tree starting from
     * the "start" parameter. Checks to see if the graph is connected.
     * @param start     The number of the Vertex to start at
     * @return          How many unique Vertexes DFS traversed through
     */
    private void depthFirstSearch(int start){
        countDFS++;
        seen[start] = true;
        for(Vertex visiting: adjacencyList.get(start)){
            if(!seen[visiting.getNumber()]){
                predecessors[visiting.getNumber()] = start;
                depthFirstSearch(visiting.getNumber());
            }
        }
    }

    /**
     * Converts the Adjacency Matrix to an Array of all of the Edges
     */
    public void makeEdgeArrayMatrix(){
        time = System.currentTimeMillis();
        int i = 0;
        for(int c = 0; c < adjacencyMatrix.length; ++c){
            for(int r = c; r < adjacencyMatrix.length; ++r){
                if(adjacencyMatrix[r][c] > 0){
                    allEdges[i] = allVertex.get(c).getEdge(allVertex.get(r));
                    ++i;
                }
            }
        }
        time = System.currentTimeMillis() - time;
    }

    /**
     * Converts the Adjacency List to an Array of all of the Edges
     */
    public void makeEdgeArrayList(){
        time = System.currentTimeMillis();
        int j = 0;
        //ArrayList<Edge> allEdgesTemp = new ArrayList<Edge>();
        for(int i = 0; i < adjacencyList.size(); ++i){
            for (Vertex ver: adjacencyList.get(i)){
                Edge checking = ver.getEdge(allVertex.get(i));
                if(checking.getLeft() == allVertex.get(i)){
                    allEdges[j] = checking;
                    ++j;
                }
            }
        }
        time = System.currentTimeMillis() - time;
    }


    /**
     * Does an insertion sort on the Adjacency Matrix with priority
     * of weight, left_vertex, and right_vertex from greatest to least
     */
    public void insertionSort(){
        time = System.currentTimeMillis();
        int N = allEdges.length;
        for(int i = 0; i< N; ++i){
            for(int j = i; j > 0; --j){
                if(allEdges[j].lessThan(allEdges[j-1]))
                    exchange(j, j-1);
                else break;
            }
        }
        time = System.currentTimeMillis() - time;
    }

    /**
     * Does an insertion sort on the Adjacency Matrix with priority
     * of weight, left_vertex, and right_vertex from greatest to least
     * Ignores time since it is used with Count Sort. Since the list should
     * be close to sorted, if not sorted already, it should be O(n)
     */
    public void insertionSortIgnoreTime(){
        int N = allEdges.length;
        for(int i = 0; i< N; ++i){
            for(int j = i; j > 0; --j){
                if(allEdges[j].lessThan(allEdges[j-1]))
                    exchange(j, j-1);
                else break;
            }
        }
    }


    /**
     * Does an Count Sort on the Adjacency List with priority of weight,
     * left_vertex, and right_vertex from greatest to least
     */
    public void countSort(){
        time = System.currentTimeMillis();
        int n = allEdges.length;
        int R = getMaxWeight() + 1;
        int[] count = new int[R + 1];
        Edge[] aux = new Edge[allEdges.length];

        for(int i = 0; i < n; ++i){
            int index = allEdges[i].getWeight()+1;
            count[index]++;
        }


        for(int r = 0; r < R; ++r)
            count[r+1] += count[r];

        for(int i = 0; i < n; ++i){
            //System.out.println("Edge holder: " +  aux[count[allEdges[i].getWeight()]+1]);
            aux[count[allEdges[i].getWeight()]++] = allEdges[i];
        }

        for(int i = 0; i < n; ++i){
            allEdges[i] = aux[i];
        }

        insertionSortIgnoreTime();
        time = System.currentTimeMillis() - time;
    }


    /**
     * Does a Quick Sort on allEdges with priority of weight,
     * left_vertex, and right_vertex from greatest to least
     */
    public void quickSort(){
        Collections.shuffle(Arrays.asList(allEdges));
        time = System.currentTimeMillis();
        int lo = 0;
        int hi = allEdges.length;
        quickSort(lo,hi-1);
        time = System.currentTimeMillis() - time;
    }

    /**
     * Does a Quick Sort Helper on allEdges with priority of weight,
     * left_vertex, and right_vertex from greatest to least
     */
    private void quickSort(int lo, int hi){
        if(hi <= lo) return;
        int j = partition(lo,hi);
        quickSort(lo, j-1);
        quickSort(j+1, hi);
    }

    /**
     * Does the partitions (and pretty much the sort) of QuickSort
     * @oaram       lo         The lo part of the partition
     * @param       hi         The hi part of the partition
     */
    private int partition(int lo, int hi){
        int i = 0, j = hi+1;
        while(true){

            while(allEdges[++i].lessThan(allEdges[lo]))
                if(i == hi) break;

            while(allEdges[lo].lessThan(allEdges[--j]))
                if(j == lo) break;

            if(i >= j) break;
            exchange(i, j);
        }

        exchange(lo, j);
        return j;
    }

    /**
     * Exchanges the two edges at index a and index b
     * @param a     int     First index to exchange
     * @param b     int     Second index to exchange
     */
    private void exchange(int a, int b){
        Edge switchA = allEdges[a];
        Edge switchB = allEdges[b];
        allEdges[a] = switchB;
        allEdges[b] = switchA;
    }


    /**
     * Finds the maxWeight in all of the Edges
     * Used for countSort to determine R
     * @return                      Int of the maximum weight of all Edges
     */
    private int getMaxWeight(){
        int max = 0;
        for (Edge check: allEdges){
            if(max < check.getWeight())
                max = check.getWeight();
        }
        return max;
    }

    /**
     * Sums all of the weights in the adjacency matrix returning the sum
     * @return      Sum of all weights
     */
    public int sumAdjacencyMatrix(){
        int sum = 0;
        for(int r = 0; r < adjacencyList.size(); ++r){
            for(int c = 0; c < adjacencyList.size(); ++c){
                sum += adjacencyMatrix[r][c];
            }
        }
        return sum;
    }

    /**
     * Sums all of the weights in the adjacency list returning the sum
     * @return      Sum of all the weights
     */
    public int sumAdjacencyList(){
        int sum = 0;
        for(int ver = 0; ver < adjacencyList.size(); ++ver){
            for(Vertex neigh: adjacencyList.get(ver)){
                sum += neigh.getWeight(allVertex.get(ver));
            }
        }
        return sum;
    }

    /**
     * Sums all the Edge Weights together and returns it
     * @return      The summation of all the Edge weights
     */
    private int sumEdgeTable(){
        int sum = 0;
        for(Edge adding: allEdges){
            sum += adding.getWeight();
        }
        return sum;
    }

    /**
     * Resets allVertex's parents and rank to
     */
    private void resetAllVertex(){
        for(Vertex reset: allVertex){
            reset.resetRank();
            reset.resetParent();
        }
    }


    /**
     * Does Kruskal's Algorithm on allEdges (which should be sorted already)
     */
    public ArrayList<Edge> doKruskalAlgorithm(){
        ArrayList<Edge> MST = new ArrayList<Edge>();
        resetAllVertex();

        time = System.currentTimeMillis();

        int includedCount = 0;
        int i = 0;
        while(includedCount < this.allVertex.size() && i < allEdges.length){
            Vertex root1 = find(allEdges[i].getLeft());
            Vertex root2 = find(allEdges[i].getRight());
            if(!root1.equals(root2)){
                MST.add(allEdges[i]);
                includedCount++;
                union(root1,root2);
            }
            ++i;
        }

        time = System.currentTimeMillis() - time;
        return MST;
    }

    /**
     * Finds the roots of the subtrees that vertices u and v belong to
     * @param u         The Vertex to find the root of
     * @return          The root of the subtree the parameter belongs to
     */
    private Vertex find(Vertex u){
        if(u != u.getParent())
            u.setParent(find(u.getParent()));
        return u.getParent();
    }

    /**
     * Updates the partition by merging two subtrees t1 and t2 into a single
     * subtree assuming u and v have different roots
     * @param t1        First subtree to merge
     * @param t2        Second subtree to merge
     */
    private void union(Vertex t1, Vertex t2){
        if(t1.getRank() > t2.getRank()){
            t2.setParent(t1);
        }
        else{
            t1.setParent(t2);
            if(t1.getRank() == t2.getRank()){
                t2.addRank(1);
            }
        }
    }

    /**
     * Does Prim's algorithm
     * @return
     */
    public ArrayList<Edge> doPrimAlgo(){
        ArrayList<Edge> MST = new ArrayList<Edge>();
        ArrayList<Vertex> inMST = new ArrayList<Vertex>();

        time = System.currentTimeMillis();

        //Add vertex ID = 0 to MST
        inMST.add(allVertex.get(0));
        priorityQueue = makePriorityQueue();
        Vertex lastAdded = allVertex.get(0);

        int num = allVertex.size()-1;

        while (num > 0){

            //Update priorities (keys) of neightbors of v
            updatePriorityQueue(lastAdded);
            //printPriorityQueue();
            //build-heap (bottom-up) (heapify PQ)
            //heapify(0);
            bottomUpHeapify();
            //delete root (new v), add to MST
            //printPriorityQueue();
            pqNode toMST = priorityQueue.get(0);
            lastAdded = allVertex.get(toMST.getID());
            inMST.add(lastAdded);
            MST.add(lastAdded.getEdge(allVertex.get(toMST.getParent())));
            //move last element in PQ to first PQ[1] = PQ[num]
            priorityQueue.set(0,priorityQueue.get(priorityQueue.size()-1));
            priorityQueue.remove(priorityQueue.size()-1);

            num--;
        }

        time = System.currentTimeMillis() - time;
        return MST;
    }

    /**
     * Updates the priority queue's keys of the neighbors of v (just added to priority queue)
     * @param v                 The last Vertex put into the MST. All neighbors of v should be updated
     *                          priority iff the new priority (edge weight) is less than current one
     * @return                  The updated priority queue
     */
    private ArrayList<pqNode> updatePriorityQueue(Vertex v){
        ArrayList<Vertex> neighborsOfV = v.getNeighbors();
        for(Vertex neighbor: neighborsOfV){
            for(pqNode node: priorityQueue){
                if(node.getID() == neighbor.getNumber()){
                    node.setPriority(v.getEdge(allVertex.get(node.getID())).getWeight(), v);
                }
            }
        }
        //printPriorityQueue();
        return priorityQueue;
    }

    /**
     * Prints the priority queue for debugging purposees
     */
    private void printPriorityQueue(){
        System.out.println("");
        for(pqNode node: priorityQueue){
            System.out.println("Node: " + node.getID() + " " + node.getKey() + " " + node.getParent() + "\n");
        }
    }

    /**
     * Allows the heapify to become bottom-up version by checking all nodes that
     * have children
     */
    private void bottomUpHeapify(){
        int toHeapify = (priorityQueue.size()/2)-1;
        for(int i = toHeapify; i >= 0; --i){
            //System.out.println("HEAPIFYING: " + i);
            heapify(i);
        }
    }

    /**
     * Heapify's the priority queue
     */
    private void heapify(int check){
        int left,right,min;
        pqNode tmp;

        left = (2*check)+1;
        right = (2*check)+2;

        if(left < priorityQueue.size() && priorityQueue.get(left).getKey() < priorityQueue.get(check).getKey())
            min = left;
        else
            min = check;

        if(right < priorityQueue.size() && priorityQueue.get(right).getKey() < priorityQueue.get(min).getKey())
            min = right;

        if(min != check){
            tmp = priorityQueue.get(check);
            priorityQueue.set(check,priorityQueue.get(min));
            priorityQueue.set(min,tmp);
            heapify(min);
        }
    }

    /**
     * Makes the initial priority queue to be used in Prim's algorithm
     * @return      The initial priority queue
     */
    private ArrayList<pqNode> makePriorityQueue(){
        ArrayList<pqNode> priorityQueue = new ArrayList<pqNode>();
        for(Vertex ver: allVertex){
            if (ver.getNumber() > 0){
                priorityQueue.add(new pqNode(ver.getNumber()));
            }
        }
        return priorityQueue;
    }

    /**
     * Total weight of all the edges in the MST
     * @param MST       The Minimum Spanning Tree to check for
     * @return          The total amount of weight of the Edges in the MST
     */
    private int sumWeightOfMST(ArrayList<Edge> MST){
        int sum = 0;
        for(Edge edge: MST){
            sum += edge.getWeight();
        }
        return sum;
    }


    /**
     * Shuffles the Edge Table so that the times with the sorts are genuine
     */
    public void shuffleEdgeTable(){
        Collections.shuffle(Arrays.asList(allEdges));
    }


    /**
     * Prints out the Adjacency Matrix like in the Part 1 handout
     */
    public void printAdjacencyMatrix(){
        System.out.println("The graph as an adjacency matrix:");
        System.out.println("");
        for (int r = 0; r < adjacencyList.size(); ++r){
            for (int c = 0; c < adjacencyList.size(); ++c){
                System.out.print(adjacencyMatrix[r][c] + "   ");
            }
            System.out.println("\n");
        }
        //System.out.println("");
    }

    /**
     * Prints out the Adjacency List like in the Part 1 handout
     */
    public void printAdjacancyList(){
        int weight = 0;
        System.out.println("The graph as an adjacency list:");
        for(int ver = 0; ver < adjacencyList.size(); ++ver){
            System.out.print(ver + "-> ");
            for(Vertex neigh: adjacencyList.get(ver)){
                weight = neigh.getWeight(allVertex.get(ver));
                System.out.print(neigh.getNumber() + "(" + weight + ") ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    /**
     * Prints out the Depth First Search and Predecessors like the Part 1 handout
     */
    public void printDepthFirstSearch(){
        System.out.println("Depth-First Search:");
        System.out.println("Vertices:");
        for (int i = 0; i < adjacencyList.size(); ++i){
            if(i != 0)
                System.out.print(" ");
            System.out.print(i);
        }
        System.out.println("\nPredecessor:");
        for(int i = 0; i < adjacencyList.size(); ++i){
            if(i != 0)
                System.out.print(" ");
            System.out.print(predecessors[i]);
        }
        System.out.println("");
    }

    /**
     * Prints the what sort with what data structure and including
     * the total weight and runtime
     * @param usingMatrix   Matrix if true, List if false
     * @param sort          What sort was used
     * @param timeDone      How long it took to do the sort
     * @param n             The number of Nodes in the Graph
     */
    public void printSorts(Boolean usingMatrix, String sort, long timeDone, int n){
        String mOrL = (usingMatrix) ? "MATRIX" : "LIST";
        int sum = sumEdgeTable();

        System.out.println("===================================");
        System.out.println("SORTED EDGES WITH " + mOrL + " USING " + sort.toUpperCase() + " SORT");

        if(n < 10)
            printAllEdges();

        System.out.println("");
        System.out.println("Total weight = " + sum);
        System.out.println("Runtime: " + timeDone + " milliseconds");
        System.out.println("");
    }

    /**
     * Prints the output for project part 3 including the MST of Kruskal using
     * the different sorts
     * @param usingMatrix       Matrix if true, List if false
     * @param sort              What sort is used
     * @param timeDone          How long it took to do Kruskal + sort
     * @param n                 The number of Nodes in the Graph
     * @param MST               The minimum spanning tree to print
     */
    public void printKruskalFinal(Boolean usingMatrix, String sort, long timeDone, int n, ArrayList<Edge> MST){
        String mOrL = (usingMatrix) ? "MATRIX" : "LIST";
        int sum = sumWeightOfMST(MST);

        System.out.println("===================================");
        System.out.println("KRUSKAL WITH " + mOrL + " USING " + sort.toUpperCase() + " SORT");

        if(n < 10)
            printMST(MST);

        System.out.println("");
        System.out.println("Total weight of MST using Kruskal: " + sum);
        System.out.println("Runtime: " + timeDone + " milliseconds");
        System.out.println("");
    }

    /**
     * Prints the output for project part 4 including the MST of Prim using the
     * Adjacency List and Adjacency Matrix
     * @param usingMatrix       Matrix if true, List if false
     * @param timeDone          How long it took to do Prim
     * @param n                 The amount of Nodes in the Graph
     * @param MST               The minimum spanning tree to print
     */
    public void printPrimFinal(Boolean usingMatrix, long timeDone, int n, ArrayList<Edge> MST){
        String mOrL = (usingMatrix) ? "ADJACENCY MATRIX": "ADJACENCY LIST";
        int sum = sumWeightOfMST(MST);

        System.out.println("===================================");
        System.out.println("PRIM WITH " + mOrL);

        if(n < 10){
            //MST = sortMSTForPrimPrint(MST);
            printMST(MST);
        }


        System.out.println("");
        System.out.println("Total weight of MST using Prim: " + sum);
        System.out.println("Runtime: " + timeDone + " milliseconds");
        System.out.println("");
    }

    /**
     * Sorts the Edges by right node first as shown in example for Part 4
     * @param MST        The MST to sort
     * @return           A Sorted MST
     */
    private ArrayList<Edge> sortMSTForPrimPrint(ArrayList<Edge> MST){
        int N = MST.size();
        Edge temp;
        for(int i = 0; i< N; ++i){
            for(int j = i; j > 0; --j){
                if(MST.get(j).getRight().getNumber() < (MST.get(j-1).getRight().getNumber())){
                    temp = MST.get(j-1);
                    MST.set(j-1,MST.get(j));
                    MST.set(j, temp);
                }
                else if(MST.get(j).getRight().getNumber() == (MST.get(j-1).getRight().getNumber())){
                    if(MST.get(j).getWeight() < (MST.get(j-1).getWeight())){
                        temp = MST.get(j-1);
                        MST.set(j-1,MST.get(j));
                        MST.set(j, temp);
                    }
                }
                    //exchange(j, j-1);
                else break;
            }
        }
        return MST;
    }

    /**
     * Prints the MST in the way describes in the output (specifically for Part 3)
     * @param MST           The Minimum Spanning Tree to print out
     */
    private void printMST(ArrayList<Edge> MST){
        for(Edge edge: MST){
            System.out.println(edge.getLeft().getNumber() + " " + edge.getRight().getNumber() + " weight = " + edge.getWeight());
        }
    }

    /**
     * Prints all of the Edges in allEdges like specified in Part 2
     */
    private void printAllEdges(){
        for(Edge edge: allEdges){
            System.out.println(edge.getLeft().getNumber() + " " + edge.getRight().getNumber() + " weight = " + edge.getWeight());
        }
    }

    /**
     * The Getter of allEdges
     * @return      The Edge array of all Edges in the Graph
     */
    public Edge[] getAllEdges(){
        return allEdges;
    }

}
