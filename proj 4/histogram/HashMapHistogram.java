package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import java.util.Set;
import java.util.stream.Collectors;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{

	private HashMap<T, Integer> list = new HashMap<T,Integer>();
	
	@Override
	public void addItem(T item) {
		int x=0;
		for(T t: list.keySet()) {
    		if (t!=null&&t.equals(item)) {
    			x=1;
    			list.put(item, list.get(t)+1);		
    		}
		}
    		if (x==0) {
    			list.put(item,1);	
    		}
	}
	
	@Override
	public boolean removeItem(T item)  {
		for(T t: this.list.keySet()) {
    		if (t.equals(item)&&list.get(t)!=0) {
    			list.put(item, list.get(t)-1);
    			return true;
    		}
    		}
		return false; 
	}
	
	
	@Override
	public void addAll(Collection<T> items) {
		for(T t: items) {
			addItem(t);
		}
	}

	@Override
	public int getCountForItem(T item) {
		int ret=0;
		if(list.containsKey(item)) {
          ret=list.get(item);
		}
		return ret; 
	}
	@Override
	public void clear() {
     list.clear();
		}

	@Override
	public Set<T> getItemsSet() {
		Set<T> set = list.keySet();
		return set;
	}
	
	@Override
	public int getCountsSum() {
		int sum=0;
		for(T t: this.list.keySet()) {
			sum=sum+list.get(t);
		}
		return sum;
	}

	@Override
	public Iterator<Map.Entry<T, Integer>> iterator() {
		return new HashMapHistogramIterator<T>(this.list);
	}


}
