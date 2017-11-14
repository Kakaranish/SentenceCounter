import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Counter {
	public static void count(String filename) {
		
		HashMap<Integer,Integer> sentenceLength = new HashMap<Integer,Integer>();
		
		File file = new File(filename);
		BufferedReader breader = null;
		try{
			breader = new BufferedReader(new FileReader(file));
		} catch(IOException e){
			System.out.println("Nie udało się odnaleźć podanego pliku!");
			e.printStackTrace();
		}
		
		String line;
		int words_num = 0, sentence_num = 0;
		String endOfSentence = ".!?";
		String endOfWord = " ,-\"\'";
		boolean nextIteration = false;
		boolean prevSpecial = true;
		
		
		try {
			while((line = breader.readLine()) != null){
				for(int i=0;i<line.length();i++){
					
					nextIteration = false;
					
					for(int j=0;j<endOfWord.length();j++){
						if(line.charAt(i)==endOfWord.charAt(j) && !prevSpecial){
							prevSpecial = true;
							words_num++;
							nextIteration = true;
						}
					}
					
					if(nextIteration)
						continue;
					
					for(int j=0;j<endOfSentence.length();j++){
						if(line.charAt(i)==endOfSentence.charAt(j) && !prevSpecial){
							
							words_num++;
							sentence_num++;
							
							if(!sentenceLength.containsKey(words_num))
								sentenceLength.put(words_num, 1);
							else
								sentenceLength.put(words_num, sentenceLength.get(words_num) + 1);
												
							words_num = 0;
							prevSpecial = true;
							nextIteration = true;
						}
					}
					if(nextIteration)
						continue;
					
					prevSpecial = false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Iterator it = sentenceLength.entrySet().iterator();
		System.out.println("Ksiazka: "+filename);
		System.out.println("Calkowitka ilosc wyrazow = "+sentence_num);
		System.out.println("Statystyka dlugosci zdań:");
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			System.out.println(pair.getKey()+" słów "+(Integer)pair.getValue()/new Float(sentence_num) * 100+"%");
		}
	}
	
	public static void main(String[] args){
		System.out.println("Ksiazki Wiliama Shakespearea");
		count("ksiazki/tymon-atenczyk.txt");
		count("ksiazki/krol-ryszard.txt");	
		count("ksiazki/makbet.txt");	
		System.out.println("\n\n\nKsiazki Stanisława Jachowicza");
		count("ksiazki/dwa-pieski.txt");
		count("ksiazki/dwa-plugi.txt");
		System.out.println("\n\n\nKsiazki Ignacego Krasickiego");
		count("ksiazki/satyry-czesc-druga-malzenstwo.txt");
		count("ksiazki/satyry-czesc-druga-medrek.txt");
	}
}
