/**
 * Created by Jayme on 5/1/2016.
 */
public class pqNode {

    private int ID;
    private int key;
    private int parent;
    private static final Integer MAXKEY = Integer.MAX_VALUE;
    private static final int DEFAULT_PARENT = -1;


    /**
     * Makes a Nodes that will go into the Priority Queue in order to
     * do Prim's algorithm
     * @param ID        The ID (num) of a Node

     */
    public pqNode(int ID){
        this.ID = ID;
        this.key = MAXKEY;
        this.parent = DEFAULT_PARENT;
    }

    /**
     * Compares the old priority to the new priority. If the new is
     * lower then replaces it. If the newPriority is higher, then do nothing.
     * If the new Priority is replaced, updates the parent as well
     * @param newPriority       The "newPriority" to check
     * @param v                 The parent vertex of the priority
     */
    public void setPriority(int newPriority, Vertex v){
        if(newPriority < this.key){
            this.key = newPriority;
            this.parent = v.getNumber();
        }
    }

    /**
     * Getter of ID
     * @return      The ID (num) of a Node
     */
    public int getID() {
        return ID;
    }

    /**
     * Getter of Key
     * @return      The key (priority) of the Node
     */
    public int getKey() {
        return key;
    }

    /**
     * Getter of parent
     * @return      The parent of the Node in the Graph
     */
    public int getParent() {
        return parent;
    }
}
