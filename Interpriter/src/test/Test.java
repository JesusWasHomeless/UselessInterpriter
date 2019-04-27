package test;

//import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
import java.util.LinkedList;
//import java.util.List;
//import java.util.Scanner;
//import java.util.Queue;

import lexer.LexGrammar;
import lexer.Lexer;
import lexer.Token;
import parser.Parser;

public class Test {
/*	public static String FileReader(String phrase) throws Exception{
		FileReader file = new FileReader("Test.txt");
		Scanner scan = new Scanner(file);
		while(scan.hasNextLine()) {
			phrase = scan.nextLine();
		}
		file.close();
		scan.close();
		return phrase;
	}
	*/
	public static void main(String[] args) throws Exception{
		LinkedList <Token> tokens = new LinkedList<>();
		Lexer lexer = new Lexer();
		//String string = null;
		String stroka = new String(Files.readAllBytes(Paths.get("test.txt")));
		tokens = lexer.tokenize(stroka);
		for (Token token : tokens){
			LexGrammar lex = token.getLex();
			String val = token.getVal();
			System.out.println("\n");
			System.out.println("The lexeme: {" + lex + "} stands for: {" + val + "}");
		}
		System.out.println("\n");
		new Parser(tokens);
		System.out.println("\n");
		System.out.println(stroka);
		System.out.println(Parser.Poliz(tokens));
   }
}
