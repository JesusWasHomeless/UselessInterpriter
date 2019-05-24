package test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;

import lexer.LexGrammar;
import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import poliz.Poliz;
import stackMachine.StackMachine;
import KustarniyLinkedList.KLL;


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
        //KLL a = new KLL();
      //  a.add(1);
       // a.add(2);
       // a.add(100);
	//	System.out.println(a.getSize());
		//System.out.println(a.getElement(2));
		//System.out.println(a.remove());
		//System.out.println(a.getSize());
		StackMachine SM = new StackMachine();
        SM.initialization(tokens2);
        Poliz poliz = new Poliz();
       // poliz.Poliz(tokens);
	//	System.out.println(poliz.Poliz(tokens));
		LinkedList<Token> pol =  poliz.Poliz(tokens);
		System.out.println("\n");
		//System.out.println(pol);
		SM.interpreter(pol);


	}
}

