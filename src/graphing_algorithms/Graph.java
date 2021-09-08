package graphing_algorithms;

import java.util.Vector;
import java.util.LinkedList;

public class Graph {

	static class Edge {
	 int source;
	 int destination;
	 int weight;
	 
	 //Start of Creating Graph
	 public Edge(int source, int destination, int weight) {
		 this.source = source;
		 this.destination = destination;
		 this.weight = weight;
	 	}
	 }
	

	 static class Graphs {
		 int vertices;
		 LinkedList<Edge> [] adjacencylist;
	
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
	 //End of Creating Graph
	 
	 
	 //Start of min Heap
	 public int[] dij(Graphs G, int Source) {
		 
		 
		 
	 }
	 
	 
	 
	 public static void main(String[] args) {
		 int vertices = 5;
		 Graphs graph = new Graphs(vertices);
		 graph.addEdge(0, 1, 4);
		 graph.addEdge(0, 2, 3);
		 graph.addEdge(1, 3, 2);
		 graph.addEdge(1, 2, 5);
		 graph.addEdge(2, 3, 7);
		 graph.addEdge(3, 4, 2);
		 graph.addEdge(4, 0, 4);
		 graph.addEdge(4, 1, 4);
		 graph.addEdge(4, 5, 6);
		 graph.printGraph();
	 }
}
