package crux;

//import ErrorSymbol;
//import NonTerminal;
//import RedeclarationError;
//import Scanner;
//import Statement;
//import Symbol;
//import SymbolNotFoundError;
//import SymbolTable;
//import Token;
//import ast;
import ast.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//import Parser.QuitParseException;
import types.*;

public class Parser {
    public static String studentName = "Lesmond Chow";
    public static String studentID = "17412911";
    public static String uciNetID = "lesmondc";
  
// Typing System ===================================
    
    private Type tryResolveType(String typeStr)
    {
        return Type.getBaseType(typeStr);
    }
        


//Parser ==========================================

public ast.Command parse()
{
    initSymbolTable();
    try {
        return program();
    } catch (QuitParseException q) {
        return new ast.Error(lineNumber(), charPosition(), "Could not complete parsing.");
    }
}
private int parseTreeRecursionDepth = 0;
private StringBuffer parseTreeBuffer = new StringBuffer();

public void enterRule(NonTerminal nonTerminal) {
    String lineData = new String();
    for(int i = 0; i < parseTreeRecursionDepth; i++)
    {
        lineData += "  ";
    }
    lineData += nonTerminal.name();
    //System.out.println("descending " + lineData);
    parseTreeBuffer.append(lineData + "\n");
    parseTreeRecursionDepth++;
}

private void exitRule(NonTerminal nonTerminal)
{
    parseTreeRecursionDepth--;
}

public String parseTreeReport()
{
    return parseTreeBuffer.toString();
}

//Error Reporting ==========================================
private StringBuffer errorBuffer = new StringBuffer();

private String reportSyntaxError(NonTerminal nt)
{
    String message = "SyntaxError(" + lineNumber() + "," + charPosition() + ")[Expected a token from " + nt.name() + " but got " + currentToken.kind + ".]";
    errorBuffer.append(message + "\n");
    return message;
}
 
private String reportSyntaxError(Token.Kind kind)
{
    String message = "SyntaxError(" + lineNumber() + "," + charPosition() + ")[Expected " + kind + " but got " + currentToken.kind + ".]";
    errorBuffer.append(message + "\n");
    return message;
}

public String errorReport()
{
    return errorBuffer.toString();
}

public boolean hasError()
{
    return errorBuffer.length() != 0;
}

private class QuitParseException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    public QuitParseException(String errorMessage) {
        super(errorMessage);
    }
}

private int lineNumber()
{
    return currentToken.lineNumber();
}

private int charPosition()
{
    return currentToken.charPosition();
}
      
//Parser ==========================================
private Scanner scanner;
private Token currentToken;

public Parser(Scanner scanner)
{
	// TODO:
    //throw new RuntimeException("implement this");
	this.scanner = scanner;
	currentToken = scanner.next();
}


// SymbolTable Management ==========================
private SymbolTable symbolTable;

private void initSymbolTable()
{
	symbolTable = new SymbolTable();
    symbolTable.insert("readInt");
    symbolTable.insert("readFloat");
    symbolTable.insert("printBool");
    symbolTable.insert("printInt");
    symbolTable.insert("printFloat");
    symbolTable.insert("println");
}

private void enterScope()
{
    symbolTable.new_scope();
}

private void exitScope()
{
    symbolTable.bad_scope();
}

private Symbol tryResolveSymbol(Token ident)
{
    assert(ident.is(Token.Kind.IDENTIFIER));
    String name = ident.lexeme();
    try {
        return symbolTable.lookup(name);
    } catch (SymbolNotFoundError e) {
        String message = reportResolveSymbolError(name, ident.lineNumber(), ident.charPosition());
        return new ErrorSymbol(message);
    }
}

private String reportResolveSymbolError(String name, int lineNum, int charPos)
{
    String message = "ResolveSymbolError(" + lineNum + "," + charPos + ")[Could not find " + name + ".]";
    errorBuffer.append(message + "\n");
    errorBuffer.append(symbolTable.toString() + "\n");
    return message;
}

private Symbol tryDeclareSymbol(Token ident)
{
    assert(ident.is(Token.Kind.IDENTIFIER));
    String name = ident.lexeme();
    try {
        return symbolTable.insert(name);
    } catch (RedeclarationError re) {
        String message = reportDeclareSymbolError(name, ident.lineNumber(), ident.charPosition());
        return new ErrorSymbol(message);
    }
}

private String reportDeclareSymbolError(String name, int lineNum, int charPos)
{
    String message = "DeclareSymbolError(" + lineNum + "," + charPos + ")[" + name + " already exists.]";
    errorBuffer.append(message + "\n");
    errorBuffer.append(symbolTable.toString() + "\n");
    return message;
}  

//Helper Methods ==========================================
private boolean have(Token.Kind kind)
{
    return currentToken.is(kind);
}

private boolean have(NonTerminal nt)
{
    return nt.firstSet().contains(currentToken.kind);
}

private boolean accept(Token.Kind kind)
{
    if (have(kind)) {
        currentToken = scanner.next();
        return true;
    }
    return false;
}    

private boolean accept(NonTerminal nt)
{
    if (have(nt)) {
        currentToken = scanner.next();
        return true;
    }
    return false;
}

private boolean expect(Token.Kind kind)
{
    if (accept(kind))
        return true;
    String errormessage = reportSyntaxError(kind);
    throw new QuitParseException(errormessage);
    //return false;
}
    
private boolean expect(NonTerminal nt)
{
    if (accept(nt))
        return true;
    String errormessage = reportSyntaxError(nt);
    throw new QuitParseException(errormessage);
    //return false;
}
 
private Token expectRetrieve(Token.Kind kind)
{
    Token tok = currentToken;
    if (accept(kind))
        return tok;
    String errorMessage = reportSyntaxError(kind);
    throw new QuitParseException(errorMessage);
    //return ErrorToken(errorMessage);
}
    
private Token expectRetrieve(NonTerminal nt)
{
    Token tok = currentToken;
    if (accept(nt))
        return tok;
    String errorMessage = reportSyntaxError(nt);
    throw new QuitParseException(errorMessage);
    //return ErrorToken(errorMessage);
}

//Grammar Rules =====================================================

// literal := INTEGER | FLOAT | TRUE | FALSE .
public ast.Expression literal()
{
    ast.Expression expr;
    enterRule(NonTerminal.LITERAL);
    
    Token tok = expectRetrieve(NonTerminal.LITERAL);
    expr = Command.newLiteral(tok);
    
    exitRule(NonTerminal.LITERAL);
    return expr;
}

// designator := IDENTIFIER { "[" expression0 "]" } .
public ast.Expression designator()
{
	// use addressof, index/of, use deference in expression when using the value for designator
	ast.Expression expr;
	ast.Expression result = null;
	int linenum = lineNumber();
	int charpos = charPosition();
	enterRule(NonTerminal.DESIGNATOR);
    Token tok = expectRetrieve(Token.Kind.IDENTIFIER);
    Symbol temp;
    //System.out.println("hi");
 	temp = tryResolveSymbol(tok);
    expr = new ast.AddressOf(linenum,charpos,temp);
    ast.Expression amount;
    if (accept(Token.Kind.OPEN_BRACKET)) 
    {
    	linenum = lineNumber();
		charpos = charPosition();
    	amount = expression0();
    	result = new ast.Index(linenum, charpos, expr, amount);
    	expect(Token.Kind.CLOSE_BRACKET);
    	while (accept(Token.Kind.OPEN_BRACKET))
    	{
    		linenum = lineNumber();
    		charpos = charPosition();
    		amount = expression0();
    		result = new ast.Index(linenum,charpos,result, amount);
    		expect(Token.Kind.CLOSE_BRACKET);
    	}
    }
    else
    {
    	return expr;
    }
    exitRule(NonTerminal.DESIGNATOR);
    return result;
}

// type:= IDENTIFIER.
public Type type()
{
	Type t_type;
	enterRule(NonTerminal.TYPE);
	Token t = expectRetrieve(Token.Kind.IDENTIFIER);
	exitRule(NonTerminal.TYPE);
	t_type = tryResolveType(t.lexeme());
	return t_type;
}

// op0 := ">=" | "<=" | "!=" | "==" | ">" | "<" .
public Token op0()
{
	Token result = null;
	enterRule(NonTerminal.OP0);
	
	if (have(Token.Kind.GREATER_EQUAL))
	{
		result = expectRetrieve(Token.Kind.GREATER_EQUAL);
	}
	else if (have(Token.Kind.LESSER_EQUAL))
	{
		result = expectRetrieve(Token.Kind.LESSER_EQUAL);
	}
	else if (have(Token.Kind.NOT_EQUAL))
	{
		result = expectRetrieve(Token.Kind.NOT_EQUAL);
	}
	else if (have(Token.Kind.EQUAL))
	{
		result = expectRetrieve(Token.Kind.EQUAL);
	}
	else if (have(Token.Kind.GREATER_THAN))
	{
		result = expectRetrieve(Token.Kind.GREATER_THAN);
	}
	else if (have(Token.Kind.LESS_THAN))
	{
		result = expectRetrieve(Token.Kind.LESS_THAN);
	}
	exitRule(NonTerminal.OP0);
	return result;
		
}

// op1 := "+" | "-" | "or" .
public Token op1()
{
	Token result = null;
	enterRule(NonTerminal.OP1);
	if (have(Token.Kind.ADD))
	{
		result = expectRetrieve(Token.Kind.ADD);
	}
	else if (have(Token.Kind.SUB))
	{
		result = expectRetrieve(Token.Kind.SUB);
	}
	else if (have(Token.Kind.OR))
	{
		result = expectRetrieve(Token.Kind.OR);
	}
	exitRule(NonTerminal.OP1);
	return result;
}

// op2 := "*" | "/" | "and" .
public Token op2()
{
	Token result = null;
	
	enterRule(NonTerminal.OP2);
	if (have(Token.Kind.MUL))
	{
		result = expectRetrieve(Token.Kind.MUL);
	}
	else if (have(Token.Kind.DIV))
	{
		result = expectRetrieve(Token.Kind.DIV);
	}
	else if (have(Token.Kind.AND))
	{
		result = expectRetrieve(Token.Kind.AND);
	}
	exitRule(NonTerminal.OP2);
	return result;
}

// expression0 := expression1 [ op0 expression1 ] .
public ast.Expression expression0()
{
	ast.Expression result;
	enterRule(NonTerminal.EXPRESSION0);
	result = expression1();
	if (have(NonTerminal.OP0))
	{
		result = Command.newExpression(result, op0(), expression1());
		op0();
		//expression1();
	}
	exitRule(NonTerminal.EXPRESSION0);
	return result;
}

// expression1 := expression2 { op1  expression2 } .
public ast.Expression expression1()
{
	ast.Expression result;
	enterRule(NonTerminal.EXPRESSION1);
	result = expression2();
	while(have(NonTerminal.OP1))
	{
		result = Command.newExpression(result, op1(), expression2());
		//op1();
		//expression2();
	}
	exitRule(NonTerminal.EXPRESSION1);
	return result;
}

public ast.Expression expression2()
{
	ast.Expression result;
	//System.out.println("expre2 :" + this.parseTreeReport());
	enterRule(NonTerminal.EXPRESSION2);
	result = expression3();
	while(have(NonTerminal.OP2))
	{
		result = Command.newExpression(result, op2(), expression3());
		//op2();
		//expression3();
	}
	exitRule(NonTerminal.EXPRESSION2);
	return result;
}

/* expression3 := "not" expression3
| "(" expression0 ")"
| designator
| call-expression
| literal .*/
public ast.Expression expression3()
{
	ast.Expression result = null;
	int linenum = lineNumber();
	int charpos = charPosition();
	enterRule(NonTerminal.EXPRESSION3);
	if(have(Token.Kind.NOT))
	{
		expect(Token.Kind.NOT);
		ast.Expression not_temp;
		not_temp = expression3();
		result = new ast.LogicalNot(linenum, charpos, not_temp);
	}
	else if (accept(Token.Kind.OPEN_PAREN))
	{
		result = expression0();
		expect(Token.Kind.CLOSE_PAREN);
	}
	else if (have(NonTerminal.DESIGNATOR))
	{
		ast.Expression x;
		x = designator();
		result = new ast.Dereference(linenum, charpos, x);
	}
	else if (have(NonTerminal.CALL_EXPRESSION))
	{
		result = call_expression();
	}
	else if (have(NonTerminal.LITERAL))
	{
		result = literal();
	}
	exitRule(NonTerminal.EXPRESSION3);
	return result;
}

//call-expression := "::" IDENTIFIER "(" expression-list ")" .
public ast.Call call_expression()
{
	ast.Call result;
	int linenum = lineNumber();
	int charpos = charPosition();
	
	enterRule(NonTerminal.CALL_EXPRESSION);
	
	expect(Token.Kind.CALL);
	Token x;
	x = expectRetrieve(Token.Kind.IDENTIFIER);
	Symbol temp = tryResolveSymbol(x);
	symbolTable.symbol_table.put(x.lexeme(),temp);
	expect(Token.Kind.OPEN_PAREN);
	ast.ExpressionList expr_list;
	expr_list = expression_list();
	expect(Token.Kind.CLOSE_PAREN);
	exitRule(NonTerminal.CALL_EXPRESSION);
	
	result = new ast.Call(linenum,charpos,temp,expr_list);
	return result;
}

// expression-list := [ expression0 { "," expression0 } ] .
public ast.ExpressionList expression_list()
{
	ast.ExpressionList result = new ast.ExpressionList(lineNumber(), charPosition());
	ast.Expression val;
	enterRule(NonTerminal.EXPRESSION_LIST);
	if (have(NonTerminal.EXPRESSION0))
	{
		val = expression0();
		result.add(val);
		while(have(Token.Kind.COMMA))
		{
			expect(Token.Kind.COMMA);
			val = expression0();
			result.add(val);
		}
	}
	exitRule(NonTerminal.EXPRESSION_LIST);
	return result;
}

// parameter := IDENTIFIER ":" type .
public Symbol parameter()
{
	Symbol temp = null;
	enterRule(NonTerminal.PARAMETER);
	//expect(Token.Kind.IDENTIFIER);
	Token x;
    x = expectRetrieve(Token.Kind.IDENTIFIER);
    if (x.kind == Token.Kind.IDENTIFIER)
    {
     	temp = tryDeclareSymbol(x);
    }
	expect(Token.Kind.COLON);
	temp.setType(type());
	symbolTable.symbol_table.put(x.lexeme(),temp);
	exitRule(NonTerminal.PARAMETER);
	return temp;
}

// parameter-list := [ parameter { "," parameter } ] .
public List<Symbol> parameter_list()
{
	List<Symbol> result = new ArrayList<Symbol>();
	Symbol element;
	
	enterRule(NonTerminal.PARAMETER_LIST);
	
	enterScope();
	if (have(NonTerminal.PARAMETER))
	{
		element = parameter();
		result.add(element);
		while(have(Token.Kind.COMMA))
		{
			expect(Token.Kind.COMMA);
			element = parameter();
			result.add(element);
		}
	}
	exitRule(NonTerminal.PARAMETER_LIST);
	return result;
}

// variable-declaration := "var" IDENTIFIER ":" type ";"
public ast.VariableDeclaration variable_declaration()
{
	Symbol temp = null;
	ast.VariableDeclaration result = null;
	int linenum = lineNumber();
	int charpos = charPosition();
	enterRule(NonTerminal.VARIABLE_DECLARATION);
	expect(Token.Kind.VAR);
	//expect(Token.Kind.IDENTIFIER);
	Token x;
    x = expectRetrieve(Token.Kind.IDENTIFIER);
    if (x.kind == Token.Kind.IDENTIFIER)
    {
     	temp = tryDeclareSymbol(x);
    	result = new ast.VariableDeclaration(linenum, charpos, temp);

    }
	expect(Token.Kind.COLON);
	temp.setType(type());
	symbolTable.symbol_table.put(x.lexeme(),temp);
	expect(Token.Kind.SEMICOLON);
	exitRule(NonTerminal.VARIABLE_DECLARATION);
	return result;
}

// array-declaration := "array" IDENTIFIER ":" type "[" INTEGER "]" { "[" INTEGER "]" } ";"
public ast.Declaration array_declaration()
{
	ast.Declaration decl = null;
	Symbol temp = null;
	int linenum = lineNumber();
	int charpos = charPosition();
	
	enterRule(NonTerminal.ARRAY_DECLARATION);
	expect(Token.Kind.ARRAY);
	
	Token x;
    x = expectRetrieve(Token.Kind.IDENTIFIER);
    if (x.kind == Token.Kind.IDENTIFIER)
    {
     	temp = tryDeclareSymbol(x);
     	decl = new ast.ArrayDeclaration(linenum, charpos, temp);
    }
	expect(Token.Kind.COLON);
	temp.setType(type());
 	symbolTable.symbol_table.put(x.lexeme(),temp);
	accept(Token.Kind.OPEN_BRACKET);
	expect(Token.Kind.INTEGER);
	expect(Token.Kind.CLOSE_BRACKET);
	while (accept(Token.Kind.OPEN_BRACKET))
	{
		expect(Token.Kind.INTEGER);
		expect(Token.Kind.CLOSE_BRACKET);
	}
	expect(Token.Kind.SEMICOLON);
	exitRule(NonTerminal.ARRAY_DECLARATION);
	return decl;
}

// function-definition := "func" IDENTIFIER "(" parameter-list ")" ":" type statement-block .
public ast.Declaration function_definition()
{
	int linenum = lineNumber();
	int charpos = charPosition();
	Symbol temp = null;
	
	enterRule(NonTerminal.FUNCTION_DEFINITION);
	
	expect(Token.Kind.FUNC);
	Token x;
    x = expectRetrieve(Token.Kind.IDENTIFIER);
    if (x.kind == Token.Kind.IDENTIFIER)
    {
     	temp = tryDeclareSymbol(x);
    }
	accept(Token.Kind.OPEN_PAREN);
	List<Symbol> p_list;
	p_list = parameter_list();
	expect(Token.Kind.CLOSE_PAREN);
	expect(Token.Kind.COLON);
	temp.setType(type());
 	symbolTable.symbol_table.put(x.lexeme(),temp);
	ast.StatementList s_list;
	s_list = statement_block();
	exitRule(NonTerminal.FUNCTION_DEFINITION);
	ast.Declaration result = new ast.FunctionDefinition(linenum, charpos, temp,p_list, s_list);
	return result;
}

// declaration := variable-declaration | array-declaration | function-definition .
public ast.Declaration declaration()
{
	ast.Declaration declr = null;
	enterRule(NonTerminal.DECLARATION);
	if (have(NonTerminal.VARIABLE_DECLARATION))
	{
		declr = variable_declaration();
	}
	else if (have(NonTerminal.ARRAY_DECLARATION))
	{
		declr = array_declaration();
	}
	else if (have(NonTerminal.FUNCTION_DEFINITION))
	{
		declr = function_definition();
	}
	exitRule(NonTerminal.DECLARATION);
	return declr;
}
// declaration-list := { declaration } .
public ast.DeclarationList declaration_list()
{
	ast.DeclarationList result = new ast.DeclarationList(lineNumber(), charPosition());
	enterRule(NonTerminal.DECLARATION_LIST);
	while (have(NonTerminal.DECLARATION))
	{
		result.add(declaration());
	}
	exitRule(NonTerminal.DECLARATION_LIST);
	return result;
}
// assignment-statement := "let" designator "=" expression0 ";"
public ast.Statement assignment_statement()
{
	//System.out.println(this.parseTreeReport());
	ast.Expression deg;
	ast.Expression source;
	ast.Statement result;
	int linenum = lineNumber();
	int charpos = charPosition();
	enterRule(NonTerminal.ASSIGNMENT_STATEMENT);
	
	expect(Token.Kind.LET);
	deg = designator();
	expect(Token.Kind.ASSIGN);
	source = expression0();
	expect(Token.Kind.SEMICOLON);
	
	exitRule(NonTerminal.ASSIGNMENT_STATEMENT);
	result = new ast.Assignment(linenum, charpos, deg, source);
	return result;
}

// call-statement := call-expression ";"
public ast.Statement call_statement()
{
	ast.Call result;
	enterRule(NonTerminal.CALL_STATEMENT);
	
	result = call_expression();
	expect(Token.Kind.SEMICOLON);
	
	exitRule(NonTerminal.CALL_STATEMENT);
	return result;
}

// if-statement := "if" expression0 statement-block [ "else" statement-block ] .
public ast.Statement if_statement()
{
	int linenum = lineNumber();
	int charpos = charPosition();
	ast.Expression expr;
	ast.Statement result;
	ast.StatementList thenblock;
	ast.StatementList elseblock;
	enterRule(NonTerminal.IF_STATEMENT);
	
	expect(Token.Kind.IF);
	enterScope();
	expr = expression0();
	thenblock = statement_block();
	if (have(Token.Kind.ELSE))
	{
		expect(Token.Kind.ELSE);
		enterScope();
		elseblock = statement_block();
	}
	else
	{
    	elseblock = new ast.StatementList(lineNumber(), charPosition());
	}
	exitRule(NonTerminal.IF_STATEMENT);
	result = new ast.IfElseBranch(linenum, charpos, expr, thenblock, elseblock);
	return result;
}

// while-statement := "while" expression0 statement-block .
public ast.Statement while_statement()
{
	ast.Statement result;
	ast.Expression condition;
	ast.StatementList body;
	int linenum = lineNumber();
	int charpos = charPosition();
	
	enterRule(NonTerminal.WHILE_STATEMENT);
	
	expect(Token.Kind.WHILE);
	enterScope();
	condition = expression0();
	body = statement_block();
	exitRule(NonTerminal.WHILE_STATEMENT);
	result = new ast.WhileLoop(linenum, charpos, condition, body);
	return result;
	
}

// return-statement := "return" expression0 ";" .
public ast.Statement return_statement()
{
	ast.Statement result;
	ast.Expression value;
	int linenum = lineNumber();
	int charpos = charPosition();
	
	enterRule(NonTerminal.RETURN_STATEMENT);
	
	expect(Token.Kind.RETURN);
	value = expression0();
	expect(Token.Kind.SEMICOLON);
	exitRule(NonTerminal.RETURN_STATEMENT);
	result = new ast.Return(linenum, charpos, value);
	return result;
}

/* statement := variable-declaration
| call-statement
| assignment-statement
| if-statement
| while-statement
| return-statement .*/
public ast.Statement statement()
{
	ast.Statement result = null;
	enterRule(NonTerminal.STATEMENT);
	
	//System.out.println("state :" + this.parseTreeReport());
	if (have(NonTerminal.VARIABLE_DECLARATION))
	{
		result = variable_declaration();
	}
	else if (have(NonTerminal.CALL_STATEMENT))
	{
		result = call_statement();
	}
	else if (have(NonTerminal.ASSIGNMENT_STATEMENT))
	{
		result = assignment_statement();
	}
	else if (have(NonTerminal.IF_STATEMENT))
	{
		result = if_statement();
	}
	else if (have(NonTerminal.WHILE_STATEMENT))
	{
		result = while_statement();
	}
	else if (have(NonTerminal.RETURN_STATEMENT))
	{
		result = return_statement();
	}
	exitRule(NonTerminal.STATEMENT);
	return result;
}

//statement-list := { statement } .
public ast.StatementList statement_list()
{
	//System.out.println("statelist :" + this.parseTreeReport());
	//int linenum = lineNumber();
	//int charpos = charPosition();
	ast.StatementList result = new ast.StatementList(lineNumber(), charPosition());
	enterRule(NonTerminal.STATEMENT_LIST);
	while (have(NonTerminal.STATEMENT))
	{
		ast.Statement element;
		element = statement();
		result.add(element);
	}
	exitRule(NonTerminal.STATEMENT_LIST);
	return result;
}

//statement-block := "{" statement-list "}" .
public ast.StatementList statement_block()
{
	//System.out.println("stateblock :" + this.parseTreeReport());
	ast.StatementList result;
	enterRule(NonTerminal.STATEMENT_BLOCK);
	accept(Token.Kind.OPEN_BRACE);
	result = statement_list();
	expect(Token.Kind.CLOSE_BRACE);
	exitScope();
	exitRule(NonTerminal.STATEMENT_BLOCK);
	return result;
}


// program := declaration-list EOF .
public ast.DeclarationList program()
{
	ast.DeclarationList end;
	enterRule(NonTerminal.PROGRAM);
	end = declaration_list();
	expect(Token.Kind.EOF);
	exitRule(NonTerminal.PROGRAM);
	return end;
    //throw new RuntimeException("add code to each grammar rule, to build as ast.");
}

}