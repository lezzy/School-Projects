package types;

import java.util.HashMap;

import crux.Symbol;
import ast.*;
import ast.Comparison.Operation;

public class TypeChecker implements CommandVisitor {
    
    private HashMap<Command, Type> typeMap;
    private StringBuffer errorBuffer;
    private String func_n;
    private Type func_t = new ErrorType("");

    /* Useful error strings:
     *
     * "Function " + func.name() + " has a void argument in position " + pos + "."
     * "Function " + func.name() + " has an error in argument in position " + pos + ": " + error.getMessage()
     *
     * "Function main has invalid signature."
     *
     * "Not all paths in function " + currentFunctionName + " have a return."
     *
     * "IfElseBranch requires bool condition not " + condType + "."
     * "WhileLoop requires bool condition not " + condType + "."
     *
     * "Function " + currentFunctionName + " returns " + currentReturnType + " not " + retType + "."
     *
     * "Variable " + varName + " has invalid type " + varType + "."
     * "Array " + arrayName + " has invalid base type " + baseType + "."
     */

    public TypeChecker()
    {
        typeMap = new HashMap<Command, Type>();
        errorBuffer = new StringBuffer();
    }

    private void reportError(int lineNum, int charPos, String message)
    {
        errorBuffer.append("TypeError(" + lineNum + "," + charPos + ")");
        errorBuffer.append("[" + message + "]" + "\n");
    }

    private void put(Command node, Type type)
    {
        if (type instanceof ErrorType) {
            reportError(node.lineNumber(), node.charPosition(), ((ErrorType)type).getMessage());
        }
        typeMap.put(node, type);
    }
    
    public Type getType(Object node)
    {
        return typeMap.get(node);
    }
    
    public boolean check(Command ast)
    {
        ast.accept(this);
        return !hasError();
    }
    
    public boolean hasError()
    {
        return errorBuffer.length() != 0;
    }
    
    public String errorReport()
    {
        return errorBuffer.toString();
    }
    
    // ############### THE START ##################

    @Override
    public void visit(ExpressionList node) {
    	for (Expression expr : node)
    	{
    		expr.accept(this);
    	}//throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(DeclarationList node) {
    	for (Declaration decl : node)
    	{
    		decl.accept(this);
    	}//throw new RuntimeException("Implement this");
    }
    @Override
    public void visit(StatementList node) {
    	for (Statement stat : node)
    	{
    		stat.accept(this);
    	}//throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(AddressOf node) {
    	Symbol temp = node.symbol();
    	Type t = temp.type();
    	put(node, t);
        //throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(LiteralBool node) {
    	put(node, new BoolType());
        //throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(LiteralFloat node) {
    	put(node, new FloatType());
        //throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(LiteralInt node) {
    	put(node, new IntType());
        //throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(VariableDeclaration node) {
    	Symbol temp = node.symbol();
    	Type var_t = temp.type();
    	if (var_t instanceof VoidType)
    	{
    		reportError(node.lineNumber(), node.charPosition(),
    				"Variable " + temp.name() + " has invalid type " + var_t + ".");
    	}
    	put(node, getType(node));
        //throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(ArrayDeclaration node) {
    	Symbol temp = node.symbol();
    	Type arry_t = temp.type();
    	if (arry_t instanceof VoidType)
    	{
    		reportError(node.lineNumber(), node.charPosition(),
    				"Array " + temp.name() + " has invalid base type " + arry_t + ".");
    	}
    	put(node, getType(node));
        //throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(FunctionDefinition node) {
    	Symbol temp = node.symbol();
    	int position = 0;
    	func_t = temp.type();
    	func_n = temp.name();
    	
    	if (func_t == null)
    	{
    		func_t = new VoidType();
    	}
    	if (func_n.equals("main") && !(func_t instanceof VoidType))
    	{
    		reportError(node.lineNumber(), node.charPosition(),"Function main has invalid signature.");
    	}
    	for (Symbol sym: node.arguments())
    	{
    		if (sym.type() instanceof VoidType)
    		{
    			reportError(node.lineNumber(), node.charPosition(), 
    					"Function " + func_n + " has a void argument in position " + position + ".");
    		}
    		else if (sym.type() instanceof ErrorType)
    		{
    			reportError(node.lineNumber(), node.charPosition(), 
    					"Function " + func_n + 
    					" has an error in argument in position " + position + ": " + "Unknown type: error");
    		}
    		position++;
    	}
    	for (Statement stat: node.body())
    	{
    		stat.accept(this);
    	}
    	put(node, func_t);
        //throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(Comparison node) {
    	Expression left_node = node.leftSide();
    	Expression right_node = node.rightSide();
    	left_node.accept(this);
    	right_node.accept(this);
    	Type r = getType(right_node);
    	Type l = getType(left_node);
    	put(node, l.compare(r));
        //throw new RuntimeException("Implement this");
    }
    
    @Override
    public void visit(Addition node) {
    	Expression left_node = node.leftSide();
    	Expression right_node = node.rightSide();
    	left_node.accept(this);
    	right_node.accept(this);
    	Type r = getType(right_node);
    	Type l = getType(left_node);
    	put(node, l.add(r));
        //throw new RuntimeException("Implement this");
    }
    
    @Override
    public void visit(Subtraction node) {
    	Expression left_node = node.leftSide();
    	Expression right_node = node.rightSide();
    	left_node.accept(this);
    	right_node.accept(this);
    	Type r = getType(right_node);
    	Type l = getType(left_node);
    	put(node, l.sub(r));
        //throw new RuntimeException("Implement this");
    }
    
    @Override
    public void visit(Multiplication node) {
    	Expression left_node = node.leftSide();
    	Expression right_node = node.rightSide();
    	left_node.accept(this);
    	right_node.accept(this);
    	Type r = getType(right_node);
    	Type l = getType(left_node);
    	put(node, l.mul(r));
        //throw new RuntimeException("Implement this");
    }
    
    @Override
    public void visit(Division node) {
    	Expression left_node = node.leftSide();
    	Expression right_node = node.rightSide();
    	left_node.accept(this);
    	right_node.accept(this);
    	Type r = getType(right_node);
    	Type l = getType(left_node);
    	put(node, l.div(r));
    }
    
    @Override
    public void visit(LogicalAnd node) {
    	Expression left_node = node.leftSide();
    	Expression right_node = node.rightSide();
    	left_node.accept(this);
    	right_node.accept(this);
    	Type r = getType(right_node);
    	Type l = getType(left_node);
    	put(node, l.and(r));
        //throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(LogicalOr node) {
    	Expression left_node = node.leftSide();
    	Expression right_node = node.rightSide();
    	left_node.accept(this);
    	right_node.accept(this);
    	Type r = getType(right_node);
    	Type l = getType(left_node);
    	put(node, l.or(r));
        //throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(LogicalNot node) {
    	Expression not_node = node.expression();
    	not_node.accept(this);
    	Type n = getType(not_node);
    	put(node,n.not());
        //throw new RuntimeException("Implement this");
    }
    
    @Override
    public void visit(Dereference node) {
    	Expression deref = node.expression();
    	deref.accept(this);
    	Type n = getType(node);
    	put(node,getType(deref));
        //throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(Index node) {
    	Expression base = node.base();
    	Expression amount = node.amount();
    	base.accept(this);
    	amount.accept(this);
    	Type b = getType(base);
    	Type a = getType(amount);
    	put(node, b.index(a));
        //throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(Assignment node) {
    	Expression dest = node.destination();
    	Expression source = node.source();
    	dest.accept(this);
    	source.accept(this);
    	Type d = getType(dest);
    	Type s = getType(source);
    	put(node, d.assign(s));
        //throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(Call node) {
    	TypeList arg_list = new TypeList();
    	for(Expression arg: node.arguments()){
    		arg_list.add(getType(arg));
    	}
        //throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(IfElseBranch node) {
    	Expression cond = node.condition();
    	cond.accept(this);
    	if (!(getType(cond) instanceof BoolType))
    	{
    		reportError(node.lineNumber(), node.charPosition(),"IfElseBranch requires bool condition not " + getType(cond) + ".");
    	}
    	for(Statement stat: node.thenBlock())
    	{
    		stat.accept(this);
    	}
    	for(Statement stat: node.elseBlock())
    	{
    		stat.accept(this);
    	}
        //throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(WhileLoop node) {
    	Expression cond = node.condition();
    	cond.accept(this);
    	if (!(getType(cond) instanceof BoolType))
    	{
    		reportError(node.lineNumber(), node.charPosition(),"WhileLoop requires bool condition not " + getType(cond) + ".");
    	}
    	else
    	{
    		for (Statement stat: node.body())
    		{
    			stat.accept(this);
    		}
    	}
        //throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(Return node) {
    	Expression args = node.argument();
    	args.accept(this);
    	try
    	{
    		if(!(getType(args).equivalent(func_t)))
    		{
    			reportError(node.lineNumber(), node.charPosition(), 
    					"Function " + func_n + " returns " + func_t + " not " + getType(args)  + "." );
    		}
    		if(func_n.contains("missing"))
    		{
    			reportError(node.lineNumber(), node.charPosition(), 
    					"Not all paths in function " + func_n + " have a return.");
    		}
    	}
    	catch(Exception e)
    	{
    		
    	}
        //throw new RuntimeException("Implement this");
    }

    @Override
    public void visit(ast.Error node) {
    	
        put(node, new ErrorType(node.message()));
    }
}
