public abstract class Node
{
    /**
     * Evaluates this node.
     * @return The evaluation of this node.
     */
    public abstract double evaluate();
    
    public abstract void mutate();

    /**
     * Clone function.
     * @return A copy of this node.
     */
    public abstract Node sheep();

    protected Node[] children;
    
    protected Node(Node[] children)
    {
        this.children = children;
    }
    
    public final Node[] getChildren()
    {
        return children;
    }

    public final int getNumChildren()
    {
        return children.length;
    }

    public final boolean isLeaf()
    {
        return children == null || children.length == 0;
    }

    public final Node getChild(int i)
    {
        return children[i];
    }

    public final Node replaceChild(int i, Node replacement)
    {
        Node oldChild = children[i];
        children[i] = replacement;
        return oldChild;
    }
}
