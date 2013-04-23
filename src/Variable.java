public class Variable extends Node
{
    int id;

    public Variable(int id)
    {
        super(null);
        this.id = id;
    }

    @Override
    public double evaluate()
    {
        // TODO: get value from "Environment" (hypothetical singleton class)
        
        // something like this:
        // return Environment.getInstance().getVariableValue(this.id);
        return Double.NaN;
    }

    @Override
    public Node sheep()
    {
        return new Variable(this.id);
    }

    @Override
    public String toString()
    {
        // TODO: get name from "Environment".
        
        // something like this:
        // return Environment.getInstance().getVariableName(this.id);
        return "$"+id;
    }
}
