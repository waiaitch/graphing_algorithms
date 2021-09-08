package graphing_algorithms;

import java.util.Vector;

public class Part_B {
	
	int V; // No. of vertices
    Vector<Integer>[] adj; // No. of vertices

    
    // Constructor
    @SuppressWarnings("unchecked")
    Part_B(int V)
    {
        this.V = V;
        this.adj = new Vector[2 * V];

        for (int i = 0; i < 2 * V; i++)
            this.adj[i] = new Vector<>();
    }
    
	// adds an edge
    public void addEdge(int v, int w, int weight)
    {

        // split all edges of weight 2 into two
        // edges of weight 1 each. The intermediate
        // vertex number is maximum vertex number + 1,
        // that is V.
        if (weight == 2) 
        {
            adj[v].add(v + this.V);
            adj[v + this.V].add(w);
        } else // Weight is 1
            adj[v].add(w); // Add w to v's list.
    }

    
    public static void main(String[] args)
    {
  
        // Create a graph given in the above diagram
        int V = 4;
        Graph g = new Graph(V);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 2);
        g.addEdge(1, 2, 1);
        g.addEdge(1, 3, 1);
        g.addEdge(2, 0, 1);
        g.addEdge(2, 3, 2);
        g.addEdge(3, 3, 2);
  
        int src = 0, dest = 3;
        System.out.printf("\nShortest Distance between" + 
                            " %d and %d is %d\n", src, 
                            dest, g.findShortestPath(src, dest));
    }
}
