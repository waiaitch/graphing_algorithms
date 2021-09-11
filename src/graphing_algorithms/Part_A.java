package graphing_algorithms;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Part_A {
	
	public static int comp;

    public static int[] Dijkstras(int [][] graph, int source) {
        int [] nodesVisited = new int [graph.length];
        Queue<Edge> edgeQueue = new PriorityQueue<>();
        int [] shortestDistance = new int[graph.length];
        Arrays.fill(shortestDistance, Integer.MAX_VALUE);

        // Start with the source vertex.
        int currentNode = source;
        shortestDistance[source] = 0;
        nodesVisited[source] = 1;
        
        //adjacency matrix takes 
        
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
                comp++;
            }

            //find cheapest edge to a not already visited destination.
            //remove() operations with logE
            Edge edge = edgeQueue.remove();
            comp++;
            while (nodesVisited[edge.destination] != 0) {
                if (edgeQueue.isEmpty()) {
                    return shortestDistance;
                }
                edge = edgeQueue.remove();
                comp++;
            }

            // Now that you've reached this edge as a destination, record it.
            currentNode = edge.destination;
            nodesVisited[edge.destination] = 1;
            comp++;
        } while (!edgeQueue.isEmpty());

        return nodesVisited;
    }
    
    static GraphAdjacencyMatrix randomGraph(int vertex) {
    	int src, dest, weight;
    	int edges = ThreadLocalRandom.current().nextInt(vertex, (vertex*(vertex-1)/2));
    	//System.out.println("Random edges: " + edges);
		GraphAdjacencyMatrix graph = new GraphAdjacencyMatrix(vertex);
		for (int i = 0; i < edges; i++) {
			src = ThreadLocalRandom.current().nextInt(0, vertex);
			dest = ThreadLocalRandom.current().nextInt(0,vertex);
			weight = ThreadLocalRandom.current().nextInt(0,10);
			//System.out.println("Src: " + src + ",Dest: " + dest);
			graph.addEdge(src, dest, weight);
		}

		return graph;
	}
    
    // Main driver method
    public static void main(String arg[])
    {
//    	GraphAdjacencyMatrix graph = new GraphAdjacencyMatrix(5);
//    	graph.addEdge(0,1,9);
//    	graph.addEdge(0,2,6);
//    	graph.addEdge(0,3,5);
//    	graph.addEdge(0,4,3);
//    	graph.addEdge(2,1,2);
//    	graph.addEdge(2,3,4);
    	
    	int src = 0;
    	double avgTime = 0;
    	int avgComp = 0;
    	int sample = 10;
    	
    	for(int i = 0; i < sample; i++) {
    		//play around with vertices value
        	GraphAdjacencyMatrix graph = randomGraph(200);
        	graph.printGraph();
        	
        	double startTime = System.nanoTime();
        	int[] d = Dijkstras(graph.matrix, src);
        	double stopTime = System.nanoTime();
        	avgTime += (stopTime - startTime)/1000000;
        	avgComp += comp;
        	
        	
        	//System.out.println(Arrays.toString(Dijkstras(graph.matrix, src)));
        	
        	for(int j = 0; j < d.length; j++) {
        		System.out.println(String.format("Shortest path from %s to %s is %s", src, j, d[j]));
        	}
    	}
    	
    	
    	System.out.println("Average comparisons: " + avgComp/sample);
    	System.out.println("Average computational time taken: " + avgTime/sample + "ms");
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
	int edge;

	public GraphAdjacencyMatrix(int vertex) {
		this.vertex = vertex;
		matrix = new int[vertex][vertex];
	}

	public void addEdge(int source, int destination, int weight) {
		// add edge
		matrix[source][destination] = weight;

		// add back edge for undirected graph
		matrix[destination][source] = weight;
		edge++;
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
			System.out.print("Vertex " + i + " is connected to: ");
			for (int j = 0; j < vertex; j++) {
				if (matrix[i][j] != 0) {
					System.out.print(j + " ");
				}
			}
			System.out.println();
		}
		
		System.out.println("Edges: " + edge);
		System.out.println();
	}
}