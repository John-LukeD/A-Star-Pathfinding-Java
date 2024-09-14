package astaralgorithm;

/**
 * The {@code Node} class represents a node in the A* algorithm. 
 * Each node stores its position in a grid, cost values (f, g, h), 
 * type (traversable or not), and its parent node for pathfinding purposes.
 * 
 * <p>Implements {@link Comparable} to enable comparison of nodes based on 
 * their 'f' value (sum of g and h) for priority queue ordering in A*.</p>
 * 
 * @author John-Luke Deneen
 */
public class Node implements Comparable<Node> {

    private int row, col, f, g, h, type;
    private Node parent;

    /**
     * Constructs a {@code Node} with the specified row, column, and type.
     * 
     * @param r the row position of the node
     * @param c the column position of the node
     * @param t the type of the node (0 for traversable, 1 for non-traversable)
     */
    public Node(int r, int c, int t) {
        this.row = r;
        this.col = c;
        this.type = t;
        this.parent = null;
    }

    /**
     * Compares this node with another node based on the 'f' value.
     * Used for ordering nodes in priority queues in the A* algorithm.
     * 
     * @param other the other node to compare against
     * @return a negative integer, zero, or a positive integer as this node's 
     * 'f' value is less than, equal to, or greater than the specified node's 'f' value
     */
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.getF(), other.getF());
    }

    /**
     * Sets the 'f' value, which is the sum of 'g' and 'h' values.
     */
    public void setF() {
        this.f = this.g + this.h;
    }

    /**
     * Sets the 'g' value, which represents the cost from the start node 
     * to this node.
     * 
     * @param value the 'g' value to set
     */
    public void setG(int value) {
        this.g = value;
    }

    /**
     * Sets the 'h' value, which is the estimated cost from this node 
     * to the goal.
     * 
     * @param value the 'h' value to set
     */
    public void setH(int value) {
        this.h = value;
    }

    /**
     * Sets the type of the node.
     * 
     * @param t the type to set (0 for traversable, 1 for non-traversable)
     */
    public void setT(int t) {
        this.type = t;
    }

    /**
     * Sets the parent node of this node.
     * 
     * @param n the parent node
     */
    public void setParent(Node n) {
        this.parent = n;
    }

    /**
     * Returns the 'f' value, which is the sum of 'g' and 'h'.
     * 
     * @return the 'f' value
     */
    public int getF() {
        return f;
    }

    /**
     * Returns the 'g' value, which represents the cost from the start node 
     * to this node.
     * 
     * @return the 'g' value
     */
    public int getG() {
        return g;
    }

    /**
     * Returns the 'h' value, which represents the estimated cost 
     * from this node to the goal.
     * 
     * @return the 'h' value
     */
    public int getH() {
        return h;
    }

    /**
     * Returns the type of the node (0 for traversable, 1 for non-traversable).
     * 
     * @return the type of the node
     */
    public int getT() {
        return type;
    }

    /**
     * Returns the parent node of this node.
     * 
     * @return the parent node
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Returns the row position of this node.
     * 
     * @return the row position
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column position of this node.
     * 
     * @return the column position
     */
    public int getCol() {
        return col;
    }

    /**
     * Checks if this node is equal to another object.
     * Nodes are considered equal if they have the same row and column.
     * 
     * @param in the object to compare with
     * @return {@code true} if the nodes have the same row and column, 
     * {@code false} otherwise
     */
    @Override
    public boolean equals(Object in) {
        if (this == in) {
            return true;
        }
        if (in == null || getClass() != in.getClass()) {
            return false;
        }
        Node n = (Node) in;
        return row == n.getRow() && col == n.getCol();
    }

    /**
     * Returns a string representation of this node, showing its 
     * row and column position.
     * 
     * @return a string representation of the node
     */
    @Override
    public String toString() {
        return "Node: [" + row + "," + col + "]";
    }

}
