package crux;

import java.util.LinkedList;
import java.util.Map;
import java.util.LinkedHashMap;

public class SymbolTable {
    LinkedHashMap<String,Symbol> symbol_table;
    LinkedList<LinkedHashMap<String,Symbol>> scope = new LinkedList<LinkedHashMap<String, Symbol>>();
	
    public SymbolTable()
    {
    	symbol_table = new LinkedHashMap<String, Symbol>();
    	scope.add(symbol_table);
        //throw new RuntimeException("implement this");
    }
    
    public Symbol lookup(String name) throws SymbolNotFoundError
    {
    	for (int i = scope.size()-1; i >= 0; i--)
    	{
    		LinkedHashMap<String,Symbol> table = scope.get(i);
	    	for (String key: table.keySet())
	    	{
	    		if (key.equals(name))
	    		{
	    			return table.get(key);
	    		}
	    		
	    	}
    	}
    	throw new SymbolNotFoundError(name);
    	//return new ErrorSymbol("No such symbol found.");
        //throw new RuntimeException("implement this");
    }
       
    public Symbol insert(String name) throws RedeclarationError
    {
    	Symbol potential = new Symbol(name);
    	for (String key: symbol_table.keySet())
    	{
    		if (key.equals(name))
    		{
    			potential = symbol_table.get(name);
    			throw new RedeclarationError(potential);
    		}
    		
    	}
    	symbol_table.put(name,potential);
    	return potential;
    }
    
    public void new_scope()
    {
    	LinkedHashMap<String,Symbol> new_table = new LinkedHashMap<String,Symbol>();
    	scope.add(new_table);
    	symbol_table = new_table;
    }
    
    public void bad_scope()
    {
    	scope.removeLast();
    	symbol_table = scope.getLast();
    }
    
    public String toString()
    {
    	int depth;
    	depth = scope.indexOf(symbol_table);
        StringBuffer sb = new StringBuffer();
        if (scope.size() > 1/*I have a parent table*/)
        {
        	int x =0;
        	while (x < depth)
        	{
        		String indent = new String();
                for (int i = 0; i < x; i++) {
                    indent += "  ";
                }
	        	LinkedHashMap<String,Symbol> parent;
	        	parent = scope.get(x);
	        	for (String key: parent.keySet())
	        	{
	        		Symbol sk = parent.get(key);
	        		//System.out.println(parent.get(key));
	        		sb.append(indent + sk.toString() + "\n");
	        	}
	        	x++;
        	}
            //sb.append(parent.toString());
        }
        String indent = new String();
        for (int i = 0; i < depth; i++) {
            indent += "  ";
        }
        
        for (String key: symbol_table.keySet()/*Every symbol, s, in this table*/)
        {
        	Symbol s = symbol_table.get(key);
            sb.append(indent + s.toString() + "\n");
        }
        return sb.toString();
    }
}

class SymbolNotFoundError extends Error
{
    private static final long serialVersionUID = 1L;
    private String name;
    
    SymbolNotFoundError(String name)
    {
        this.name = name;
    }
    
    public String name()
    {
        return name;
    }
}

class RedeclarationError extends Error
{
    private static final long serialVersionUID = 1L;

    public RedeclarationError(Symbol sym)
    {
        super("Symbol " + sym + " being redeclared.");
    }
}
