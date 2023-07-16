package il.ac.tau.cs.sw1.ex7;
import java.util.*;

import il.ac.tau.cs.sw1.ex7.Graph.Edge;

public class FractionalKnapSack implements Greedy<FractionalKnapSack.Item>{
    int capacity;
    List<Item> lst;

    FractionalKnapSack(int c, List<Item> lst1){
        capacity = c;
        lst = lst1;
    }

    public static class Item {
        double weight, value,valuePerWeight,coefficient;
        Item(double w, double v) {
            weight = w;
            value = v;
            valuePerWeight=v/w;
            coefficient=0.0;
        }

        @Override
        public String toString() {
            return "{" + "weight=" + coefficient*weight + ", value=" + coefficient*value + '}';
        }
    }

    @Override
    public Iterator<Item> selection() {
    	 List<Item> copy=new ArrayList<>(lst) ;
    	copy.sort(sortedByVPW);
         return copy.iterator();
    }
Comparator<Item>sortedByVPW=(i2,i1)->Double.compare(i1.valuePerWeight,i2.valuePerWeight);
		 
    @Override
    public boolean feasibility(List<Item> candidates_lst, Item element) {
    	 return sum(candidates_lst) < capacity;
    }

    @Override
    public void assign(List<Item> candidates_lst, Item element) {
    	double x=0.0;
    	if(sum(candidates_lst) + element.weight <=capacity) {
    		element.coefficient=1;
    		candidates_lst.add(element);
    	}
    	else {
    		x=sum(candidates_lst)+ element.weight-capacity;
    		x=element.weight-x;
    		x=x/element.weight;
    		element.coefficient=x;
    		candidates_lst.add(element);
    	}
    }

    @Override
    public boolean solution(List<Item> candidates_lst) {
    	 return sum(candidates_lst)<=capacity;
    }
    
    private Double sum(List<Item> lst){
        double sum = 0.0;
        for (Item element : lst){
            sum += element.coefficient*element.weight;
        }
        return sum;
    }

}
