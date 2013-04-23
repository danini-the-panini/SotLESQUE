public abstract class Node
{
    protected Node[] children;
    
    public abstract double evaluate();
    
    protected Node(Node[] children)
    {
        this.children = children;
    }
    
    public final Node[] getChildren()
    {
        return children;
    }
}
