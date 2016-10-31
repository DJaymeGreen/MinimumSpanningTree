import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * The Vertex Class which represents a Vertex on a graph
 * Created by Jayme on 2/10/2016.
 */
public class Vertex {

    private int number;
    ArrayList<Edge> edgeConnections = new ArrayList<Edge>();
    private int rank = 0;
    private Vertex parent = this;


    public Vertex(int id){
        number = id;
    }

    /**
     * Getter of Number
     * @return int  The Number of the Vertex
     */
    public int getNumber(){
        return number;
    }

    /**
     * Adds the Edge to the edgeConnections to track
     * what edges are connected to this Vertex
     * @param adding
     */
    public void addEdge(Edge adding){
        edgeConnections.add(adding);
    }

    /**
     * Adds a parent to the union-by-rank "tree"
     * @param parent        Parent of this Vertex in the union-find tree
     */
    public void setParent(Vertex parent) {this.parent = parent;}

    /**
     * Adds ranks according to the parameter to rank
     * @param adding        The rank to add
     */
    public void addRank(int adding) {rank += adding;}

    /**
     * Resets the rank to 0
     */
    public void resetRank(){rank = 0;}

    /**
     * Resets parent to be null
     */
    public void resetParent(){parent = this;}

    /**
     * Returns the weight between this Vertex and the given parameter
     * @param b
     * @return
     */
    public int getWeight(Vertex b){
        for(Edge connections: edgeConnections){
            if(connections.getRight().getNumber() == b.getNumber() || connections.getLeft().getNumber() == b.getNumber()){
                if(connections.getRight().getNumber() == number || connections.getLeft().getNumber() == number){
                    return connections.getWeight();
                }
            }
        }
        return 0;
    }

    /**
     * Gets and returns all Vertex neighbors of this Vertex
     * @return      All of the neighbors of this Vertex
     */
    public ArrayList<Vertex> getNeighbors(){
        ArrayList<Vertex> neighbors = new ArrayList<Vertex>();

        for(Edge connecting: edgeConnections){

            if(connecting.getLeft().getNumber() == this.getNumber()){
                if(connecting.getRight().getNumber() == this.getNumber()){
                    System.out.println("This should never happen. Edge connecting Vertex to itself");
                }
                else{
                    neighbors.add(connecting.getRight());
                }
            }
            else{
                neighbors.add(connecting.getLeft());
            }
        }
        return neighbors;
    }

    /**
     * Gets the Edge connecting this and the given Vertex together
     * @param connected     The Vertex connected to this vertex
     * @return              The Edge connecting this and connected
     */
    public Edge getEdge(Vertex connected){
        for(Edge connections: edgeConnections){
            if(connections.getRight().getNumber() == connected.getNumber() || connections.getLeft().getNumber() == connected.getNumber()){
                return connections;
            }
        }
        return null;
    }

    /**
     * Getter for edgeConnections
     * @return ArrayList<Edge> containing all Edges connected
     *                          to this vertex
     */
    public ArrayList<Edge> getEdgeConnections(){
        return edgeConnections;
    }

    /**
     * Getter for rank. Used by the union-find algorithm
     * @return      Rank of the vertex
     */
    public int getRank(){return rank;}

    /**
     * Returns the parent of this Node in the union-find tree
     * @return      Parent of the vertex
     */
    public Vertex getParent(){return parent;}
}
