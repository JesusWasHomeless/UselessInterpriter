package parser;

//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import lexer.LexGrammar;
import lexer.Token;

public class Parser {
	
	private static List<Token> tokens = new LinkedList<>();
	private int pos = 0;
	
	public Parser(List<Token> tokens){
		for(Token token : tokens){
			if(token.getLex()!= LexGrammar.SPACE){
				Parser.tokens.add(token);
			}
		}
		
		if(lang()){
			System.out.println("Parser skushal tvoi kod!");
		}
		else{
			System.err.println("Parser vyplyunul tvoi kod! Check syntax!");
			System.exit(-1);
			}
		
 	}

//LANG DESCRIPTION----------------------------------------	IF EXPR THEN OK
	private boolean lang(){
		while(pos < Parser.tokens.size()){
			if(!expr()){
				return false;
			}	
		}
		return true;
	}
//--------------------------------------------------------
	 
//EXPR DESCRIPTION---------------------------------------- IF ONE OF NEXT EXPRS THEN OK ELSE SEARCH AGAIN USING BACK()	
	private boolean expr() {
        if (assignment()){
        	return true;
        }
        else
        	back();
        
       if(if_expr()){
    	   return true;           
        }
        else
        	back();
       
    	if(while_cycle()){
    		return true;
    	}
        else
        	back();
        
        if(for_cycle()){
    		return true;
    	} 
        else
        	back();
        return false;
    }
//--------------------------------------------------------	
	
	
//ASSIGNMENT---------------------------------------------- IF ASSIGNMENT IS SUCCESSFUL AND THERE'S A BORDER OF THAT METHOD (;) THEN OK
	private boolean assignment() {
        boolean assignment = false;

        if (assign_op()) {
            if (NextLexGetter() == LexGrammar.SEMICOLON) {
                assignment = true;
            }
            else back();
        }
        return assignment;
    }
//--------------------------------------------------------
	
//IF EXPR-------------------------------------------------	IF CODE LOOKS LIKE IF(a==b) START a = a + b; END  THEN OK
	private boolean if_expr(){
		boolean if_expr = false;
		if(NextLexGetter() == LexGrammar.IF_W){
			if(log_expr()){
				if(body()){
					if_expr = true;
				}
			}
			else back();
		}
		//else back();
		return if_expr;
	}
//--------------------------------------------------------

//WHILE--------------------------------------------------- THE SAME STORY WITH WHILE CYCLE
	private boolean while_cycle(){
			boolean while_cycle = false;
			if(NextLexGetter()==LexGrammar.WHILE_W){
				if(log_expr()){
					if(body()){
						while_cycle = true;
						
					}
				}
			}
			return while_cycle;	
	}
		
//FOR-----------------------------------------------------	IF CODE LOOKS LIKE FOR(a=4; b<10; a = a + 1) START a = 22*10; END THEN OK
		private boolean for_cycle(){
			boolean for_cycle = false;
			if(NextLexGetter()==LexGrammar.FOR_W){
				if(for_body()){
					if(body()){
						for_cycle = true;
					}
				}
			}
			return for_cycle;
		}
//--------------------------------------------------------

		
//ASSIGNMENT DESCRIPTION================================== THAT'S HOW THE ASSIGNMENT WORKS

//ASSIGN_OP-----------------------------------------------		
    private boolean assign_op() {
        boolean assign_op = false;

        if (NextLexGetter() == LexGrammar.VAR) {
            if (NextLexGetter() == LexGrammar.ASSIGN_OP) {
                if (stmt()) {
                    assign_op = true;
                }
            }
        }
        return assign_op;
    }

//--------------------------------------------------------

//STMT----------------------------------------------------  
    private boolean stmt() {
        boolean stmt = false;

            if(val()) {
            	while(OP()){	
            	}
            	stmt = true;
            }
           
            
        return stmt;
    }
//--------------------------------------------------------    

//VAL-----------------------------------------------------
    private boolean val() {
        boolean val = false;

        if (NextLexGetter() == LexGrammar.VAR) {
            val = true;
        } 
        
        else back();
        
        if (NextLexGetter() == LexGrammar.NUMBER) {
            val = true;
        }
        
        else back();
        
        if (parenthesstmt()){
       	val = true;
        }
        else back();
        
        return val;
    }
//--------------------------------------------------------
    
//OP(+-*/)------------------------------------------------    
    private boolean OP() {
        boolean OP = false;
        
        		if (NextLexGetter() == LexGrammar.OP_PLUS) {
        		//	stack.push("+");
        			if (val()) {
        				OP = true;
        			}
        			else back();
        		}
        		else back();
        		
        		if (NextLexGetter() == LexGrammar.OP_MINUS) {
        	//		stack.push("-");
        			if (val()) {
        				OP = true;
        			}
        			else back();
        		}
        		else back();
        		
        		if (NextLexGetter() == LexGrammar.OP_MUL) {
        		//	stack.push("*");
        			if (val()) {
        				OP = true;
        			}
        			else back();
        		}
        		else back();
        		
        		if (NextLexGetter() == LexGrammar.OP_DIV) {
        		//	stack.push("/");
        			if (val()) {
        				OP = true;
        			}
        			else back();
        		}
        		else back();
        return OP;
    }
//--------------------------------------------------------

//PARENTHESIS(INFINITE MODULE)----------------------------   
    private boolean parenthesstmt(){
    	boolean parenthesstmt = false;
    	if(NextLexGetter() == LexGrammar.PARENTHESIS_OP){
    		if(stmt()){
    			if(NextLexGetter() == LexGrammar.PARENTHESIS_CL){
    				parenthesstmt = true;
    			}
    		}
    	}
    	return parenthesstmt;
    }
//--------------------------------------------------------

//========================================================
      

    
//IF MODULE=============================================== THAT'S FOR THE IF STMT

//LOG_EXPR------------------------------------------------
	private boolean log_expr(){
		boolean log_expr = false;
		if(NextLexGetter() == LexGrammar.PARENTHESIS_OP){
			if(NextLexGetter() == LexGrammar.VAR){
				if(LOG_OP()){
					if(NextLexGetter() == LexGrammar.PARENTHESIS_CL){
						log_expr = true;
					}	
				}
			}
		}
		return log_expr;
	}
//--------------------------------------------------------	

//LOG_OP--------------------------------------------------
	private boolean LOG_OP(){
		boolean LOG_OP = false;
		if(NextLexGetter() == LexGrammar.COMPARE_EQUALS){
			if(val()){
				LOG_OP = true;
			}
		}
		else back();
		
		if(NextLexGetter() == LexGrammar.COMPARE_MORE){
			if(val()){
				LOG_OP = true;
			}
		}
		else back();
		
		if(NextLexGetter() == LexGrammar.COMPARE_LESS){
			if(val()){
				LOG_OP = true;	
			}
		}
		else back();
		
		if(NextLexGetter() == LexGrammar.COMPARE_LESS_EQ){
			if(val()){
				LOG_OP = true;
			}
		}
		else back();
		
		if(NextLexGetter() == LexGrammar.COMPARE_MORE_EQ){
			if(val()){
				LOG_OP = true;
			}
		}
		else back();

		if(NextLexGetter() == LexGrammar.COMPARE_NON_EQUALS){
			if(val()){
				LOG_OP = true;
			}
		}	
		else back();
		
		
		return LOG_OP;
	}
//--------------------------------------------------------	
	
//BODY----------------------------------------------------
	
	private boolean body(){
		boolean body = false;
			if(NextLexGetter() == LexGrammar.START_W){
				if(expr()){
					if(NextLexGetter() == LexGrammar.END_W){
						body = true;
					}
				}
			}
		return body;
	}
//--------------------------------------------------------

//========================================================


	
//FOR MODULE============================================== THAT'S FOR THE FOR CYCLE

//LOG EXPR FOR--------------------------------------------
	private boolean log_expr_for(){
		boolean log_expr_for = false;
		if(NextLexGetter() == LexGrammar.VAR){
			if(LOG_OP()){
				if(NextLexGetter() == LexGrammar.SEMICOLON){
					log_expr_for = true;
				}	
			}		
		}
		return log_expr_for;	
	}
//---------------------------------------------------------
	
//FOR BODY-------------------------------------------------
	private boolean for_body(){
		boolean for_body = false;
		if(NextLexGetter() == LexGrammar.PARENTHESIS_OP){
			if(assign_op()){
				if(NextLexGetter() == LexGrammar.SEMICOLON){
					if(log_expr_for()){
						if (assign_op()){
							if(NextLexGetter() == LexGrammar.PARENTHESIS_CL){
								for_body = true;
							}
						}
					}
				}
			}
		}
		return for_body;
	}
//--------------------------------------------------------
	
//========================================================
	

	
//POS DECREMENTOR AND TOKEN_POS RETURN==================== IT LOOKS AT THE CODE AND IF THE TOKEN MATCHES WITH THE LEXEME THEN IT IT'S OK

//BACK---------------------------------------------------- WE HAVE TO GO BACK ON 1 POS IF DIDN'T FIND THE  
	private void back(){
        pos--;
    }
//-------------------------------------------------------- NEEDED THING

//NEXTLEXGETTER-------------------------------------------
	private LexGrammar NextLexGetter(){
        try {
            return tokens.get(pos++).getLex();

        } catch (IndexOutOfBoundsException ex){
        	System.err.println("Error: Lexeme \"" + LexGrammar.SEMICOLON + "\" expected");
            System.exit(-1);
        }
		return null;
    }
//--------------------------------------------------------


//========================================================
	
//REVERSE POLISH NOTATION---------------------------------
//--------------------------------------------------------
private static LinkedList<Token> poliz = new LinkedList<>();

    public static LinkedList<Token> Poliz(Queue<Token> input) {
        while (!input.isEmpty()) {
            Token token = input.peek();
			if (token.type != LexGrammar.WHILE_W){
                PolizExpr(input);
            }
         
            else if(token.type == LexGrammar.WHILE_W){
            	PolizWhile(input);
            }
			if(token.type != LexGrammar.IF_W){
				PolizExpr(input);
			}
			else if(token.type == LexGrammar.IF_W){
				PolizIf(input);
			}
			if(token.type != LexGrammar.FOR_W){
				PolizExpr(input);
			}
			else if(token.type == LexGrammar.FOR_W){
				PolizFor(input);
			}
            }
        int i = -1;
        for(Token t: poliz){
        	i++;
        	System.out.println(i+ " " + t);
        }
        System.out.println("Your poliz is:");
        return poliz;
    }

    private static void PolizFor(Queue<Token> input) {
        Queue<Token> boolExpr = new LinkedList<>();
        input.poll();
        Token token = input.poll();
        int index = poliz.size();
        while (token.type != LexGrammar.START_W) {
            boolExpr.add(token);
            token = input.poll();
        }
        PolizExpr(boolExpr);
        poliz.add(new Token(LexGrammar.GOTO_POS, Integer.toString(p2(poliz.size()-1, input))));
        poliz.add(new Token(LexGrammar.GOTO, "!F"));

        Queue<Token> expr = new LinkedList<>();
        token = input.poll();
        while (token.type != LexGrammar.END_W) {
            if (token.type == LexGrammar.FOR_W) {
                PolizExpr(expr);
                PolizFor(input);
            }
            if (token.type != LexGrammar.FOR_W)
                expr.add(token);
            token = input.poll();
        }
        PolizExpr(expr);
        poliz.add(new Token(LexGrammar.GOTO_POS , Integer.toString(index)));
        poliz.add(new Token(LexGrammar.GOTO, "!"));

    }
    
    private static void PolizWhile(Queue<Token> input) {
        Queue<Token> boolExpr = new LinkedList<>();
        input.poll();
        Token token = input.poll();
        int index = poliz.size();
        while (token.type != LexGrammar.START_W) {
            boolExpr.add(token);
            token = input.poll();
        }

        PolizExpr(boolExpr);
        poliz.add(new Token(LexGrammar.GOTO_POS, Integer.toString(p(poliz.size(), input))));
        poliz.add(new Token(LexGrammar.GOTO, "!F"));

        Queue<Token> expr = new LinkedList<>();
        token = input.poll();
        while (token.type != LexGrammar.END_W) {
            if (token.type == LexGrammar.WHILE_W) {
                PolizExpr(expr);
                PolizWhile(input);
            }
            if (token.type != LexGrammar.WHILE_W)
                expr.add(token);
            token = input.poll();
        }
        PolizExpr(expr);

        poliz.add(new Token(LexGrammar.GOTO_POS, Integer.toString(index)));
        poliz.add(new Token(LexGrammar.GOTO, "!"));
    }
    private static void PolizIf(Queue<Token> input) {
        Queue<Token> boolExpr = new LinkedList<>();
        input.poll();
        Token token = input.poll();
        //int index = poliz.size();
        while (token.type != LexGrammar.START_W) {
            boolExpr.add(token);
            token = input.poll();
        }

        PolizExpr(boolExpr);
        poliz.add(new Token(LexGrammar.GOTO_POS, Integer.toString(p1(poliz.size()-2, input))));
        poliz.add(new Token(LexGrammar.GOTO, "!F"));

        Queue<Token> expr = new LinkedList<>();
        token = input.poll();
        while (token.type != LexGrammar.END_W) {
            if (token.type == LexGrammar.IF_W) {
                PolizExpr(expr);
                PolizIf(input);
            }
            if (token.type != LexGrammar.IF_W)
                expr.add(token);
            token = input.poll();
        }
        PolizExpr(expr);
    }
    
    private static void PolizExpr(Queue<Token> input) {
        Stack<Token> stack = new Stack<>();

        while (!input.isEmpty()) {
            Token token = input.peek();

            if ((token.type == LexGrammar.WHILE_W)||(token.type == LexGrammar.IF_W)||(token.type == LexGrammar.FOR_W)) {
                break;
            }

            token = input.poll();

            if (token.type == LexGrammar.VAR || token.type == LexGrammar.NUMBER) {
                poliz.add(token);
            }

            if (token.type == LexGrammar.OP_MUL ||
            	token.type == LexGrammar.OP_DIV ||
            	token.type == LexGrammar.OP_PLUS ||
            	token.type == LexGrammar.OP_MINUS ||
            	token.type == LexGrammar.COMPARE_EQUALS ||
            	token.type == LexGrammar.COMPARE_MORE ||
            	token.type == LexGrammar.COMPARE_MORE_EQ ||
            	token.type == LexGrammar.COMPARE_LESS ||
            	token.type == LexGrammar.COMPARE_LESS_EQ ||
            	token.type == LexGrammar.COMPARE_NON_EQUALS ||
            	token.type == LexGrammar.ASSIGN_OP){ 
                if (!stack.empty()) {
                   while (getPriority(token.value) < getPriority(stack.peek().value)) {
                        poliz.add(stack.pop());
                    }
                }
                stack.push(token);
                System.out.println(stack);
            }

            if (token.type == LexGrammar.PARENTHESIS_OP) {
                stack.push(token);
            }

            if (token.type == LexGrammar.PARENTHESIS_CL) {
                if (!stack.empty()) {
                    while (!stack.empty() && stack.peek().type != LexGrammar.PARENTHESIS_OP) {
                        poliz.add(stack.pop());
                    }
                    if (!stack.empty() && stack.peek().type == LexGrammar.PARENTHESIS_OP) {
                        stack.pop();
                    }
                }
            }

            if (token.type == LexGrammar.SEMICOLON) {
                if (!stack.empty()) {
                    poliz.add(stack.pop());
                }
               //  poliz.add(new Token(LexGrammar.SEMICOLON, "EOF"));
            }
        }
        while (!stack.empty()) {
            poliz.add(stack.pop());
        }
    }
    private static int p(int size, Queue<Token> tokens) {
        int p = size;
        int i = 1;

        Queue<Token> tokens1 = new LinkedList<>(tokens);
        Token token1 = tokens1.poll();

        while (i > 0){
            if (token1.type == LexGrammar.WHILE_W) {
                i++;
                p--;
            }
            //p++;
            
            if (token1.type == LexGrammar.SEMICOLON) {
                i--;
            }
            token1 = tokens1.poll();
            if (token1.type != LexGrammar.PARENTHESIS_OP) {
                p++;
                
            }
        }
        //p+=3;
        return p;
    }
    
    private static int p1(int size1, Queue<Token> tokens) {
        int p1 = size1;
        int i = 1;

        Queue<Token> tokens1 = new LinkedList<>(tokens);
        Token token1 = tokens1.poll();

        while (i > 0){
        	if (token1.type == LexGrammar.IF_W){ 	
            	i++;
            	p1--;
            }
            
            if (token1.type == LexGrammar.SEMICOLON) {
                i--;
            }
            token1 = tokens1.poll();
            if (token1.type != LexGrammar.PARENTHESIS_OP) {
                p1++;
                
            }
        }
        //p+=3;
        return p1;
    }
    private static int p2(int size, Queue<Token> tokens) {
        int p2 = size;
        int i = 1;

        Queue<Token> tokens1 = new LinkedList<>(tokens);
        Token token1 = tokens1.peek();

        while (i > 0){
        	if (token1.type == LexGrammar.FOR_W){ 	
            	i++;
            	p2--;
            }
            
            if (token1.type == LexGrammar.SEMICOLON) {
            	i--;
            }
            token1 = tokens1.poll();
            if (token1.type != LexGrammar.PARENTHESIS_OP) {
                p2++;
                
            }
        }
        //p2+=3;
        return p2;
    }

    private static int getPriority(String bin_op) {
         if (bin_op.equals("*") || bin_op.equals("/"))
            return 3;
        else if (bin_op.equals("+") || bin_op.equals("-"))
            return 2;
        else if (bin_op.equals(">") || bin_op.equals(">=") || bin_op.equals("<") || bin_op.equals("<=") || bin_op.equals("==") || bin_op.equals("<>"))
            return 1;
        else
            return 0;
    }
}
