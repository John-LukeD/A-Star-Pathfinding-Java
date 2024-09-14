# A-Star-Pathfinding-Java
This repository implements the A* (A-star) algorithm to find the shortest and most efficient path between two nodes on a grid, while avoiding
obstacles. The algorithm evaluates possible paths by balancing the shortest distance traveled and the estimated cost to reach the goal.

## Features
- Randomly generated obstacles (10% chance of blocked nodes)
- User input for selecting start and goal nodes
- output of the path from start to goal
- Efficient pathfinding using a priority queue to explore the shortest paths first

## How It Works
The A* algorithm evaluates paths by considering two factors for each node:
- **G**: The exact cost to reach the current node from the start node.
- **H**: The estimated cost to reach the goal node from the current node (using the Manhattan distance in this case).
- **F**: The sum of G and H, representing the total estimated cost of the path.

The algorithm processes the node with the lowest F value first, ensuring that the most promising paths are explored. If diagonal movement is
allowed, it incurs a slightly higher cost (14 units) than straight movement (10 units).
Once the goal node is reached, the algorithm traces back through parent nodes to reconstruct the shortest path.

## How to Run
- Clone the repository
- Compile and run the AStarAlgorithm.java file

## Future Improvements
- Allow custom grid sizes and obstacle densities
- Add visualization for real-time pathfinding progress
- Support for weighted terrain (nodes with different movement costs)

## License
This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details
