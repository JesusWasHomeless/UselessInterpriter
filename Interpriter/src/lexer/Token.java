package lexer;

public class Token {
	public LexGrammar type;
	public String value;
	
	public Token(LexGrammar type, String value){
		this.type = type;
		this.value = value;
	}
	public LexGrammar getLex(){
		return type;
	}
	public String getVal(){
		return value;
	}
	
	public String toString(){
		return value.toString();
	}
}
