package il.ac.tau.cs.sw1.ex5;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class BigramModel {
	public static final int MAX_VOCABULARY_SIZE = 14500;
	public static final String VOC_FILE_SUFFIX = ".voc";
	public static final String COUNTS_FILE_SUFFIX = ".counts";
	public static final String SOME_NUM = "some_num";
	public static final int ELEMENT_NOT_FOUND = -1;
	
	String[] mVocabulary;
	int[][] mBigramCounts;
	
	// DO NOT CHANGE THIS !!! 
	public void initModel(String fileName) throws IOException{
		mVocabulary = buildVocabularyIndex(fileName);
		mBigramCounts = buildCountsArray(fileName, mVocabulary);
		
	}
	
	

	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public String[] buildVocabularyIndex(String fileName) throws IOException{ // Q 1
		// replace with your code
		String[] arr= new String[MAX_VOCABULARY_SIZE];
		int j=0;
		int someNumCheck=0;
		BufferedReader reader= new BufferedReader(new FileReader(fileName));
		String line="";

	            line=reader.readLine();
	            
	           while(line!=null&&j<MAX_VOCABULARY_SIZE)
	           {
	        	   line=line.toLowerCase();
	        	   String [] lineArr=line.split(" ");
	        	   for(int k=0;k<lineArr.length;k++){
		        	   boolean checkIfWord=false;
		        	   boolean checkIfNum=false;
		        	   boolean already=false;
		        	   boolean checkIfLigalNum=true;
	        		for(int i=0;i<lineArr[k].length();i++)
	        		   
	        	   {
	        		   if(((int)lineArr[k].charAt(i)>=65&&(int)lineArr[k].charAt(i)<=90)||((int)lineArr[k].charAt(i)>=97&&(int)lineArr[k].charAt(i)<=122)) {
	        			   checkIfWord=true;
	        		   }
	        		   else if((int)lineArr[k].charAt(i)>=48&&(int)lineArr[k].charAt(i)<=57) {
	        			   checkIfNum=true;
	        		   }
	        		   else { 
	        			   checkIfLigalNum=false;
	        		   }
	        	   }
	        		String word=lineArr[k];
	        	   
	        		   for(int n=0;n<j;n++) {
	        			   if (arr[n].equals(word)) {
	        				   already=true;
	        			   }
	        		   }
	        	      if(checkIfWord&&!already&&!word.equals(SOME_NUM)) {
	        		   arr[j]=new String (word);
	        		   j=j+1;
	        	         }
	        	   else if(((checkIfLigalNum&&checkIfNum)||word.equals(SOME_NUM))&&someNumCheck==0) {
	        		   arr[j]=new String(SOME_NUM);
	        		   someNumCheck=1;
	        		   j=j+1;
	        	   }
	           }
	        	   line=reader.readLine();
	           }
		  mVocabulary=new String[j];

		  for(int k=0;k<mVocabulary.length;k++) {
			  mVocabulary[k]=arr[k];
		  }
		  reader.close();
		return  mVocabulary;
	}
	
	
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public int[][] buildCountsArray(String fileName, String[] vocabulary) throws IOException{ // Q - 2
		// replace with your code
		int[][] arr=new int[vocabulary.length][vocabulary.length]; 
		BufferedReader reader= new BufferedReader(new FileReader(fileName));
		String line="";
		line=reader.readLine();
		int test1=0,test2=0;
       while(line!=null)
       {
    	   line=line.toLowerCase();
    	   String [] lineArr=line.split(" ");
		for(int i=0;i<lineArr.length-1;i++) {
			test1=0;
    		for(int r=0;r<lineArr[i].length();r++){
    		   if((int)lineArr[i].charAt(r)>=48&&(int)lineArr[i].charAt(r)<=57) {
    			   test1=test1+1;
    		   }
    	   }
    		   if(test1==lineArr[i].length()) {
    			   lineArr[i]=new String(SOME_NUM);  
    		   } 
			for(int j=0;j<vocabulary.length;j++) {
				
				if(vocabulary[j].equals(lineArr[i])){
					test2=0;
		    		for(int x=0;x<lineArr[i+1].length();x++){
                           if((int)lineArr[i+1].charAt(x)>=48&&(int)lineArr[i+1].charAt(x)<=57) {
			    			  test2=test2+1;
			    		   }

			    		   }
			    		   if(test2==lineArr[i+1].length()) {
			    			   lineArr[i+1]=new String(SOME_NUM);  
			    		   } 
					for(int k=0;k<vocabulary.length;k++) {

						if(vocabulary[k].equals(lineArr[i+1])){
							arr[j][k]=arr[j][k]+1;
						}
				}
	     	}
          }
		}
		line=reader.readLine();
       }
       
       reader.close();
		return arr;
}
	
	/*
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: fileName is a legal file path
	 */
	public void saveModel(String fileName) throws IOException{ // Q-3
		// add your code here
		File voc=new File(fileName+VOC_FILE_SUFFIX);
		File count=new File(fileName+COUNTS_FILE_SUFFIX);
		BufferedWriter vocFile=new BufferedWriter(new FileWriter(voc));
		BufferedWriter countFile=new BufferedWriter(new FileWriter(count));
	 vocFile.write(mVocabulary.length+" words");
	 vocFile.write(System.lineSeparator());
	 for(int i=0;i<mVocabulary.length;i++) {
		 vocFile.write(i+","+mVocabulary[i]);
		 vocFile.write(System.lineSeparator());
	 }
	 for(int i=0;i<mBigramCounts.length;i++) {
		 for(int j=0;j<mBigramCounts[i].length;j++) {
			 if(mBigramCounts[i][j]!=0) {
				 countFile.write(i+","+j+":"+mBigramCounts[i][j]);
				 countFile.write(System.lineSeparator());
			 }
	 }
	 }
	 countFile.close();
	 vocFile.close();
	}
	
	
	/*
	 * @pre: fileName is a legal file path
	 */
	public void loadModel(String fileName) throws IOException{ // Q - 4
			String []arr= new String[MAX_VOCABULARY_SIZE];
			BufferedReader reader= new BufferedReader(new FileReader(fileName+VOC_FILE_SUFFIX));
			String line=reader.readLine();
			line=reader.readLine();
			int count=0;
			int k,j,m;
			while (line!=null&&count<MAX_VOCABULARY_SIZE) {
				m=1;
				while(line.charAt(m-1)!=',') {
					m=m+1;
				}
				arr[count]=line.substring(m, line.length());
				count=count+1;
				line=reader.readLine();
			}
			mVocabulary= new String[count];
			for(int i=0;i<mVocabulary.length;i++) {
				mVocabulary[i]=arr[i];
			}
			mBigramCounts=new int[mVocabulary.length][mVocabulary.length];
			reader= new BufferedReader(new FileReader(fileName+COUNTS_FILE_SUFFIX));
			line=reader.readLine();
			String s1,s2,s3;
			while(line!=null) {
				k=0;
				j=0;
				count=0;
				while(line.charAt(k)!=',') {
					k=k+1;
				}
				while(line.charAt(j)!=':') {
					j=j+1;
				}
				
				s1=new String(String.valueOf(line.substring(0,k)));
				s2=new String(String.valueOf(line.substring(k+1,j)));
				s3=new String(String.valueOf(line.substring(j+1)));
				k=Integer.parseInt(s1);
				j=Integer.parseInt(s2);
				count=Integer.parseInt(s3);
				mBigramCounts[k][j]=count;
				line=reader.readLine(); 
			}
				
			reader.close();	
		}

	
	
	/*
	 * @pre: word is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
	 */
	public int getWordIndex(String word){  // Q - 5
		String str=new String(word.toLowerCase());
		int test1=0;
		for(int r=0;r<str.length();r++){
 		   if((int)str.charAt(r)>=48&&(int)str.charAt(r)<=57) {
 			   test1=test1+1;
 		   }
 	   }
 		   if(test1==word.length()) {
 			  str=new String(SOME_NUM);  
 		   } 
		for (int i=0;i<mVocabulary.length;i++) {
			if(mVocabulary[i].equals(str)) {
				return i;
			}
		}
		// replace with your code
		return ELEMENT_NOT_FOUND;
	}
	
	
	
	/*
	 * @pre: word1, word2 are in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the count for the bigram <word1, word2>. if one of the words does not
	 * exist in the vocabulary, $ret = 0
	 */
	public int getBigramCount(String word1, String word2){ //  Q - 6
		// replace with your code
		int i=0;
		int j=0;
		i=getWordIndex(word1);
		j=getWordIndex(word2);
		if(i!=ELEMENT_NOT_FOUND&&j!=ELEMENT_NOT_FOUND&&mBigramCounts[i][j]>0) {
		return mBigramCounts[i][j];
		}
		return 0;
	}
	
	
	/*
	 * @pre word in lowercase, and is in mVocabulary
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post $ret = the word with the lowest vocabulary index that appears most fequently after word (if a bigram starting with
	 * word was never seen, $ret will be null
	 */
	public String getMostFrequentProceeding(String word){ //  Q - 7
		int i=getWordIndex(word);
		if(i==-1) {
			return null;
		}
		int maxIndex=0;
		int max=mBigramCounts[i][0];
		for (int j=0;j<mBigramCounts[i].length;j++) {
			if(max<mBigramCounts[i][j]) {
				max=mBigramCounts[i][j];
				maxIndex=j;
			}
		}
		if(max==0) {
			return null;
		}
		// replace with your code
		return mVocabulary[maxIndex];
	}
	
	
	/* @pre: sentence is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: each two words in the sentence are are separated with a single space
	 * @post: if sentence is is probable, according to the model, $ret = true, else, $ret = false
	 */
	public boolean isLegalSentence(String sentence){  //  Q - 8
		String str1="";
		String str2="";
		int startIndex=0;
		boolean check=true;
		String line=String.valueOf(sentence);
		line=line.toLowerCase();
		for(int k=0;k<line.length();k++) {
			if(line.charAt(k)!=' '&&check) {
					startIndex=k;
					check=false;	
				}
		}
		for(int k=0;k<line.length();k++) {
			if(k==line.length()-1||line.charAt(k)==' ') {
				str1=line.valueOf(line.substring(startIndex,k));
				line=line.valueOf(line.substring(k+1, line.length()));
				break;
			}
		}
		check=true;
		startIndex=0;
		while(line.length()!=0) {
		int k=0;
		check=true;
		startIndex=0;
		while(k<line.length()) {
			if(line.charAt(k)!=' '&&check) {
					startIndex=k;
					check=false;	
				}
			   if(k==line.length()-1||line.charAt(k)==' ') {
				   int t=0;
				   if (k==line.length()-1) {
					   t=1;
				   }
				str2=String.valueOf(line.substring(startIndex,k+startIndex+t));
				line=String.valueOf(line.substring(k+1, line.length()));
				break;
			}
			   k=k+1;
		}
								if(0>=getBigramCount(str1,str2)){
									return false;
								}
								str1=String.valueOf(str2);
					}			
		// replace with your code
		return true;
	}
	
	
	
	/*
	 * @pre: arr1.length = arr2.legnth
	 * post if arr1 or arr2 are only filled with zeros, $ret = -1, otherwise calcluates CosineSim
	 */
	public static double calcCosineSim(int[] arr1, int[] arr2){ //  Q - 9
		double ans=0.0;
		double sumAB=0.0;
		double sumA=0.0;
		double sumB=0.0;
		for(int i=0;i<arr1.length;i++) {
			sumAB=sumAB+(arr1[i]*arr2[i]);
			sumA=sumA+(arr1[i]*arr1[i]);
			sumB=sumB+(arr2[i]*arr2[i]);
		}
		if(sumA==0.0||sumB==0.0) {
			return  ELEMENT_NOT_FOUND;
		}
		ans =sumAB/(Math.sqrt(sumA)*Math.sqrt(sumB));
		
		// replace with your code
		return ans;
	}
	
	
	/*
	 * @pre: word is in vocabulary
	 * @pre: the method initModel was called (the language model is initialized), 
	 * @post: $ret = w implies that w is the word with the largest cosineSimilarity(vector for word, vector for w) among all the
	 * other words in vocabulary
	 */
	public String getClosestWord(String word){ //  Q - 10
		// replace with your code
		String str=new String(word.toLowerCase());
		int i=getWordIndex(str);
		int index=0;
		double max=calcCosineSim(mBigramCounts[i],mBigramCounts[0]);
		for(int k=1;k<mBigramCounts.length;k++) {
			if(max<calcCosineSim(mBigramCounts[i],mBigramCounts[k])&&i!=k) {
				index=k;
				 max=calcCosineSim(mBigramCounts[i],mBigramCounts[index]);
			}
		}
		return mVocabulary[index];
	}
 

}
