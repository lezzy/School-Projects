package types;

public class ArrayType extends Type {
    
    private Type base;
    private int extent;
    
    public ArrayType(int extent, Type base)
    {
        //throw new RuntimeException("implement operators");
        this.extent = extent;
        this.base = base;
    }
    
    public int extent()
    {
        return extent;
    }
    
    public Type base()
    {
        return base;
    }
    
    @Override
    public String toString()
    {
        return "array[" + extent + "," + base + "]";
    }
    
    public Type index(Type that){
    	if ((that instanceof IntType))
    	{
            return base;
    	}
    	return super.index(that);
    }
    
    @Override
    public Type assign(Type source)
    {
    	if ((source.equivalent(base)))
    	{
    		return source;
    	}
    	return super.assign(source);
    }
    
    @Override
    public boolean equivalent(Type that)
    {
        if (that == null)
            return false;
        if (!(that instanceof IntType))
            return false;
        
        ArrayType aType = (ArrayType)that;
        return this.extent == aType.extent && base.equivalent(aType.base);
    }
}
