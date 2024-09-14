package astaralgorithm;

import java.util.Random;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Implements the A* Algorithm for pathfinding in a 2D grid environment.
 * This class handles the setup of the environment (15x15 grid), the user input for start and goal nodes,
 * and the execution of the algorithm to find the shortest path from the start to the goal.
 * 
 * The algorithm works by maintaining a priority queue (open list) to explore the nodes,
 * and a closed list to track the visited nodes. The priority queue is ordered by the
 * 'f' value of each node (the estimated total cost of the path through the node).
 * 
 * @author John-Luke Deneen
 */
public class AStarAlgorithm {

    public static Node[][] enviornment = new Node[15][15]; // 2D grid representing the environment.
    public static Scanner scanner = new Scanner(System.in); // Scanner for user input.
    public static PriorityQueue<Node> openList = new PriorityQueue<>(); // Priority queue for open nodes.
    public static LinkedList<Node> closedList = new LinkedList<>(); // List for closed nodes.
    public static ArrayList<Node> reverseOrder = new ArrayList<>(); // Stores the final path in reverse order.
    public static Node currNode = new Node(0, 0, 0); // The current node being processed.
    public static int startRow; // Start node's row.
    public static int startCol; // Start node's column.
    public static int goalRow; // Goal node's row.
    public static int goalCol; // Goal node's column.
    public static boolean done; // Flag to indicate when the pathfinding is complete.

    /**
     * Main method that initiates the A* algorithm.
     * It prompts the user to input the start and goal nodes, then runs the algorithm
     * to find the shortest path from the start node to the goal node.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {

        // Randomly populate the environment with traversable and non-traversable nodes
        populateRandEnv(enviornment);
        System.out.println("0 is traversable, 1 is not");
        System.out.println();
        printEnv(enviornment);

        do {
            // Clear lists to reset the algorithm's state for each new pathfinding attempt
            System.out.println();
            reverseOrder.clear();
            closedList.clear();
            openList.clear();

            // Get user input for the start and goal nodes
            startRow = getStartRow();
            if (startRow == -1) {
                break;
            }
            startCol = getStartCol();
            goalRow = getGoalRow();
            goalCol = getGoalCol();

            sleep();
            System.out.println();
            System.out.println("Start Node selected at Row: " + startRow + " Col: " + startCol);
            sleep();
            System.out.println("Goal Node selected at Row: " + goalRow + " Col: " + goalCol);

            // Add the start node to the open list
            openList.add(enviornment[startRow][startCol]);

            done = false;

            // Continue running the main loop until the path is found or no solution exists
            while (!done) {
                mainLoop();
            }

        } while (startRow != -1);

        System.out.println("Exiting...");
        scanner.close();
    }

    /**
     * Main loop of the A* algorithm. It processes nodes from the open list until the path
     * to the goal is found or no solution exists.
     */
    public static void mainLoop() {
        // Get the node with the lowest f-value (estimated total cost) from the open list
        currNode = openList.poll();
        if (currNode == null) {
            System.out.println("Current node is null, no solution exists");
            return;
        }

        // Check if the current node is the goal node
        if (currNode.getCol() == goalCol && currNode.getRow() == goalRow) {
            // Trace the path back from the goal to the start using parent nodes
            sleep();
            System.out.println("Path found! Generating path...");
            System.out.println();
            sleep();

            // Reverse order to get the path from start to goal
            while (currNode != null) {
                reverseOrder.add(currNode);
                currNode = currNode.getParent();
            }

            // Print the path from start to goal
            for (int i = reverseOrder.size() - 1; i >= 0; i--) {
                Node node = reverseOrder.get(i);
                System.out.println(node);
            }

            done = true;
        } else {
            // Generate neighboring nodes, calculate their f-values, and add to the open list
            generateNeighbors(currNode);
            closedList.add(currNode);
        }
    }

    /**
     * Generates the neighbors of the current node, calculates their g, h, and f values,
     * sets their parent to the current node, and adds them to the open list if they are traversable.
     * 
     * @param node The current node for which to generate neighbors.
     */
    public static void generateNeighbors(Node node) {
        // Check all neighboring nodes in a 3x3 grid around the current node (including diagonals)
        for (int i = node.getRow() - 1; i <= node.getRow() + 1; i++) {
            for (int j = node.getCol() - 1; j <= node.getCol() + 1; j++) {
                // Skip the current node itself
                if (i == node.getRow() && j == node.getCol()) {
                    continue;
                }

                // Check if the neighbor is within the bounds of the grid and is traversable
                if ((i >= 0 && i < 15) && (j >= 0 && j < 15)
                        && (enviornment[i][j].getT() == 0)) {
                    // Skip if the node is already in the closed list
                    if (closedList.contains(enviornment[i][j])) {
                        continue;
                    }

                    // Set the G value (G represents the cost from the start node to this node 
                    //10 for non-diagonal, 14 for diagonal moves)
                    if (i == goalRow || j == goalCol) {
                        enviornment[i][j].setG(10);
                    } else {
                        enviornment[i][j].setG(14);
                    }

                    // Set the H value (represents the estimated cost to the goal) using Manhattan distance
                    int x = Math.abs(i - goalRow);
                    int y = Math.abs(j - goalCol);
                    enviornment[i][j].setH((x + y) * 10);

                    // Calculate and set the F value
                    enviornment[i][j].setF();

                    // Set the parent to the current node
                    enviornment[i][j].setParent(enviornment[currNode.getRow()][currNode.getCol()]);

                    // Add the neighbor to the open list
                    openList.add(enviornment[i][j]);
                }
            }
        }
    }

    /**
     * Populates the 2D grid with random traversable (0) and non-traversable (1) nodes.
     * A 10% chance is assigned to each node being non-traversable.
     * 
     * @param array The 2D array representing the environment grid.
     */
    public static void populateRandEnv(Node[][] array) {
        Random random = new Random();

        // Loop through the 2D array to randomly assign values
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                // 10% chance of assigning a non-traversable node
                int randNum = random.nextInt(10);
                if (randNum < 1) {
                    array[i][j] = new Node(i, j, 1);
                } else {
                    array[i][j] = new Node(i, j, 0);
                }
            }
        }
    }

    /**
     * Prints the current state of the environment grid, where 0 represents a traversable node
     * and 1 represents a non-traversable node.
     * 
     * @param array The 2D array representing the environment grid.
     */
    public static void printEnv(Node[][] array) {
        for (Node[] row : array) {
            for (Node element : row) {
                System.out.print(element.getT() + " ");
            }
            System.out.println();
        }
    }

    /**
     * Prompts the user for the row index of the start node.
     * 
     * @return The row index of the start node, or -1 to exit the program.
     */
    public static int getStartRow() {
        System.out.println("Please enter desired start node row index (0 - 14)");
        System.out.println("{Enter '-1' to exit}");
        return scanner.nextInt();
    }

    /**
     * Prompts the user for the column index of the start node.
     * 
     * @return The column index of the start node.
     */
    public static int getStartCol() {
        System.out.println("Please enter desired start node column index (0 - 14)");
        return scanner.nextInt();
    }

    /**
     * Prompts the user for the row index of the goal node.
     * 
     * @return The row index of the goal node.
     */
    public static int getGoalRow() {
        System.out.println("Please enter desired goal node row index (0 - 14)");
        return scanner.nextInt();
    }

    /**
     * Prompts the user for the column index of the goal node.
     * 
     * @return The column index of the goal node.
     */
    public static int getGoalCol() {
        System.out.println("Please enter desired goal node column index (0 - 14)");
        return scanner.nextInt();
    }

    /**
     * Pauses the program for a short period to simulate processing time.
     */
    public static void sleep() {
        try {
            Thread.sleep(700); // Delay for 700 milliseconds
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exception");
        }
    }

}
