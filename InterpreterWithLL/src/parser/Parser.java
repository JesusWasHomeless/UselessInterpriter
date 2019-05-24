package parser;

//import java.util.ArrayList;
import java.util.*;

import lexer.LexGrammar;
import lexer.Token;

public class Parser {
	public static List<Token> tokens = new LinkedList<>();
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

		if(KustarniyLinkedList()){
			return true;
		}
		else back();
		return false;
	}
//--------------------------------------------------------
//LINKED LIST
private boolean KustarniyLinkedList(){
	if(ListCreate()){
		return true;
	}
	else {back();}

	if(ListAdd()){
		return true;
	}
	else back();
	if(ListRemove()){
		return true;
	}
	else back();
	if(ListSuperRemove()){
		return true;
	}
	else back();
	if(ListGetElement()){
		return true;
	}
	else back();
	if(ListSize()){
		return true;
	}
	else back();
    if(HashSetCreate()){
        return true;
    }
    else {back();}
    if(HashSize()){
        return true;
    }
    else back();
    if(HashAdd()){
        return true;
    }
    else back();
    if(HashRemove()){
        return true;
    }
    else back();
    if(HashContains()){
        return true;
    }
    else back();

    return false;
}
    private boolean KustarniyHashSet(){

        return false;
    }
    private boolean HashSetCreate() {
        boolean SetCreate = false;
        if (NextLexGetter() == LexGrammar.TYPE_SET) {
            if (NextLexGetter() == LexGrammar.VAR) {
                if (NextLexGetter() == LexGrammar.SEMICOLON) {
                    SetCreate = true;
                }
            }
        }
        //}
        return SetCreate;
    }
    private boolean HashSize() {
        boolean ListSize = false;
        if(NextLexGetter() == LexGrammar.TYPE_SET_SIZE) {
            if (NextLexGetter() == LexGrammar.VAR) {
                if (NextLexGetter() == LexGrammar.SEMICOLON) {
                    ListSize = true;
                }
            }
        }
        return ListSize;
    }

    private boolean HashContains() {
        boolean ListElements = false;
        if(NextLexGetter() == LexGrammar.TYPE_SET_CONTAINS){
			if(NextLexGetter() == LexGrammar.VAR){
				if(NextLexGetter() == LexGrammar.COMMA){
					if((NextLexGetter() == LexGrammar.VAR)){
						if(NextLexGetter() == LexGrammar.SEMICOLON) {
							return true;
						}
					}
					else back();
					if((NextLexGetter() == LexGrammar.NUMBER)){
						if(NextLexGetter()==LexGrammar.SEMICOLON){
							return true;
						}
					}
				}
			}
        }
        return ListElements;
    }

    private boolean HashRemove() {
        boolean ListRemove = false;
        if(NextLexGetter() == LexGrammar.TYPE_SET_REMOVE){
			if(NextLexGetter() == LexGrammar.VAR){
				if(NextLexGetter() == LexGrammar.COMMA){
					if((NextLexGetter() == LexGrammar.VAR)){
						if(NextLexGetter() == LexGrammar.SEMICOLON) {
							return true;
						}
					}
					else back();
					if((NextLexGetter() == LexGrammar.NUMBER)){
						if(NextLexGetter()==LexGrammar.SEMICOLON){
							return true;
						}
					}
				}
			}
        }
        return ListRemove;

    }
    private boolean HashAdd() {
        if(NextLexGetter() == LexGrammar.TYPE_SET_ADD){
            if(NextLexGetter() == LexGrammar.VAR){
                if(NextLexGetter() == LexGrammar.COMMA){
                    if((NextLexGetter() == LexGrammar.VAR)){
                        if(NextLexGetter() == LexGrammar.SEMICOLON) {
                            return true;
                        }
                    }
                    else back();
                    if((NextLexGetter() == LexGrammar.NUMBER)){
                        if(NextLexGetter()==LexGrammar.SEMICOLON){
                            return true;
                        }
                    }
                }
            }
        }
        //else back();
        return false;

    }
	private boolean ListCreate() {
		boolean ListCreate = false;
		//if(NextLexGetter() == LexGrammar.TYPE_W) {
			if (NextLexGetter() == LexGrammar.TYPE_LIST) {
				if (NextLexGetter() == LexGrammar.VAR) {
					if (NextLexGetter() == LexGrammar.SEMICOLON) {
						ListCreate = true;
					}
				}
			}
		//}
		return ListCreate;
	}

	private boolean ListSize() {
		boolean ListSize = false;
		if(NextLexGetter() == LexGrammar.TYPE_LIST_SIZE) {
			if (NextLexGetter() == LexGrammar.VAR) {
				if (NextLexGetter() == LexGrammar.SEMICOLON) {
					ListSize = true;
				}
			}
		}
		return ListSize;
	}

	private boolean ListGetElement() {
		boolean ListElements = false;
		if(NextLexGetter() == LexGrammar.TYPE_LIST_ELEMENTS){
			if(NextLexGetter() == LexGrammar.VAR){
				if(NextLexGetter() == LexGrammar.COMMA){
					if(NextLexGetter() == LexGrammar.NUMBER){
						if(NextLexGetter() == LexGrammar.SEMICOLON) {
							ListElements = true;
						}
					}
				}
			}
		}
		return ListElements;
	}

	private boolean ListRemove() {
		boolean ListRemove = false;
		if(NextLexGetter() == LexGrammar.TYPE_LIST_REMOVE){
			if(NextLexGetter() == LexGrammar.VAR){
						if(NextLexGetter() == LexGrammar.SEMICOLON) {
							ListRemove = true;
						}
					}
				}
		return ListRemove;

	}
	private boolean ListAdd() {
		if(NextLexGetter() == LexGrammar.TYPE_LIST_ADD){
			if(NextLexGetter() == LexGrammar.VAR){
				if(NextLexGetter() == LexGrammar.COMMA){
					if((NextLexGetter() == LexGrammar.VAR)){
						if(NextLexGetter() == LexGrammar.SEMICOLON) {
							return true;
						}
					}
					else back();
					 if((NextLexGetter() == LexGrammar.NUMBER)){
						if(NextLexGetter()==LexGrammar.SEMICOLON){
							return true;
						}
					}
				}
			}
		}
		//else back();
	return false;
	}
	private boolean ListSuperRemove() {
		if(NextLexGetter() == LexGrammar.TYPE_LIST_REMOVE_ELEM){
			if(NextLexGetter() == LexGrammar.VAR){
				if(NextLexGetter() == LexGrammar.COMMA){
					if((NextLexGetter() == LexGrammar.VAR)){
						if(NextLexGetter() == LexGrammar.SEMICOLON) {
							return true;
						}
					}
					else back();
					if((NextLexGetter() == LexGrammar.NUMBER)){
						if(NextLexGetter()==LexGrammar.SEMICOLON){
							return true;
						}
					}
				}
			}
		}
		//else back();
		return false;
	}
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
					if(else_expr()){
						if_expr = true;
					}else if(!else_expr()) {if_expr = true;}
				}
			}
			}
		return if_expr;
	}

	private boolean else_expr(){
		boolean else_expr = false;
		if(tokens.size() != pos){
		if(NextLexGetter() == LexGrammar.ELSE_W){
			if(body()) {
				else_expr = true;
			}
		}  else back();

		}//else back();
		return else_expr;
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
			//var = NextLexGetter();
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
			while (OP()){}
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
			while (NextLexGetter() != LexGrammar.END_W){
				back();
				if(expr()){
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
				if(NextLexGetter() == LexGrammar.DIV){
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
				if(NextLexGetter() == LexGrammar.DIV){
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

}
