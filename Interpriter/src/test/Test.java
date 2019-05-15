package test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

import lexer.LexGrammar;
import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import poliz.Poliz;
import stackMachine.StackMachine;


public class Test {
	public static void main(String[] args) throws Exception{
		LinkedList <Token> tokens;
        LinkedList <Token> tokens2;
        Lexer lexer = new Lexer();
		//String string = null;
		String stroka = new String(Files.readAllBytes(Paths.get("test.txt")));
		tokens = lexer.tokenize(stroka);//на вход полизу приходят токены, полученные из файла
        tokens2 = tokens;
		for (Token token : tokens){
			LexGrammar lex = token.getLex();
			String val = token.getVal();
			System.out.println("\n");
			System.out.println("The lexeme: {" + lex + "} stands for: {" + val + "}");
		}
		System.out.println("\n");
		Parser parser = new Parser(tokens);
        System.out.println("\n");
		System.out.println(stroka);
		System.out.println(parser.tokens);
        System.out.println("\n");
        StackMachine SM = new StackMachine();
        SM.initialization(tokens2);
        Poliz poliz = new Poliz();
        LinkedList<Token> pol =  poliz.Poliz(tokens);
        SM.interpreter(pol);
    }
