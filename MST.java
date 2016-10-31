/**
 * Created by Jayme on 2/10/2016.
 * The main class for the implementation of the Minimum Spanning Tree
 */
import java.io.*;
import java.util.ArrayList;

public class MST {

    /**
     * The main functions of the program. Should accept an input file (checking for errors)
     * @param args  An input file to test
     */
    public static void main(String args[]){

        int n = 0;
        int seed = 0;
        double p = -1;

        try{
            if(args.length < 1){
                System.out.println("Input file not found");
                System.exit(0);
            }

            //WINDOWS DELETE LATER
            //BufferedReader br = new BufferedReader(new FileReader(args[0] + ".txt"));
            //LINUX UNCOMMENT LATER
            BufferedReader br = new BufferedReader(new FileReader(args[0]));

            try{
                n = Integer.parseInt(br.readLine());
                seed = Integer.parseInt(br.readLine());
            }catch(NumberFormatException e){
                System.out.println("n and seed must be integers");
                System.exit(0);
            }

            if (n < 2){
                System.out.println("n must be greater than 1");
                System.exit(0);
            }

            try{
                p = Double.parseDouble(br.readLine());
            }
            catch(NumberFormatException e){
                System.out.println("p must be a real number");
                System.exit(0);
            }

            if(p < 0 || p > 1){
                System.out.println("p must be between 0 and 1");
                System.exit(0);
            }
            //System.out.println("Yaya! Input was correct");


        }catch(Exception e){
            System.out.println(e.toString());
            System.exit(0);
        }

        Graph g = makeGraph(n,seed,p);
        sortEdges(g, n);
    }

    /**
     * Makes a new Graph checking its' connected. Commander of Part  1
     * @param n     Number of Nodes in Graph
     * @param seed  The seed of randomness used
     * @param p     The probability of Nodes being connected
     */
    public static Graph makeGraph(int n, int seed, double p){

        Graph g;
        do{
            //System.out.println("In makeGraph");
            g = new Graph(n,seed,p);
        }while(n != g.doDepthFirstSearch(0));

        printGraph(g,n,seed,p);
        return g;
    }


    /**
     * Does Insertion Sort, Count Sort, and QuickSort
     * @param g     The Graph which to sort the edges of
     * @param n     The number of Nodes in the Graph
     */
    public static void sortEdges(Graph g, int n){
        g.makeEdgeArrayMatrix();
        long timeToConvertMatrixToEdges = g.getTime();

        g.insertionSort();
        long sort = g.getTime();
        ArrayList<Edge> MST = g.doKruskalAlgorithm();
        g.printKruskalFinal(true,"Insertion", timeToConvertMatrixToEdges + sort +g.getTime(),n,MST);
        //g.printSorts(true, "Insertion", timeToConvertMatrixToEdges + g.getTime(), n);

        g.shuffleEdgeTable();
        g.countSort();
        sort = g.getTime();
        MST = g.doKruskalAlgorithm();
        g.printKruskalFinal(true,"Count", timeToConvertMatrixToEdges + sort +g.getTime(),n,MST);
        //g.printSorts(true, "Count", timeToConvertMatrixToEdges + g.time, n);

        g.shuffleEdgeTable();
        g.quickSort();
        sort = g.getTime();
        MST = g.doKruskalAlgorithm();
        g.printKruskalFinal(true,"Quick", timeToConvertMatrixToEdges + sort +g.getTime(),n,MST);
        //g.printSorts(true, "Quick", timeToConvertMatrixToEdges + g.time, n);


        g.makeEdgeArrayList();
        long timeToConvertListToEdges = g.getTime();

        g.insertionSort();
        sort = g.getTime();
        MST = g.doKruskalAlgorithm();
        g.printKruskalFinal(false,"Insertion",timeToConvertListToEdges + sort + g.getTime(),n,MST);
        //g.printSorts(false, "Insertion", timeToConvertListToEdges + g.getTime(), n);

        g.shuffleEdgeTable();
        g.countSort();
        sort = g.getTime();
        MST = g.doKruskalAlgorithm();
        g.printKruskalFinal(false,"Count",timeToConvertListToEdges + sort + g.getTime(),n,MST);
        //g.printSorts(false, "Count", timeToConvertListToEdges + g.time, n);

        g.shuffleEdgeTable();
        g.quickSort();
        sort = g.getTime();
        MST = g.doKruskalAlgorithm();
        g.printKruskalFinal(false,"Quick",timeToConvertListToEdges + sort + g.getTime(),n,MST);
        //g.printSorts(false, "Quick", timeToConvertListToEdges + g.time, n);

        doPrimAlgo(g,n);
    }

    /**
     * Does Prim's algorithm (or rather calls other methods in Graph to do Prim's algorithm)
     * and prints it out. Does Prim for Adjacency Matrix and Adjacency List
     * @param g     The current graph to do Prim's on
     * @param n     The amount of total Nodes in the graph
     */
    private static void doPrimAlgo(Graph g, int n){

        ArrayList<Edge> MST = g.doPrimAlgo();
        long time = g.getTime();
        g.printPrimFinal(true,time,n,MST);

        MST = g.doPrimAlgo();
        time = g.getTime();
        g.printPrimFinal(false,time,n,MST);
    }

    /**
     * Prints out the Graph as described in the Part 1 handout
     * @param g     The graph to print out
     * @param n     Number of Nodes in Graph
     * @param seed  The seed of randomness used
     * @param p     The probability of Nodes being connected
     */
    private static void printGraph(Graph g, int n, int seed, double p){
        System.out.println("\nTEST: n=" + n + ", seed=" + seed + ", p=" + p);
        System.out.println("Time to generate the graph: " + g.getTime() + " milliseconds\n");
        if(n < 10){
            g.printAdjacencyMatrix();
            g.printAdjacancyList();
            g.printDepthFirstSearch();
        }
    }
}
