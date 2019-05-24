package lexer;

//import java.util.ArrayList;
import java.util.LinkedList;
//import java.util.List;
import java.util.regex.Matcher;

public class Lexer {
	private int pos = 0;
	private String acc = "";
	private boolean SuccTrigger = true;
	private LexGrammar lex = null;

	private boolean find(){
		for(LexGrammar lexeme : LexGrammar.values()){
			Matcher m = lexeme.getPattern().matcher(acc);
			if (m.matches()){
				lex = lexeme;
				return true;
			}
			
		}
		return false;
	
	}
	public LinkedList<Token> tokenize (String input){
		LinkedList<Token> tokens = new LinkedList<>();
		if(input.length()!=0){
			while(pos<input.length()){
				acc = acc + input.charAt(pos++);
				boolean find = find();
				if(!find){
					if(!SuccTrigger){
						SuccTrigger = true;
						Token token = new Token(lex,value(acc));
						tokens.add(token);
						acc = "";
					}
					else{
						SuccTrigger = true;
						System.err.println(" Ошибка! Не могу разобрать: '" + acc + "' в позиции:" + pos + "!");
                        System.exit(-1);
					}
				}
					else{
						SuccTrigger = false;
					}
				}
				tokens.add(new Token(lex,value(acc+1)));
			}
			else {
				System.err.println("Ошибка! Пустая строка.");
				System.exit(-1);
			}
       return tokens;
	}
	private String value(String acc) {
		pos--;
		return acc.substring(0, acc.length() - 1);
	}
}
