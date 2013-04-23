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
        return Environment.getInstance().getVariableValue(this.id);
    }

    @Override
    public void mutate()
    {
        id = Tree.RANDOM.nextInt(Environment.getInstance().getVariableCount());
    }

    @Override
    public Node sheep()
    {
        return new Variable(this.id);
    }

    @Override
    public String toString()
    {
        return Environment.getInstance().getVariableName(this.id);
    }
}
