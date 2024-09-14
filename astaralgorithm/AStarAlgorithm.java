/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package astaralgorithm;

import java.util.Random;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.ArrayList;
//[row][col]

/**
 *
 * @author johnlukedeneen
 */
public class AStarAlgorithm {

    public static Node[][] enviornment = new Node[15][15];
    public static Scanner scanner = new Scanner(System.in);
    public static PriorityQueue<Node> openList = new PriorityQueue<>();
    public static LinkedList<Node> closedList = new LinkedList<>();
    public static ArrayList<Node> reverseOrder = new ArrayList<>();
    public static Node currNode = new Node(0, 0, 0);
    public static int startRow;
    public static int startCol;
    public static int goalRow;
    public static int goalCol;
    public static boolean done;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        populateRandEnv(enviornment);
        System.out.println("0 is traverseable, 1 is not");
        System.out.println();
        printEnv(enviornment);

        do {

            //clear all lists at beginning of each itteration
            System.out.println();
            reverseOrder.clear();
            closedList.clear();
            openList.clear();

            //get user input for start and goal nodes
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
            //Add start node to open list
            openList.add(enviornment[startRow][startCol]);
            //set done to false to keep 
            done = false;

            while (done == false) {
                mainLoop();
            }

        } while (startRow != -1);

        System.out.println("Exiting...");
        scanner.close();

    }

    public static void mainLoop() {
        //pop best node from open list and set as current node
        currNode = openList.poll();
        if (currNode == null) {
            System.out.println("Current node is null, no solution exists");
            return;
        }
        //check if current node is goal node
        if (currNode.getCol() == goalCol && currNode.getRow() == goalRow) {
            //stop searching and generate path
            sleep();
            System.out.println("Path found! generating path...");
            System.out.println();
            sleep();
            while (currNode != null) {
                //Trace from goal node back to start node through parents
                //and add to reverseOrder
                reverseOrder.add(currNode);
                currNode = currNode.getParent();
            }
            //print out each node in reverse order to get the trail start to finish
            for (int i = reverseOrder.size() - 1; i >= 0; i--) {
                Node node = reverseOrder.get(i);
                System.out.println(node);
            }
            //exit main loop
            done = true;
        } else {
            //generate neighbors -- method includes:
            //setting G,H,F,Parent, and adding each node to heap (open list)
            generateNeighbors(currNode);
            // add current node to closed list
            closedList.add(currNode);
            //go back to first step of main loop
        }

    }

    public static void generateNeighbors(Node node) {
        //iterate through 8 neighbor nodes
        for (int i = node.getRow() - 1; i <= node.getRow() + 1; i++) {
            for (int j = node.getCol() - 1; j <= node.getCol() + 1; j++) {
                //skip over curr node when generating neighbors
                if (i == node.getRow() && j == node.getCol()) {
                    continue;
                }
                //check if each neighbor node is at valid index and pathable
                if ((i >= 0 && i < 15) && (j >= 0 && j < 15)
                        && (enviornment[i][j].getT() == 0)) {
                    // Skip if the node is in the closed list
                    if (closedList.contains(enviornment[i][j])) {
                        continue;
                    }
                    //set G
                    //check if node is non-diagonal, if so g = 10, else g = 14
                    if (i == goalRow || j == goalCol) {
                        enviornment[i][j].setG(10);
                    } else {
                        enviornment[i][j].setG(14);
                    }
                    //set H (manhattan method)
                    int x = Math.abs(i - goalRow);
                    int y = Math.abs(j - goalCol);
                    enviornment[i][j].setH((x + y) * 10);
                    //set F
                    enviornment[i][j].setF();
                    //set parent
                    enviornment[i][j].setParent(enviornment[currNode.getRow()][currNode.getCol()]);
                    //add to open list (nodeHeap)
                    openList.add(enviornment[i][j]);

                }
            }
        }

    }

    //Method to randomly populate enviornment
    public static void populateRandEnv(Node[][] array) {
        Random random = new Random();
        //loop through 2d array
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                //random number between 0 and 9
                int randNum = random.nextInt(10);
                //if random number is 0 (10% chance) assign value of 1
                //to represent unpathable block
                if (randNum < 1) {
                    array[i][j] = new Node(i, j, 1);
                    //else is number is > 0, assign value of 0 to represent
                    //pathable block
                } else {
                    array[i][j] = new Node(i, j, 0);
                }

            }
        }
    }

    public static void printEnv(Node[][] array) {
        for (Node[] row : array) {
            for (Node element : row) {
                System.out.print(element.getT() + " ");
            }
            System.out.println();
        }
    }

    public static int getStartRow() {
        System.out.println("Please enter desired start node row index (0 - 14) ");
        System.out.println("{Enter '-1' to exit}");
        int startRow = scanner.nextInt();
        return startRow;
    }

    public static int getStartCol() {
        System.out.println("Please enter desired start node col index (0 - 14)");
        int startCol = scanner.nextInt();
        return startCol;
    }

    public static int getGoalRow() {
        System.out.println("Please enter desired goal node row index (0 - 14)");
        int goalRow = scanner.nextInt();
        return goalRow;
    }

    public static int getGoalCol() {
        System.out.println("Please enter desired goal node col index (0 - 14)");
        int goalCol = scanner.nextInt();
        return goalCol;
    }

    //extra method to make execution flow better
    public static void sleep() {
        try {
            // Delay for (700 milliseconds)
            Thread.sleep(700);
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exception");
        }
    }

}
