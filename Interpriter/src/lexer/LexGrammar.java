package lexer;

import java.util.regex.Pattern;

public enum LexGrammar {
		START_W(Pattern.compile("^START$")),
		END_W(Pattern.compile("^END$")),
		IF_W(Pattern.compile("^IF$")),
		ELSE_W(Pattern.compile("^ELSE$")),
		WHILE_W(Pattern.compile("^WHILE$")),
		FOR_W(Pattern.compile("^FOR$")),
		PRINT_W(Pattern.compile("^PRINT")),
		DIV(Pattern.compile("^:$")),
		SEMICOLON(Pattern.compile("^;$")),
		COMPARE_MORE(Pattern.compile("^>$")),
		COMPARE_LESS(Pattern.compile("^<$")),
		COMPARE_MORE_EQ(Pattern.compile("^>=$")),
		COMPARE_LESS_EQ(Pattern.compile("^<=$")),
		COMPARE_EQUALS(Pattern.compile("^==$")),
		COMPARE_NON_EQUALS(Pattern.compile("^<>$")),
		BRACE_OP(Pattern.compile("^\\{$")),
		BRACE_CL(Pattern.compile("^}$")),
		PARENTHESIS_OP(Pattern.compile("^\\($+")),
		PARENTHESIS_CL(Pattern.compile("^\\)$+")),
		OP_PLUS(Pattern.compile("^\\+$")),
		OP_MINUS(Pattern.compile("^-$")),
		OP_MUL(Pattern.compile("^\\*$")),
		OP_DIV(Pattern.compile("^/$")),
		ASSIGN_OP(Pattern.compile("^=$")),
		VAR(Pattern.compile("^[A-Z,a-z]+$")),
		NUMBER(Pattern.compile("^0|([1-9][0-9]*)$")),
		SPACE(Pattern.compile("^\\s$")),
		GOTO_POS (Pattern.compile("")),
        JUMP(Pattern.compile("^!$")),
        JUMP_F(Pattern.compile("^!$"));
		
		private Pattern pattern;

		LexGrammar(Pattern pattern){	
			this.pattern = pattern;
		}
		
		public Pattern getPattern(){
			return pattern;
		}
		
}
