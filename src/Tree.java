public class Tree
{
    private Node root;

    public Tree(Node root)
    {
        this.root = root;
    }
    
    public static final int MUTATE_NOISE = 0, MUTATE_CHANGE_FUNC = 1,
            MUTATE_SWAP_BRANCH = 2, MUTATE_PRUNE = 3, MUTATE_ADD = 4;
    
    public double evaluate()
    {
        return root.evaluate();
    }
    
    public void mutate(int depth, int type)
    {
        // TODO: mutate based on type!
        switch (type)
        {
            case MUTATE_NOISE:
                break;
            case MUTATE_CHANGE_FUNC:
                break;
            case MUTATE_SWAP_BRANCH:
                break;
            case MUTATE_PRUNE:
                break;
            case MUTATE_ADD:
                break;
            default:
                throw new IllegalArgumentException("Nonexistant mutation type: " + type);
        }
    }
    
    public Tree[] crossover(Tree other, int depth)
    {
        // TODO: STUB! implement
        // currently just returns this and other...
        return new Tree[]{this,other};
    }
    
    private static Node generateNode(int depth)
    {
        if (depth == 1)
            return new Scalar(Math.random()); // TODO: specify a "range" for the random number?
        
        if (Math.random() < 0.5f)
        {
            return new UnaryFunction(
                    generateNode(depth-1),
                    (int)(Math.random()*UnaryFunction.NUM_TYPES));
        }
        else
        {
            return new BinaryFunction(
                    generateNode(depth-1),
                    generateNode(depth-1),
                    (int)(Math.random()*BinaryFunction.NUM_TYPES));
        }
        // TODO: add small chance of stopping tree earlier?
        // otherwise random trees will always be full.
        // this is probably not desurable for anything other than depth 1 and 2
    }
    
    public static Tree generate(int depth)
    {
        if (depth <= 0) throw new IllegalArgumentException("Cannot generate tree of depth " + depth + ".");
        return new Tree(generateNode(depth));
    }
    
    // TODO: add convenience functions for getting random branches etc.
}
