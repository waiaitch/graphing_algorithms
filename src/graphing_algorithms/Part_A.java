package graphing_algorithms;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Part_A {
	
	public static int comp;
	public static GraphAdjacencyMatrix graph;
	public static Graphs graph2;
	public static int comp2;
	

    public static int[] Dijkstras(int [][] graph, int source) {
        int [] nodesVisited = new int [graph.length];
        int [] shortestDistance = new int[graph.length];
        Arrays.fill(shortestDistance, Integer.MAX_VALUE);
        int [] pi = new int[graph.length]; //Source array
        int [] Q = new int[graph.length+1];
        Arrays.fill(Q, -1);
        Q[graph.length] = 0; //Store priority queue size
        // Start with the source vertex.
        int currentNode = source;
        shortestDistance[source] = 0;
        nodesVisited[source] = 1;
        
        //adjacency matrix takes 
        
        do {
            for (int col = 0; col<graph[currentNode].length; col++) {
                // Take all edges from this node. Skip self & 0-weight edges
                if (col == currentNode || graph[currentNode][col] == 0 || nodesVisited[col]==1) {
                    continue;
                }

                // Update distance for this destination if better than existing.
                if (shortestDistance[col] > shortestDistance[currentNode] + graph[currentNode][col]) 
                {
                	pi[col] = currentNode;
                	shortestDistance[col] = shortestDistance[currentNode] + graph[currentNode][col];
                	insert(Q,col,shortestDistance[col]);
                }
                comp++;
            }

            //find cheapest edge to a not already visited destination.
            //remove() operations with logE
            int edge = remove(Q);
            comp++;
            while (nodesVisited[edge] != 0) {
                if (Q[Q.length-1]==0) {
                	for(int x=0;x<pi.length;x++) {
                		System.out.println("Vertex " + x + " is connected from" + pi[x]);
                	}
                    return shortestDistance;
                }
                edge = remove(Q);
                comp++;
            }

            // Now that you've reached this edge as a destination, record it.
            currentNode = edge;
            nodesVisited[edge] = 1;
            Q[edge] = -1;
            comp++;
        } while (Q[Q.length-1]!=0);

        for(int x=0;x<pi.length;x++) {
    		System.out.println("Vertex " + x + " is connected from" + pi[x]);
    	}
        return shortestDistance;
    }
    
    //Array Priority Queue
    public static void insert(int [] Q, int vert, int dist) {
    	if(Q[vert]==-1)
    		Q[Q.length-1]++;
    	Q[vert] = dist;
    }
    
    public static int remove(int [] Q) {
    	int vert = -1;
    	int min = Integer.MAX_VALUE;
    	for(int x=0;x<Q.length-1;x++) {
    		if(Q[x]<min && Q[x]!=-1) {
    			min = Q[x];
    			vert = x;
    		}
    	}
    	Q[Q.length-1]--;
    	return vert;
    }
    //End of Array Priority Queue
    //End of Dijkstra for Matrix
    
    //Start of Dijkstra for List
    public static int[] Dijkstras2(Graphs graph, int source) {
        int [] nodesVisited = new int [graph.vertices];
        Queue<Edge> edgeQueue = new PriorityQueue<>();
        int [] shortestDistance = new int[graph.vertices];
        Arrays.fill(shortestDistance, Integer.MAX_VALUE);
        int [] pi = new int[graph.vertices];
        // Start with the source vertex.
        int currentNode = source;
        shortestDistance[source] = 0;
        nodesVisited[source] = 1;
        
        //adjacency matrix takes 
        
        do {
        	LinkedList<Edge> list = graph.adjacencylist[currentNode];
            for (int col = 0; col<list.size();col++) {
                // Take all edges from this node. Skip self & 0-weight edges
                if (list.get(col).weight == 0) {
                    continue;
                }
                edgeQueue.add(new Edge(currentNode, list.get(col).destination, list.get(col).weight));

                // Update distance for this destination if better than existing.
                if (shortestDistance[list.get(col).destination] > shortestDistance[currentNode] + list.get(col).weight) 
                {
                	pi[list.get(col).destination] = currentNode;
                    shortestDistance[list.get(col).destination] = shortestDistance[currentNode] + list.get(col).weight;
                }
                comp2++;
            }

            //find cheapest edge to a not already visited destination.
            //remove() operations with logE
            Edge edge = edgeQueue.remove();
            comp2++;
            while (nodesVisited[edge.destination] != 0) {
                if (edgeQueue.isEmpty()) {
                	for(int x=0;x<pi.length;x++) {
                		System.out.println("Vertex " + x + " is connected from" + pi[x]);
                	}
                    return shortestDistance;
                }
                edge = edgeQueue.remove();
                comp2++;
            }

            // Now that you've reached this edge as a destination, record it.
            currentNode = edge.destination;
            nodesVisited[edge.destination] = 1;
            comp2++;
        } while (!edgeQueue.isEmpty());

        return nodesVisited;
    }
    //End
    
    static GraphAdjacencyMatrix randomGraph(int vertex) {
    	int src, dest, weight;
    	int edges = ThreadLocalRandom.current().nextInt(vertex, (vertex*(vertex-1)/2));
    	//System.out.println("Random edges: " + edges);
		graph = new GraphAdjacencyMatrix(vertex);
		graph2 = new Graphs(vertex);
		for (int i = 0; i < edges; i++) {
			src = ThreadLocalRandom.current().nextInt(0, vertex);
			dest = ThreadLocalRandom.current().nextInt(1,vertex);
			weight = ThreadLocalRandom.current().nextInt(0,10);
			//System.out.println("Src: " + src + ",Dest: " + dest);
			graph.addEdge(src, dest, weight);
			graph2.addEdge(src, dest, weight);
		}

		return graph;
	}
    
    // Main driver method
    public static void main(String arg[])
    {
    	/*GraphAdjacencyMatrix graph = new GraphAdjacencyMatrix(5);
    	graph.addEdge(0,1,4);
	    graph.addEdge(0,2,7);
	   	graph.addEdge(1,4,2);
    	graph.addEdge(1,3,3);
    	graph.addEdge(2,3,6);
    	graph.addEdge(3,2,3);
    	graph.addEdge(1,4,2);
    	*/
    	int src = 0;
    	double avgTime = 0;
    	int avgComp = 0;
    	double avgTime2 = 0;
    	int avgComp2 = 0;
    	int sample = 1;
    	
    	for(int i = 0; i < sample; i++) {
    		comp = 0;
    		comp2 = 0;
    		//play around with vertices value
        	randomGraph(5);
        	
        	graph2.printGraph();
        	
        	double startTime = System.nanoTime();
        	int[] d = Dijkstras(graph.matrix, src);
        	double stopTime = System.nanoTime();
        	avgTime += (stopTime - startTime)/1000000;
        	avgComp += comp;
        	double startTime2 = System.nanoTime();
        	int[] d2 = Dijkstras2(graph2, src);
        	double stopTime2 = System.nanoTime();
        	avgTime2 += (stopTime2 - startTime2)/1000000;
        	avgComp2 += comp;
        	
        	//System.out.println(Arrays.toString(Dijkstras(graph.matrix, src)));
        	
        	for(int j = 0; j < d.length; j++) {
        		System.out.println(String.format("Shortest path from %s to %s is %s", src, j, d[j]));
        	}
        	for(int j = 0; j < d.length; j++) {
        		System.out.println(String.format("Shortest path from %s to %s is %s", src, j, d2[j]));
        	}
    	}
    	
    	
    	System.out.println("Average comparisons: " + avgComp/sample);
    	System.out.println("Average computational time taken: " + avgTime/sample + "ms");
    	System.out.println("Average comparisons: " + avgComp2/sample);
    	System.out.println("Average computational time taken: " + avgTime2/sample + "ms");
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