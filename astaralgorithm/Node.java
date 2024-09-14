/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package astaralgorithm;

/**
 *
 * @author johnlukedeneen
 */
public class Node implements Comparable<Node>{

    private int row, col, f, g, h, type;
    private Node parent;

    public Node(int r, int c, int t) {
        row = r;
        col = c;
        type = t;
        parent = null;
        //type 0 is traverseable, 1 is not
    }

    //CompareTo compares nodes based on F value to order heap (open list)
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.getF(), other.getF());
    }
    //mutator methods to set values
    public void setF() {
        f = g + h;
    }

    public void setG(int value) {
        g = value;
    }

    public void setH(int value) {
        h = value;
    }
    
    public void setT(int t){
        type = t;
    }

    public void setParent(Node n) {
        parent = n;
    }

    //accessor methods to get values
    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }
    
    public int getT() {
        return type;
    }

    public Node getParent() {
        return parent;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean equals(Object in) {
        //typecast to Node
        Node n = (Node) in;

        return row == n.getRow() && col == n.getCol();
    }

    public String toString() {
        return "Node: [" + row + "," + col + "]";
    }

}
