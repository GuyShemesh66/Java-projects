package il.ac.tau.cs.sw1.ex7;
import java.util.*;

import il.ac.tau.cs.sw1.ex7.FractionalKnapSack.Item;


public class Graph implements Greedy<Graph.Edge>{
    List<Edge> lst; //Graph is represented in Edge-List. It is undirected. Assumed to be connected.
    int n; //nodes are in [0,..., n]

    Graph(int n1, List<Edge> lst1){
        lst = lst1;
        n = n1;
    }

    public static class Edge{
        int node1, node2,minNode, maxNode;
        double weight;

        Edge(int n1, int n2, double w) {
            node1 =n1;
            node2 = n2;
            weight = w;
            minNode=Math.min(n1, n2);
            maxNode=Math.max(n1, n2);
            
        }

        @Override
        public String toString() {
            return "{" + "(" + node1 + "," + node2 + "), weight=" + weight + '}';
        }
    }

    @Override
    public Iterator<Edge> selection() {
    	 List<Edge> copy=new ArrayList<>(lst) ;
    	copy.sort(sortedByFirstNodes);
    	copy.sort(sortedByWeight);
        return copy.iterator();
    }

    Comparator<Edge>sortedByWeight=(i1,i2)->Double.compare(i1.weight,i2.weight);
    Comparator<Edge>sortedByFirstNodes=(i1,i2)->Integer.compare(i1.minNode,i2.minNode);
    @Override
    
    public boolean feasibility(List<Edge> candidates_lst, Edge element) {
    	if (element.node1==element.node2)
    		return false;
    	if(candidates_lst.isEmpty())
    		return true;
    	int [][] pathArr=new int [n+1][n+1];
    	for(Edge i: candidates_lst) {
    		pathArr[i.node1][i.node2]=1;
    		pathArr[i.node2][i.node1]=1;
    	}

        if(findShortestPath(pathArr, element.node1,element.node2)==-1) {
        		return true;
        }
    	return false;
    }

    @Override
    public void assign(List<Edge> candidates_lst, Edge element) {
    	candidates_lst.add(element);
    }

    @Override
    public boolean solution(List<Edge> candidates_lst) {
        return candidates_lst.size()== n;
    }
    

    private static int findShortestPath(int[][] m, int i, int j) {
        // TODO    
        if (i==j) {  //if the trail start and end in the same node
            return 0;
        }
        int [] arr=new int [m.length];
    int answer= findShortestPath(m,i,j,0,arr);
    if (answer==m.length+1) {//if there is no possible trail from i to j
        return -1;
    }
    else
        return answer;
    }

    private static int findShortestPath(int[][] m, int nextNode, int j, int minPath,int [] arr)
    {
        if(nextNode==j) {
            return minPath;// we arrived to the destination
        }
        arr[nextNode]=1;//mark the node we across in the path
        int path=0;
        int min=m.length+1;
        for(int x=0;x<m.length;x++) {
            if ((x!=nextNode)&&(m[nextNode][x]==1))// we check the possible way from the correct node
            {
            	if(arr[x]==0) {//make sure we will not across the same node twice in the same path
            min=Math.min(min,findShortestPath(m,x,j,minPath+1,arr));
                path=path+1;
            }
            }
        }
        arr[nextNode]=0;//unmark the node
        if (path==0){// the correct node is without a way to continue
            return m.length+1;
        }
        return min;
     
    }
}

