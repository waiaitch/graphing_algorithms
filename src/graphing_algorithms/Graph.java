package graphing_algorithms;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Graph {
	
	public static int comp;
	
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
                comp++;
            }

            //find cheapest edge to a not already visited destination.
            //remove() operations with logE
            Edge edge = edgeQueue.remove();
            comp++;
            while (nodesVisited[edge.destination] != 0) {
                if (edgeQueue.isEmpty()) {
                	for(int x=0;x<pi.length;x++) {
                		System.out.println("Vertex " + x + " is connected from" + pi[x]);
                	}
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
	
	static Graphs randomGraph(int vertex) {
    	int src, dest, weight;
    	int edges = ThreadLocalRandom.current().nextInt(vertex, (vertex*(vertex-1)/2));
    	//System.out.println("Random edges: " + edges);
		Graphs graph = new Graphs(vertex);
		for (int i = 0; i < edges; i++) {
			src = ThreadLocalRandom.current().nextInt(0, vertex);
			dest = ThreadLocalRandom.current().nextInt(1,vertex);
			weight = ThreadLocalRandom.current().nextInt(0,10);
			//System.out.println("Src: " + src + ",Dest: " + dest);
			graph.addEdge(src, dest, weight);
		}

		return graph;
	}
		 
	 public static void main(String arg[])
	    {
    	/*Graphs graph = new Graphs(5);
    	graph.addEdge(0,1,4);
	    graph.addEdge(0,2,3);
	   	graph.addEdge(0,3,8);
    	graph.addEdge(0,4,9);
    	graph.addEdge(2,1,2);
    	graph.addEdge(2,3,4);
    	graph.addEdge(1,4,2);
    	graph.printGraph();
    	*/
    	int src = 0;
    	double avgTime = 0;
    	int avgComp = 0;
    	int sample = 1;
    	
    	for(int i = 0; i < sample; i++) {
    		comp = 0;
    		//play around with vertices value
        	Graphs graph = randomGraph(5);
        	graph.printGraph();
        	
        	double startTime = System.nanoTime();
        	int[] d = Dijkstras2(graph, src);
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



class Graphs {
	int vertices;
	LinkedList<Edge> [] adjacencylist;

	@SuppressWarnings("unchecked")
	Graphs(int vertices) {
		this.vertices = vertices;
		adjacencylist = new LinkedList[vertices];
		//initialize adjacency lists for all the vertices
		 for (int i = 0; i <vertices ; i++) {
			 adjacencylist[i] = new LinkedList<>();
		 }
	 }

	 public void addEdge(int source, int destination, int weight) {
		 Edge edge = new Edge(source, destination, weight);
		 adjacencylist[source].addFirst(edge); //for directed graph
	 }
	
	 public void printGraph(){
	 for (int i = 0; i <vertices ; i++) {
		 LinkedList<Edge> list = adjacencylist[i];
			 for (int j = 0; j <list.size() ; j++) {
				 System.out.println("vertex-" + i + " is connected to " +
	 list.get(j).destination + " with weight " + list.get(j).weight);
			 }
		 }
	 }
 }