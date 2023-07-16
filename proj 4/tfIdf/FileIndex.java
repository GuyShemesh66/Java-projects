package il.ac.tau.cs.sw1.ex8.tfIdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {
	
	private boolean isInitialized = false;
	private HashMap<String,HashMapHistogram<String>>  list;
	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 * @pre: isInitialized() == false;
	 */
  	public void indexDirectory(String folderPath) { //Q1
		//This code iterates over all the files in the folder. add your code wherever is needed
  		List<String> stringList;
		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		HashMap<String,HashMapHistogram<String>> newList=new HashMap<>();
		for (File file : listFiles) {
			// for every file in the folder
			if (file.isFile()) {
				try {
					stringList=FileUtils.readAllTokens(file);
				    HashMapHistogram<String> count=new HashMapHistogram<>();
				    count.addAll(stringList);
				    newList.put(file.getName(),count);
					}
			 catch (IOException e) {}
			}
		}
	      list=newList;
		isInitialized = true;
	}
  	
	// Q2
  	
	/* @pre: isInitialized() */
	public int getCountInFile(String word, String fileName) throws FileIndexException{ 
		// add your code here
		int count=0;
	
		String copyWord=word.toLowerCase();
		if(this.list.containsKey(fileName)) {
			count=this.list.get(fileName).getCountForItem(copyWord);
			return count;
		}
			throw new FileIndexException("file doesnt exist or word is not defined");
		}
		
	
	/* @pre: isInitialized() */
	public int getNumOfUniqueWordsInFile(String fileName) throws FileIndexException{ 
		int sum=0;
		try {
		for(Map.Entry<String,Integer> s : list.get(fileName)) {
			if(s.getValue()>0)
			 sum=sum+1;
		}
		return sum;
	}catch(Exception e) {
		throw new FileIndexException("file doesnt exist");
	}
	}
	
	/* @pre: isInitialized() */
	public int getNumOfFilesInIndex(){
		int count=0;
		try {
		for (String s: list.keySet())
				if (getNumOfUniqueWordsInFile(s)>0)
					count=count+1;
			} catch (FileIndexException e) {}
				
		// add your code here
		return count; //replace this with the correct value
	}

	
	/* @pre: isInitialized() */
	public double getTF(String word, String fileName) throws FileIndexException{ // Q3
		int count=0;
		int count2=0;
		count2=list.get(fileName).getCountsSum();
		count=list.get(fileName).getCountForItem(word.toLowerCase());
		if (count2==0)
			throw new FileIndexException("file doesnt exist");
		// add your code here
		return calcTF(count,count2); 
	}
	
	/* @pre: isInitialized() 
	 * @pre: exist fileName such that getCountInFile(word) > 0*/
	public double getIDF(String word){ //Q4
		// add your code here
		int count=this.getNumOfFilesInIndex();
		int count2=0;
		for (String s: list.keySet()) {
			if(list.get(s).getCountForItem(word.toLowerCase())>0)
			count2=count2+1;
		}
		if (count2==0)
			return Double.POSITIVE_INFINITY;

		return calcIDF(count,count2); 
	}
	
	
	
	/*
	 * @pre: isInitialized()
	 * @pre: 0 < k <= getNumOfUniqueWordsInFile(fileName)
	 * @post: $ret.size() = k
	 * @post for i in (0,k-2):
	 * 		$ret[i].value >= $ret[i+1].value
	 */
	public List<Map.Entry<String, Double>> getTopKMostSignificantWords(String fileName, int k) 
													throws FileIndexException{ //Q5
		int i=0;
	    List<Map.Entry<String,Double>> list1 = new ArrayList<>();
	    for(Map.Entry<String,Integer> s : list.get(fileName)) {
		     list1.add(Map.entry(s.getKey(), getTFIDF(s.getKey(),fileName)));	
	    }  
	    
	    Collections.sort(list1,new ComparatorFile());
		List<Map.Entry<String, Double>> newList = new ArrayList<Map.Entry<String, Double>>();
		for(Map.Entry<String,Double> s : list1) {
			if (i==k)
				break;
			newList.add(s);
			i++;
		}
		// add your code here
		return  newList; //replace this with the correct value
	}
	
	/* @pre: isInitialized() */
	public double getCosineSimilarity(String fileName1, String fileName2) throws FileIndexException{ //Q6
	HashMap<String,Double> l1 = new HashMap<>();
		    for(Map.Entry<String,Integer> s : list.get(fileName1)) {
			     l1.put(s.getKey(), getTFIDF(s.getKey(),fileName1));	
		    }  
		    HashMap<String,Double> l2 = new HashMap<>();
		    for(Map.Entry<String,Integer> s : list.get(fileName2)) {
			     l2.put(s.getKey(), getTFIDF(s.getKey(),fileName2));	
		    } 
		double ans=0.0;
		double sumAB=0.0;
		double sumA=0.0;
		double sumB=0.0;
		for(Map.Entry<String,Integer> s : list.get(fileName1)) {
			sumA=sumA+(l1.get(s.getKey())*l1.get(s.getKey()));
		}
		for(Map.Entry<String,Integer> s : list.get(fileName2)) {
			if (list.get(fileName1).getCountForItem(s.getKey())>0) {
			sumAB=sumAB+(l1.get(s.getKey())*l2.get(s.getKey()));
			}
			sumB=sumB+(l2.get(s.getKey())*l2.get(s.getKey()));
		}

		if(sumA==0.0||sumB==0.0) {
			return  0;
		}
		ans =sumAB/(Math.sqrt(sumA*sumB));
		// replace with your code
		return ans;

		// add your code here
	}
	
	/*
	 * @pre: isInitialized()
	 * @pre: 0 < k <= getNumOfFilesInIndex()-1
	 * @post: $ret.size() = k
	 * @post for i in (0,k-2):
	 * 		$ret[i].value >= $ret[i+1].value
	 */
	public List<Map.Entry<String, Double>> getTopKClosestDocuments(String fileName, int k) 
			throws FileIndexException{ //Q6
		 List<Map.Entry<String,Double>> list1 = new ArrayList<>();
		 for(String s : list.keySet()) {
			 if(list.get(s).getCountsSum()>0&&!s.equals(fileName))
			 list1.add(Map.entry(s, getCosineSimilarity(fileName,s)));	
		 }
		 Collections.sort(list1,new ComparatorFile());
		 List<Map.Entry<String,Double>> list2 = new ArrayList<>();
		for (int i=0;i<k;i++) {
			list2.add(list1.get(i));
		}
		// add your code here
		return list2; //replace this with the correct value
	}
	
	private class ComparatorFile implements Comparator<Map.Entry<String, Double>>{
	public int compare(Map.Entry<String, Double> i1,Map.Entry<String, Double> i2) {
		double t= i2.getValue()-i1.getValue();
		if(t==0) {
			return i1.getKey().compareTo(i2.getKey());
		}
		else {
			if (t<0)
				return-1;
			else
			return 1;
		}
	}
	}
	//add private methods here, if needed

	
	/*************************************************************/
	/********************* Don't change this ********************/
	/*************************************************************/
	
	public boolean isInitialized(){
		return this.isInitialized;
	}
	
	/* @pre: exist fileName such that getCountInFile(word) > 0*/
	public double getTFIDF(String word, String fileName) throws FileIndexException{
		return this.getTF(word, fileName)*this.getIDF(word);
	}
	
	private static double calcTF(int repetitionsForWord, int numOfWordsInDoc){
		return (double)repetitionsForWord/numOfWordsInDoc;
	}
	
	private static double calcIDF(int numOfDocs, int numOfDocsContainingWord){
		return Math.log((double)numOfDocs/numOfDocsContainingWord);
	}

	
}
