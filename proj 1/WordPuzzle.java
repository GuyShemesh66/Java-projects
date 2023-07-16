package il.ac.tau.cs.sw1.ex4;

import java.util.Scanner;

public class WordPuzzle {
	public static final char HIDDEN_CHAR = '_';
	
	/*
	 * @pre: template is legal for word
	 */
	public static char[] createPuzzleFromTemplate(String word,boolean[] template) { // Q - 1
		// Replace this with your code
		char[] puzzle =new char[word.length()];
		for(int i=0;i<puzzle.length;i++){
		if(template[i]){
			puzzle[i]=HIDDEN_CHAR;
		 }
		else {
			puzzle[i]=word.charAt(i);
		 }
		}
	return puzzle;
	}

	public static boolean checkLegalTemplate(String word, boolean[] template) { // Q - 2
		// Replace this with your code
		if (template==null) {
			return false;
		}
			
		if (template.length!=word.length()){
		return false;
		}
		int checkHide=0;
		int checkNotHide=0;
		char[] puzzle=createPuzzleFromTemplate(word,template);
		for(int i=0;i<puzzle.length;i++){
			 for(int j=0;j<word.length();j++){
				 if(template[i]) {
			          if ((i!=j)&&(word.charAt(i)==puzzle[j])){
				          return false;
				 }
				 }
			          else{
			        	  if ((i!=j)&&(word.charAt(i)==word.charAt(j)&&HIDDEN_CHAR==puzzle[j])){
			        	  return false;
			          }
			 }
		 }
		   if(template[i]){
		      checkNotHide=checkNotHide+1;
		        }
		         else {
		         checkHide=checkHide+1;
		         }
		}
		return ((checkHide>=1)&&(checkNotHide>=1));
	}
	

    /*
     * @pre: 0 < k < word.lenght(), word.length() <= 10
     */
	   public static boolean[][] getAllLegalTemplates(String word, int k){  // Q - 3
		   int num=(int)Math.pow(2,word.length());
		   int rowNum=0;
	        int index=0;
	        for(int i=1;i<num;i++) {
	            int count=0;
	            for(int j=0;j<Integer.toBinaryString(i).length();j++) {
	            	if(Integer.toBinaryString(i).charAt(j)=='1') {
	            		count=count+1;
	            	}
	            }
	            if(count==k) {  
	            boolean[] template=new boolean [word.length()];
                for(int j=0;j<template.length;j++) {
                    template[j]=false;
                }
                for(int j=0;j<Integer.toBinaryString(i).length();j++){
                    if(Integer.toBinaryString(i).charAt(Integer.toBinaryString(i).length()-j-1)=='1') {
                        template[template.length-1-j]=true;
                    }
                }
                if(checkLegalTemplate(word,template)) {
                    	rowNum=rowNum+1;
                    }
	            }
	        }
	        boolean [][] boolArray=new boolean [rowNum][word.length()]; 
	        for(int i=1;i<num;i++) {
	            int count=0;
	            for(int j=0;j<Integer.toBinaryString(i).length();j++) {
	            	if(Integer.toBinaryString(i).charAt(j)=='1') {
	            		count=count+1;
	            	}
	            }
	            if(count==k) {
	                boolean[] template=new boolean [word.length()];
	                for(int j=0;j<template.length;j++) {
	                    template[j]=false;
	                }
	                for(int j=0;j<Integer.toBinaryString(i).length();j++){
	                    if(Integer.toBinaryString(i).charAt(Integer.toBinaryString(i).length()-j-1)=='1') {
	                        template[template.length-1-j]=true;
	                    }
	                }
	                if(checkLegalTemplate(word,template)) {
	                    for(int j=0;j<template.length;j++) {
	                        boolArray[index][j]=template[j];
	                    }
	                    index=index+1;
	                }

	            }
	        }

	        return boolArray;
	    }

	
	/*
	 * @pre: puzzle is a legal puzzle constructed from word, guess is in [a...z]
	 */

    public static int applyGuess(char guess, String word, char[] puzzle) { // Q - 4
        // Replace this with your code
        int ans=0;
        for(int i=0;i<word.length();i++) {
            if(guess==word.charAt(i)&&HIDDEN_CHAR==puzzle[i]) {
                ans=ans+1;
                puzzle[i]=word.charAt(i);
            }

        }
        return ans;
    }
	

	/*
	 * @pre: puzzle is a legal puzzle constructed from word
	 * @pre: puzzle contains at least one hidden character. 
	 * @pre: there are at least 2 letters that don't appear in word, and the user didn't guess
	 */
    public static char[] getHint(String word, char[] puzzle, boolean[] already_guessed) { // Q - 5
        // Replace this with your code
        char []chr=new char[2];
        int firstCheck=0;
        int secondCheck=0;
        int i=0;
        int count=0;
        int j=0;
        while (j<2) {
            i=(int)(Math.random() * 25);
            if(!already_guessed[i]) {
                if(firstCheck==0) {
                    for(int x=0;x<word.length();x++) {
                        if((char)(i+97)==word.charAt(x)&&HIDDEN_CHAR==puzzle[x]&&firstCheck==0) {
                            chr[j]=(char)(i+97);
                            firstCheck=1;
                            j=j+1;
                        }
                    }
                }
                else 
                if(secondCheck==0) {
                    for(int x=0;x<word.length();x++) {
                        if((char)(i+97)!=word.charAt(x)) {
                            count=count+1;
                        }
                    }
                    if(count==word.length()){
                        chr[j]=(char)(i+97);
                        secondCheck=1;
                        j=j+1;
                    }
                    else
                        count =0;
                }
            }
        }
        if((int)(chr[0])>(int)(chr[1])){
            char temp=chr[0];
            chr[0]=chr[1];
            chr[1]=temp;
        }
        return chr;
    }
	

    public static char[] mainTemplateSettings(String word, Scanner inputScanner) { // Q - 6
    	printSettingsMessage();
    	return mainSettings(word,inputScanner);
    }
   private static char[] mainSettings(String word, Scanner inputScanner) {
        // Replace this with your code
       boolean lastCheck = false;
        boolean []boolArr = null;
        boolean[][] arr= null;
        String in="";
        int rand;
        int hidden;
        boolean check = true;
        String [] strArr;
        String str="";
        	printSelectTemplate();
            in=inputScanner.next();
            if(in.equals("1")) {
            	printSelectNumberOfHiddenChars();
                hidden=inputScanner.nextInt();
                arr= getAllLegalTemplates(word,hidden);

                if(arr.length==0) {
                	printWrongTemplateParameters() ;
                	return mainSettings(word,inputScanner) ;
                }
                rand=(int)(Math.random()*(arr.length-1));
                return createPuzzleFromTemplate(word,arr[rand]);
            }
            if(in.equals("2")) {
            	printEnterPuzzleTemplate();
                    str=inputScanner.next();
                    check=true;
                    if(str.length()==0||(str.charAt(0)==','||str.charAt(str.length()-1)==',') ) {
                        check=false;
                    }
                    if((str.length()+1)!=2*word.length() ) {
                        check=false;
                    }
                    for (int i=0;i<str.length();i++) {
                        if(str.charAt(i)!='X'&&str.charAt(i)!=HIDDEN_CHAR&&str.charAt(i)!=',') {
                            check=false;
                        }
                            if(i>0&&((str.charAt(i)==','&&str.charAt(i-1)==',')||(str.charAt(i)!=','&&str.charAt(i-1)!=','))){
                                check=false;
                           }
                    }
                            strArr=str.split(",");
                            boolArr=new boolean [strArr.length] ;
                            for(int j=0;j<boolArr.length;j++) {
                                if(strArr[j].charAt(0)=='X') {
                                    boolArr[j]=false;
                                }
                                else {
                                    boolArr[j]=true;
                              }
                            }
                    

                if(checkLegalTemplate(word, boolArr)&&check) {
                	return createPuzzleFromTemplate(word,boolArr);	
                }
                else{
                    printWrongTemplateParameters();
                    return mainSettings(word,inputScanner) ;
                }
            }
              return  mainSettings(word,inputScanner) ;  	
   }
	
	public static void mainGame(String word, char[] puzzle, Scanner inputScanner){ // Q - 7
		// Replace this with your code
		printGameStageMessage(); 
		int guessCount=3;
		char guess;
		int success=0;
		int checkSuccess=0;
		boolean [] alreadyGuessed=new boolean [26];
		for (int i=0;i<alreadyGuessed.length;i++) {
			alreadyGuessed[i]=false;
		}
		 printPuzzle(puzzle);
		for(int i=0;i<puzzle.length;i++) {		
			if(puzzle[i]==HIDDEN_CHAR) {
				guessCount=guessCount+1;
			}
		}
		printEnterYourGuessMessage();
		guess=inputScanner.next().charAt(0);
		if (guess=='H') {
			printHint(getHint(word,puzzle,alreadyGuessed));
			start(word,puzzle,alreadyGuessed,inputScanner,guessCount);
		}else 
		if ((int)(guess)>=97&&(int)(guess)<=122) {
			if(applyGuess(guess,word,puzzle)!=0) {
					success=success+1;
			}
				for (int j=0;j<puzzle.length;j++) {
				if(puzzle[j]!=HIDDEN_CHAR) {
				checkSuccess=checkSuccess+1;
				}
			}
		alreadyGuessed[(int)(guess)-97]=true;
		if(puzzle.length==checkSuccess) {
			printWinMessage();
		}
		else  if(success==0) {
			printWrongGuess(guessCount-1);
		}
			else
			{
				printCorrectGuess(guessCount-1);
				}
		    start(word,puzzle,alreadyGuessed,inputScanner,guessCount-1);
			}
        else
            start(word,puzzle,alreadyGuessed,inputScanner,guessCount);
		}	
	 
	private static void start(String word,char[]puzzle,boolean [] alreadyGuessed,Scanner inputScanner,int guessCount){
		char guess;
		int success=0;
		int checkSuccess=0;
		 printPuzzle(puzzle);
			printEnterYourGuessMessage();
			guess=inputScanner.next().charAt(0);
		if (guess=='H') {
			printHint(getHint(word,puzzle,alreadyGuessed));
			start(word,puzzle,alreadyGuessed,inputScanner,guessCount);
		}else
		if ((int)(guess)>=97&&(int)(guess)<=122) {
			if(applyGuess(guess,word,puzzle)!=0) {
					success=success+1;
			}
				for (int j=0;j<puzzle.length;j++) {
				if(puzzle[j]!=HIDDEN_CHAR) {
				checkSuccess=checkSuccess+1;
				}
				}
		alreadyGuessed[(int)(guess)-97]=true;
		if(puzzle.length==checkSuccess) {
			printWinMessage();
		}
		else 
			if(success==0&&guessCount>1) {
				printWrongGuess(guessCount-1);
				 start(word,puzzle,alreadyGuessed,inputScanner,guessCount-1);
		   
		}
			else if(guessCount>1)
			{
				printCorrectGuess(guessCount-1);
				 start(word,puzzle,alreadyGuessed,inputScanner,guessCount-1);
			}
			else
				printGameOver();
				
		
		}
        else
            start(word,puzzle,alreadyGuessed,inputScanner,guessCount);
		}		
		
		


/*************************************************************/
/********************* Don't change this ********************/
/*************************************************************/

	public static void main(String[] args) throws Exception { 
		if (args.length < 1){
			throw new Exception("You must specify one argument to this program");
		}
		String wordForPuzzle = args[0].toLowerCase();
		if (wordForPuzzle.length() > 10){
			throw new Exception("The word should not contain more than 10 characters");
		}
		Scanner inputScanner = new Scanner(System.in);
		char[] puzzle = mainTemplateSettings(wordForPuzzle, inputScanner);
		mainGame(wordForPuzzle, puzzle, inputScanner);
		inputScanner.close();
	}


	public static void printSettingsMessage() {
		System.out.println("--- Settings stage ---");
	}

	public static void printEnterWord() {
		System.out.println("Enter word:");
	}
	
	public static void printSelectNumberOfHiddenChars(){
		System.out.println("Enter number of hidden characters:");
	}
	public static void printSelectTemplate() {
		System.out.println("Choose a (1) random or (2) manual template:");
	}
	
	public static void printWrongTemplateParameters() {
		System.out.println("Cannot generate puzzle, try again.");
	}
	
	public static void printEnterPuzzleTemplate() {
		System.out.println("Enter your puzzle template:");
	}


	public static void printPuzzle(char[] puzzle) {
		System.out.println(puzzle);
	}


	public static void printGameStageMessage() {
		System.out.println("--- Game stage ---");
	}

	public static void printEnterYourGuessMessage() {
		System.out.println("Enter your guess:");
	}

	public static void printHint(char[] hist){
		System.out.println(String.format("Here's a hint for you: choose either %s or %s.", hist[0] ,hist[1]));

	}
	public static void printCorrectGuess(int attemptsNum) {
		System.out.println("Correct Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWrongGuess(int attemptsNum) {
		System.out.println("Wrong Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWinMessage() {
		System.out.println("Congratulations! You solved the puzzle!");
	}

	public static void printGameOver() {
		System.out.println("Game over!");
	}

}