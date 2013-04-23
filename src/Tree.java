
import java.util.ArrayList;
import java.util.Random;

public class Tree
{
    public static final Random RANDOM = new Random();
    
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
        int i;
        switch (type)
        {
            case MUTATE_NOISE:
                Scalar[] scalars = findScalars(root, depth);
                i = 0;
                while (scalars == null) // if no scalars are found on the level, search up and down until we find some
                {
                    scalars = findScalars(root, depth+i);
                    if (scalars == null && depth >= i)
                        scalars = findScalars(root, depth-i);
                    i++;
                }
                int rand = RANDOM.nextInt(scalars.length);
                scalars[rand].setValue(scalars[rand].getValue() + RANDOM.nextGaussian());
                break;
            case MUTATE_CHANGE_FUNC:
                // TODO: implement
                break;
            case MUTATE_SWAP_BRANCH:
                // TODO: implement
                break;
            case MUTATE_PRUNE:
                // TODO: implement
                break;
            case MUTATE_ADD:
                // TODO: implement
                break;
            default:
                throw new IllegalArgumentException("Nonexistant mutation type: " + type);
        }
    }
    
    // helper function for finding scalars at a given depth.
    private Scalar[] findScalars(Node node, int depth)
    {
        if (depth <= 0 && node.getChildren() == null)
            return new Scalar[]{(Scalar)node};
        
        Node[] children = node.getChildren();
        
        if (children == null)
            return null;
        
        ArrayList<Scalar> scalars = new ArrayList<Scalar>();
        
        for (Node child :children)
        {
            Scalar[] moreScalars = findScalars(child, depth-1);
            if (moreScalars != null)
                for (Scalar s :moreScalars)
                    scalars.add(s);
        }
        
        if (scalars.isEmpty()) return null;
        return scalars.toArray(new Scalar[0]);
    }
    
    public Tree[] crossover(Tree other, int depth)
    {
        // TODO: STUB! implement
        // currently just returns this and other...
        return new Tree[]{this,other};
    }
    
    // helper function for generating nodes.
    private static Node generateNode(int depth, int sofar)
    {
        if (depth == 1)
            return new Scalar(RANDOM.nextDouble()); // TODO: specify a "range" for the random number?
        
        // as a function of how deep we are,
        // a chance to "stop" the tree at the current branch.
        if (RANDOM.nextDouble() > Math.min(0.5f,(float)sofar*0.1f))
        {
            if (RANDOM.nextBoolean())
            {
                return new UnaryFunction(
                        generateNode(depth-1, sofar+1),
                        RANDOM.nextInt(UnaryFunction.NUM_TYPES));
            }
            else
            {
                return new BinaryFunction(
                        generateNode(depth-1,sofar+1),
                        generateNode(depth-1,sofar+1),
                        RANDOM.nextInt(BinaryFunction.NUM_TYPES));
            }
        }
        else
        {
            return generateNode(1, sofar);
        }
    }
    
    /**
     * Generates a random tree.
     * @param depth The desired depth of the tree (not guaranteed)
     * @return A randomly generated tree.
     */
    public static Tree generate(int depth)
    {
        if (depth <= 0) throw new IllegalArgumentException("Cannot generate tree of depth " + depth + ".");
        return new Tree(generateNode(depth, 0));
    }
    
    // TODO: add convenience functions for getting random branches etc.
}
