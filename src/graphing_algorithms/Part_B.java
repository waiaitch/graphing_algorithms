package graphing_algorithms;

import java.util.*;


public class Part_B {

	public class DirectedWeightedGraph<E> {

		// Map having Vertex as key and List of Edges as Value.
		Map<Vertex<E>, List<Edge<E>>> adj = new HashMap<>();


		public static class Vertex<E> {
		    E value;

		    public Vertex(E value) {
		        this.value = value;
		    }
		}

		public static class Edge<E> {
		    E from;
		    E to;
		    double weight;

		    public Edge(E from, E to, double weight) {
		        this.from = from;
		        this.to = to;
		        this.weight = weight;
		    }

		}

		public void addVertex(E value) {
		    Vertex<E> v = new Vertex<E>(value);
		    List<Edge<E>> edges = new ArrayList<>();
		    this.adj.put(v, edges);
		}

		public void addEdge(E from, E to, double weight) {
		    List<Edge<E>> fromEdges =  this.;
		    List<Edge<E>> toEdges =  this.getEdges(from);

		    // Add source vertex and then add edge
		    if(fromEdges == null) {
		        this.addVertex(from);
		    }
		    if(toEdges == null) {
		        this.addVertex(to);
		    }

		    fromEdges.add(new Edge<E>(from, to, weight));
		}
	
    public static void main (String[] args) {
        
    }
	
}
