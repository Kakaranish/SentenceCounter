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
							
							//System.out.println("Słów w zdaniu nr"+sentence_num+" jest "+words_num);
							
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
		System.out.println("Statystyka dlugosci zdań - (ilość wyrazów, ilość wystąpień zdania o podanej ilości wyrazów): ");
		
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			System.out.println(pair.getKey()+","+pair.getValue());
		}
	}
	
	public static void main(String[] args){
		
		count("ksiazki/tymon-atenczyk.txt");
		count("ksiazki/krol-ryszard.txt");	
		count("ksiazki/makbet.txt");	
	}
}
