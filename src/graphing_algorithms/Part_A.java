package graphing_algorithms;

import java.util.*;

public class Part_A {
	
    public static void printDijkstras(int [][] graph, int source) {
        // Prints shortest distances to all nodes from the source.
        System.out.println(Arrays.toString(Dijkstras(graph, source)));
    }

    public static int[] Dijkstras(int [][] graph, int source) {
        // Ideally should check matrix's validity to be square and symmetric but haven't done here since its pretty straightforward.

        int [] nodesVisited = new int [graph.length];
        Queue<Edge> edgeQueue = new PriorityQueue<>();
        int [] shortestDistance = new int[graph.length];
        Arrays.fill(shortestDistance, Integer.MAX_VALUE);

        // Start with the source vertex.
        int currentNode = source;
        shortestDistance[0] = 0;
        nodesVisited[0] = 1;

        do {
            for (int col = 0; col<graph[currentNode].length; col++) {
                // Take all edges from this node. Skip self & 0-weight edges
                if (col == currentNode || graph[currentNode][col] == 0) {
                    continue;
                }
                edgeQueue.add(new Edge(currentNode, col, graph[currentNode][col]));

                // Update distance for this destination if better than existing.
                if (shortestDistance[col] > shortestDistance[currentNode] + graph[currentNode][col]) 
                {
                    shortestDistance[col] = shortestDistance[currentNode] + graph[currentNode][col];
                }
            }

            //find cheapest edge to a not already visited destination.
            Edge edge = edgeQueue.remove();
            while (nodesVisited[edge.destination] == 1) {
                if (edgeQueue.isEmpty()) {
                    return shortestDistance;
                }
                edge = edgeQueue.remove();
            }

            // Now that you've reached this edge as a destination, record it.
            currentNode = edge.destination;
            nodesVisited[edge.destination] = 1;
        } while (!edgeQueue.isEmpty());

        return nodesVisited;
    }
    
    // Main driver method
    public static void main(String arg[])
    {
    	GraphAdjacencyMatrix graph = new GraphAdjacencyMatrix(5);
    	graph.addEdge(0,1,9);
    	graph.addEdge(0,2,6);
    	graph.addEdge(0,3,5);
    	graph.addEdge(0,4,3);
    	graph.addEdge(2,1,2);
    	graph.addEdge(2,3,4);
    	
    	printDijkstras(graph.matrix, 0);
    }
}

class Edge implements Comparable<Edge> {
    public int source;
    public int destination;
    public int weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public String toString() {
        return source + "---" + weight + "---" + destination;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }

//    @Override
//    public boolean equals(Object other) {
//    if (other instance of WeightedGraphMatrix.Edge) {
//            return equals((WeightedGraphMatrix.Edge)other);
//        }
//        return false;
//    }

    private boolean equals(Edge other) {
        return other.destination == this.destination;
    }

    @Override // Uses only the destination to calculate hash for the purposes of maintaining the visited hashset.
    public int hashCode() {
        int hash = 17;
        int hashMultiplikator = 79;
        return hash * hashMultiplikator * destination;
    }
}

class GraphAdjacencyMatrix {
	int vertex;
	int matrix[][];

	public GraphAdjacencyMatrix(int vertex) {
		this.vertex = vertex;
		matrix = new int[vertex][vertex];
	}

	public void addEdge(int source, int destination, int weight) {
		// add edge
		matrix[source][destination] = weight;

		// add back edge for undirected graph
		matrix[destination][source] = weight;
	}

	public void printGraph() {
		System.out.println("Graph: (Adjacency Matrix)");
		for (int i = 0; i < vertex; i++) {
			for (int j = 0; j < vertex; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		for (int i = 0; i < vertex; i++) {
			System.out.print("Vertex " + i + " is connected to:");
			for (int j = 0; j < vertex; j++) {
				if (matrix[i][j] == 1) {
					System.out.print(j + " ");
				}
			}
			System.out.println();
		}
	}
}