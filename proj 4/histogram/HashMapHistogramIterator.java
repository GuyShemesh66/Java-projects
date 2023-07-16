package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Comparator;

import org.w3c.dom.Node;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class HashMapHistogramIterator<T extends Comparable<T>> 
							implements Iterator<Map.Entry<T, Integer>>{
	private List<Map.Entry<T, Integer>> list1;
	private Iterator<Map.Entry<T, Integer>> it;
	
		public HashMapHistogramIterator(Map<T, Integer> map){
			list1=new ArrayList<>();
			for(T t: map.keySet()) {
                  list1.add(Map.entry(t, map.get(t)));
				}	
			list1.sort(new ComparatorT());
			it=list1.iterator();
			}	

	 
	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public Map.Entry<T, Integer> next() {
		return  it.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
		
	}


	private class ComparatorT implements Comparator<Map.Entry<T, Integer>>{
	public int compare(Map.Entry<T, Integer> i1,Map.Entry<T,Integer> i2) {
		int  t= i1.getKey().compareTo(i2.getKey());
		if(t==0) {
			return i1.getValue().compareTo(i2.getValue());
		}
		else {
			return t;
		}
	}


}
}
