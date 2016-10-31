import java.util.ArrayList;

/**
 * The Edge Class, represents an Edge on a Graph
 * Created by Jayme on 2/10/2016.
 */
public class Edge {


    private Vertex left;
    private Vertex right;
    private int weight;

    /**
     * Constructor of Edge.
     * @param A Vertex which is being connected to:
     * @param B Vertex which is being connected to above
     * @param weight The weight as an integer of the Edge
     */
    public Edge(Vertex A, Vertex B, int weight){
        if (A.getNumber() > B.getNumber()){
            this.right = A;
            this.left = B;
        }
        else{
            this.left = A;
            this.right = B;
        }
        this.weight = weight;
    }

    /**
     * Getter of Weight
     * @return int The Weight of the Edge
     */
    public int getWeight(){
        return weight;
    }


    /**
     * Returns true if this Edge is lessThan the comparing Edge
     * Compares weight, then left, then right
     * @param comparing     The Edge to compare with
     * @return              True if this < comparing, else false
     */
    public Boolean lessThan(Edge comparing){
        //Weight comparison
        if(this.weight < comparing.weight)
            return true;
        else if (this.weight > comparing.weight)
            return false;
        else{
            //Left Node comparison
            int thisLeft = this.left.getNumber();
            int compLeft = comparing.left.getNumber();
            if(thisLeft < compLeft)
                return true;
            else if (thisLeft > compLeft)
                return  false;
            else{
                //Right Node comparison
                return (this.right.getNumber() < comparing.right.getNumber());
            }
        }
    }

    /**
     * Getter of the two Vertices it's connecting
     * @return ArrayList<Vertex>  the connecting Vertices
     */
    private ArrayList<Vertex> getConnection(){
        ArrayList<Vertex> connect = new ArrayList<Vertex>();
        connect.add(left);
        connect.add(right);
        return connect;
    }

    /**
     * Getter of A
     * @return
     */
    public Vertex getLeft() {
        return left;
    }

    /**
     * Getter of B
     * @return
     */
    public Vertex getRight() {
        return right;
    }

}
