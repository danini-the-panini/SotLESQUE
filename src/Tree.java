
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

    public Tree(final Tree other)
    {
        this.root = other.root.sheep();
    }
    
    public static final int MUTATE_NOISE = 0, MUTATE_CHANGE_FUNC = 1,
            MUTATE_SWAP_BRANCH = 2, MUTATE_PRUNE = 3, MUTATE_ADD = 4;
    
    public double evaluate()
    {
        return root.evaluate();
    }
    
    public void mutate(int depth, int type)
    {
        switch (type)
        {
            case MUTATE_NOISE:
                {
                    Scalar[] scalars = defFindScalars(root, depth);
        
                    int rand = RANDOM.nextInt(scalars.length);
                    scalars[rand].mutate();
                }
                break;
            case MUTATE_CHANGE_FUNC:
                {
                    Function[] funcs = defFindFunctions(root, depth);                
                    
                    int rand = RANDOM.nextInt(funcs.length);
                    funcs[rand].mutate();
                }
                break;
            case MUTATE_SWAP_BRANCH:
                {
                    Function[] parents = defFindFunctions(root, depth-1);

                    // pick two mutually exclusive parents 
                    int rand1 = RANDOM.nextInt(parents.length);
                    int rand2 = RANDOM.nextInt(parents.length-1);
                    if (rand2 >= rand1) rand2++;
    
                    // pick random children of these parents
                    int randC1 = RANDOM.nextInt(parents[rand1].getNumChildren());
                    int randC2 = RANDOM.nextInt(parents[rand2].getNumChildren());
    
                    // swap these children
                    Node replacement = parents[rand2].getChild(randC2);
                    replacement = parents[rand1].replaceChild(randC1, replacement);
                    parents[rand2].replaceChild(randC2, replacement);
                }
                break;
            case MUTATE_PRUNE:
                {
                    Function[] parents = defFindFunctions(root, depth-1);

                    int rand = RANDOM.nextInt(parents.length);
                    int randC = RANDOM.nextInt(parents[rand].getNumChildren());

                    parents[rand].replaceChild(randC, generateNode(1,0));
                }
                break;
            case MUTATE_ADD:
                {
                    Function[] parents = defFindFunctions(root, depth-1, true);
                    
                    int rand = RANDOM.nextInt(parents.length);
                    Node[] children = parents[rand].getChildren();

                    // find all the children that are leaves
                    int[] leaves = new int[children.length];
                    int numLeaves = 0;
                    for (int i = 0; i < children.length; i++)
                        if (children[i].getChildren() == null)
                            leaves[numLeaves++] = i;
                    int randC = leaves[RANDOM.nextInt(numLeaves)];

                    parents[rand].replaceChild(randC,
                            generateNode(RANDOM.nextInt(2)+2,0));
                }
                break;
            default:
                throw new IllegalArgumentException("Nonexistant mutation type: " + type);
        }
    }

    // helper function for definitely finding scalars at a given depth;
    // WARNING: this function is potentially terrible!
    private static Scalar[] defFindScalars(Node node, int depth)
    {
        Scalar[] scalars = null;
        int i = 0;

        // if no scalars are found on the level,
        // search up and down until we find some
        while (scalars == null)
        {
            scalars = findScalars(node, depth+i);
            if (scalars == null && depth >= i)
                scalars = findScalars(node, depth-i);
            i++;
        }

        return scalars;
    }
 
    
    // helper function for finding scalars at a given depth.
    private static Scalar[] findScalars(Node node, int depth)
    {
        if (depth < 0) return null;

        if (depth == 0 && node.getChildren() == null)
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

    // similar explaination to defFindScalars
    // needLeaves states whether you want functions that have at least one leaf as a child
    private static Function[] defFindFunctions(Node node, int depth, boolean needLeaves)
    {
        Function[] funcs = null;
        int i = 0;

        // if not functions are found on the level,
        // search up and down until we find some.
        while (funcs == null)
        {
            funcs = findFunctions(node, depth+i, needLeaves);
            if (funcs == null && depth >= i)
                funcs = findFunctions(node, depth-i, needLeaves);
            i++;
        }

        return funcs;
    }
    private static Function[] defFindFunctions(Node node, int depth)
    {
        return defFindFunctions(node,depth,false);
    }


    // helper function for finding functions at a given depth
    private static Function[] findFunctions(Node node, int depth, boolean needLeaves)
    {
        if (depth < 0) return null;

        Node[] children = node.getChildren();

        if (depth == 0 && children != null)
        {
            for (Node child :children)
                if (child.getChildren() == null)
                    needLeaves = false;
            if (!needLeaves)
                return new Function[]{(Function)node};
        }

        if (children == null)
            return null;

        ArrayList<Function> funcs = new ArrayList<Function>();

        for (Node child :children)
        {
            Function[] moreFuncs = findFunctions(child, depth-1, needLeaves);
            if (moreFuncs != null)
                for (Function n :moreFuncs)
                    funcs.add(n);
        }

        if (funcs.isEmpty())
            return null;
            
        return funcs.toArray(new Function[0]);
    }

    public Tree[] crossover(Tree other, int depth)
    {
        Tree me = new Tree(this);
        Tree you = new Tree(other);

        Function[] myFuncs = defFindFunctions(me.root,depth);
        Function[] urFuncs = defFindFunctions(you.root,depth);

        Function myParent = myFuncs[RANDOM.nextInt(myFuncs.length)];
        Function urParent = urFuncs[RANDOM.nextInt(urFuncs.length)];

        int rand1 = RANDOM.nextInt(myParent.getNumChildren());
        int rand2 = RANDOM.nextInt(urParent.getNumChildren());

        Node child = myParent.getChild(rand1);
        child = urParent.replaceChild(rand2,child);
        myParent.replaceChild(rand1,child);

        return new Tree[]{me,you};
    }
    
    // helper function for generating nodes.
    private static Node generateNode(int depth, int sofar)
    {
        Environment env = Environment.getInstance();
        double min = env.getScalarMinValue();
        double max = env.getScalarMaxValue();
        
        if (depth == 1)
            return new Scalar(RANDOM.nextDouble()*(max-min)+min);
        
        // a chance to "stop" the tree at the current branch,
        // probability is dependent on how deep we are (so far).
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
        if (depth <= 0)
            throw new IllegalArgumentException("Cannot generate tree of depth " + depth + ".");
        return new Tree(generateNode(depth, 0));
    }
    
}
