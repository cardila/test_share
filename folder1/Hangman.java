import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Hangman {

	private Vector<String> wordList;
	private Vector<String> letterList;
	private Vector<HiddenLetter> hiddenWord;
	private Vector<String> SelectedWords;
	private Boolean canPlay = Boolean.FALSE;
	private int attempts;
	final private int easyLevel = 15;
	final private int mediumLevel = 12;
	final private int hardLevel = 9;
			
	public Hangman()
	{	
		generateVectorWords();
		generateVectorLetters();
		hiddenWord = new Vector<HiddenLetter>();
		SelectedWords = new Vector<String>();
		canPlay = askLevelPlayer();
	}
	
	private boolean askLevelPlayer(){
		
		attempts = 0;
		boolean LevelSelected = Boolean.FALSE;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(!LevelSelected){
			try{
				System.out.println("Welcome to play hangman of South American Countries");
				System.out.println("Enter a number to select the level:");
		        System.out.println("1 - Easy");
		        System.out.println("2 - Medium");
		        System.out.println("3 - Hard");
		        String level = br.readLine();
		        
		        int i = Integer.parseInt(level);
		        LevelSelected = (i==1 || i==2 || i==3) ? Boolean.TRUE : Boolean.FALSE;
		        if (LevelSelected) {		        	
		        	switch (i) {
					case 1:
						System.out.println(String.format("Level Easy: you have %d opportunities to guess", easyLevel));
						attempts = easyLevel;
						break;
					case 2:
						System.out.println(String.format("Level Medium: you have %d opportunities to guess", mediumLevel));
						attempts = mediumLevel;
						break;
					case 3:
						System.out.println(String.format("Level Hard: you have %d opportunities to guess", hardLevel));
						attempts = hardLevel;
						break;
					}
		        }
		        
	        }catch(Exception ex){	            
	        	System.out.println("Invalid imput, try again");
	        }		
		}
		return LevelSelected;
	}
	
	private void generateVectorWords(){
		wordList = new Vector<String>(10, 1);
		wordList.add("colombia");
		wordList.add("peru");
		wordList.add("chile");
		wordList.add("usa");
		wordList.add("paraguay");
		wordList.add("uruguay");
		wordList.add("bolivia");
		wordList.add("ecuador");
		wordList.add("venezuela");
		wordList.add("argentina");
		wordList.add("surinam");
		wordList.add("brasil");
	}
	
	private void generateVectorLetters(){
		letterList = new Vector<String>(27);
		letterList.add("a");
		letterList.add("b");
		letterList.add("c");
		letterList.add("d");
		letterList.add("e");
		letterList.add("f");
		letterList.add("g");
		letterList.add("h");
		letterList.add("i");
		letterList.add("j");
		letterList.add("k");
		letterList.add("l");
		letterList.add("m");
		letterList.add("n");
		letterList.add("ñ");
		letterList.add("o");
		letterList.add("p");
		letterList.add("q");
		letterList.add("r");
		letterList.add("s");
		letterList.add("t");
		letterList.add("u");
		letterList.add("v");
		letterList.add("w");
		letterList.add("x");
		letterList.add("y");
		letterList.add("z");
	}
	
	private void showWordList(){
		System.out.println("Total words: " + (wordList.capacity()-1));
		for(String word: wordList){
			System.out.println(word);
		}
	}
	
	private void showAvailableLetters(){
		System.out.println("select one of these available letters:");
		for(String word: letterList){
			System.out.print(word + " ");
		}
		System.out.println();
	}
	
	private boolean disableLetter(String letter){
		boolean exists = Boolean.FALSE;
		if(!letterList.isEmpty()){
			exists = letterList.remove(letter);
		}
		return exists;
	}
	
	private String getRandomWord(){
		String word = "";
		if (!wordList.isEmpty()){
			Random rand = new Random();
			int randomNum = rand.nextInt(wordList.capacity()-1);
			word = wordList.get(randomNum);			
		}
		return word;
	}
	
	private void play(){
		if(!wordList.isEmpty()){
			//get random word of the list
			String word = getRandomWord();
			//init the hidden word
			initHiddenWord(word);
			System.out.println("The word has " + word.length() + " letters");
			//ask for a letter
			String NewLetter;
			while(canPlay){
				//show the hidden word with mask
				System.out.println(getHiddenWord());
				showAvailableLetters();
				NewLetter = askNewLetter();
				SelectedWords.add(NewLetter);
				if(disableLetter(NewLetter)){
					attempts--;
					convertHiddenWord(SelectedWords);
					System.out.println("Attempts remaining: " + attempts);					
					if(guessedWord()){
						System.out.println("congratulations!!! you Win, the word is " + word);
						canPlay = Boolean.FALSE;
					}
					else if(attempts == 0)
					{
						canPlay = Boolean.FALSE;
						System.out.println("you don't have more attempts to guess, the word is: " + word);
					}
				}
				else
					System.out.println("Try another letter");
			}
		}
	}
	
	private void initHiddenWord(String strWord){
		char[] cArray = strWord.toCharArray();
		for (char c : cArray) {
			hiddenWord.add(new HiddenLetter(String.valueOf(c), Boolean.TRUE));
		}
	}
	
	private String getHiddenWord(){
		String strHiddenWord = "";
		for(HiddenLetter word: hiddenWord){
			if(word.getHidden())
				strHiddenWord += "_ ";
			else
				strHiddenWord += word.getLetter() + " ";
		}
		System.out.println();
		return strHiddenWord;
	}
	
	private void convertHiddenWord(Vector<String> selectedLetter){
		for(String sel_letter: selectedLetter){
			for(HiddenLetter hiddenLetter: hiddenWord){
				if(hiddenLetter.getLetter().equals(sel_letter))
					hiddenLetter.setHidden(Boolean.FALSE);
			}
		}
	}
	
	private boolean guessedWord(){
		boolean guessed = Boolean.TRUE;
		for(HiddenLetter hiddenLetter: hiddenWord){
			guessed = guessed & !hiddenLetter.getHidden();			
		}
		return guessed;
	}
	
	private String askNewLetter(){
		String letter = "";
		try{
			if(canPlay){				
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				letter = br.readLine();				
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		return letter;
	}
	
	public static void main(String args[]){
		Hangman objHangman = new Hangman();
		objHangman.play();
	}
}

class HiddenLetter{
	private String letter;
	private Boolean hidden;
	
	public HiddenLetter(String strLetter, Boolean blnHidden){
		this.letter = strLetter;
		this.hidden = blnHidden;
	}
	
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public Boolean getHidden() {
		return hidden;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}	
}
