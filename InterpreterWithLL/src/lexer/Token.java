package lexer;

public class Token {
	public LexGrammar type; // lexemes
	public String value;  // value
	
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
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
